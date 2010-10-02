package tests;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import org.junit.Assert;

import utils.Word;
import wordlists.*;
import wordlists.exceptions.*;
import tests.TestUtils;
import tests.runnable.*;

/**
 * Tester class for classes which extends WordList class.
 */
@RunWith(value=Parameterized.class)
public class TestWordList {

  private WordList wordList = null;
  private String className = null;
  private String testFile = null;
  private String fileExtension = null;

  /**
   * Creates testing environment.
   *
   * If given class object implements IWordList interface, then sets 
   * wordList field to point to that object. If not, leaves it null.
   *
   * @param className – the name of class to be used as wordList.
   * @param fileExtension – test file extension.
   */
  public TestWordList(String className, String fileExtension) 
      throws Exception {

    this.className = className;
    this.fileExtension = fileExtension;
    this.testFile = "tests/test" + this.fileExtension;

    Object o = Class.forName(className).newInstance();

    //if (o instanceof wordlists.WordList) {
    this.wordList = (WordList) o;
    }

  /**
   * Gives word lists, which need to be tested.
   */
  @Parameters
  public static Collection getWordLists() {
    return Manager.getWordListsWithFileExtensions();
    }

  @BeforeClass
  public static void setUpClass() throws Exception {
    GSFMemory wordListMem = new GSFMemory();
    wordListMem.load("tests/test.dwa");
    wordListMem.save("tests/test.gsf");
    }
  
  @AfterClass
  public static void tearDownClass() {
    File f = new File("tests/test.gsf");
    f.delete();
    }

  @Test
  public void fileLoad() throws Exception {
    // Making an assumption that all WordLists implements 
    // IWordListFileRead interface.
    ((IWordListFileRead) this.wordList).load(this.testFile);
    this.wordList.search("aaaa", 1);
    }

  @Test(expected=java.io.FileNotFoundException.class)
  public void testFileDoesNotExist() throws Exception {
    ((IWordListFileRead) this.wordList).load("tests/notExist" + 
      this.fileExtension);
    this.wordList.search("aaaa", 1);
    }

  @Test(expected=InvalidCountException.class)
  public void testInvalidCountZero() throws Exception {
    this.fileLoad();
    this.wordList.search("aaaa", 0);
    }

  @Test(expected=InvalidCountException.class)
  public void testInvalidCountNegative() throws Exception {
    this.fileLoad();
    this.wordList.search("aaaa", -2);
    }

  @Test
  public void testSearch() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("aaa", 5);

    Assert.assertEquals(5, result.size());

    Word i = result.get(0);
    Assert.assertEquals("aaaa", i.getWord());
    Assert.assertEquals("Description 1.", i.getDescription());

    i = result.get(1);
    Assert.assertEquals("aaab", i.getWord());
    Assert.assertEquals("Description 2.", i.getDescription());

    i = result.get(2);
    Assert.assertEquals("aaac", i.getWord());
    Assert.assertEquals("Description 3. Same key as in 4.", 
        i.getDescription());

    i = result.get(3);
    Assert.assertEquals("aaac", i.getWord());
    Assert.assertEquals("Description 4. Same key as in 3.",
        i.getDescription());

    i = result.get(4);
    Assert.assertEquals("aabc", i.getWord());
    Assert.assertEquals("Description 5.",
        i.getDescription());

    }

  @Test
  public void testUnicodeSearch() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("ąabh", 4);

    Assert.assertEquals(result.size(), 4);

    Word i = result.get(0);
    Assert.assertEquals("ąabi", i.getWord());
    Assert.assertEquals(
        "Description 10. Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(1);
    Assert.assertEquals("ąačc", i.getWord());
    Assert.assertEquals(
        "Description 11. Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(2);
    Assert.assertEquals("bbaa", i.getWord());
    Assert.assertEquals("Description 12.", i.getDescription());

    i = result.get(3);
    Assert.assertEquals("bbab", i.getWord());
    Assert.assertEquals("Description 13.", i.getDescription());

    }
  
  @Test
  public void testSearchInTheEnd() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("bbai", 5);

    Assert.assertEquals(4, result.size());

    Word i = result.get(0);
    Assert.assertEquals("bbai", i.getWord());
    Assert.assertEquals("Description 20.", i.getDescription());

    i = result.get(1);
    Assert.assertEquals("caa", i.getWord());
    Assert.assertEquals(
        "Description 21.  Testing if shorter is before longer.", 
        i.getDescription());

    i = result.get(2);
    Assert.assertEquals("caaa", i.getWord());
    Assert.assertEquals("Description 22.", i.getDescription());

    i = result.get(3);
    Assert.assertEquals("caab", i.getWord());
    Assert.assertEquals("Description 23.", i.getDescription());

    }
  
  @Test
  public void testConcurentSearch() throws Throwable {
    this.fileLoad();

    LinkedList<Word> expectedResultLong = wordList.search("", 100);
    LinkedList<Word> expectedResultShort = wordList.search("aa", 2);

    RunnableSearch r1 = new RunnableSearch(wordList, "", 100);
    RunnableSearch r2 = new RunnableSearch(wordList, "aa", 2);

    Thread thread1 = new Thread(r1);
    Thread thread2 = new Thread(r2);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    Throwable e = r1.getException();
    if (e != null) {
      throw e;
      }
    e = r2.getException();
    if (e != null) {
      throw e;
      }

    LinkedList<Word> concurentResultLong = r1.getResult();
    LinkedList<Word> concurentResultShort = r2.getResult();

    TestUtils.assertEquals(expectedResultLong, concurentResultLong);
    TestUtils.assertEquals(expectedResultShort, concurentResultShort);

    }

  }
