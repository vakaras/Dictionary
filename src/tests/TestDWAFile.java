package tests;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import utils.Word;


public class TestDWAFile {

  private wordlists.DWAFile wordList;

  private void assertEquals(LinkedList<Word> expected, 
      LinkedList<Word> actual) {

    Assert.assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      Word expectedWord = expected.get(i);
      Word actualWord = actual.get(i);
      Assert.assertEquals(expectedWord.getWord(), actualWord.getWord());
      Assert.assertEquals(expectedWord.getDescription(), 
          actualWord.getDescription());
      }
    
    }

  @Before
  public void setUp() {
    wordList = new wordlists.DWAFile();
    }

  @After
  public void tearDown() {
    }
  
  @Test
  public void testFileLoad() {
    wordList.load("tests/test.dwa");
    }

  @Test(expected=java.io.FileNotFoundException.class)
  public void testFileDoesNotExist() throws Exception {
    wordList.load("tests/notExist.dwa");
    wordList.search("aaaa", 1);
    Assert.fail("Should have gotten FileNotFoundException!");
    }

  @Test(expected=wordlists.exceptions.InvalidCountException.class)
  public void testInvalidCountZero() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", 0);
    Assert.fail("Should have gotten InvalidCountException!");
    }

  @Test(expected=wordlists.exceptions.InvalidCountException.class)
  public void testInvalidCountNegative() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", -2);
    Assert.fail("Should have gotten InvalidCountException!");
    }

  @Test
  public void testSimpleSearch() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", 1);
    }

  @Test
  public void testSearch() throws Exception {
    wordList.load("tests/test.dwa");

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

    wordList.load("tests/test.dwa");

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

    wordList.load("tests/test.dwa");

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

  class RunnableSearch implements Runnable {

    private wordlists.WordList wordList = null;
    private String request = null;
    private int count = 0;
    private LinkedList<Word> result = null;
    private Throwable exception = null;

    public RunnableSearch(wordlists.WordList wordList, String request, 
        int count) {
      this.wordList = wordList;
      this.request = request;
      this.count = count;
      }
    
    public LinkedList<Word> getResult() {
      return this.result;
      }

    public Throwable getException() {
      return this.exception;
      }
    
    public void run() {
      try {
        //System.out.println("Started: \"" + this.request + "\"");
        this.result = wordList.search(this.request, this.count);
        //System.out.println("Finished: \"" + this.request + "\"");
        }
      catch (Throwable e) {
        this.exception = e;
        }
      }
    
    }

  @Test
  public void testConcurentSearch() throws Throwable {

    wordList.load("tests/test.dwa");

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

    this.assertEquals(expectedResultLong, concurentResultLong);
    this.assertEquals(expectedResultShort, concurentResultShort);

    }

  }
