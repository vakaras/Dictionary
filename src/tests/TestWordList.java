package tests;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import tests.runnable.RunnableSearch;
import utils.Word;
import wordlists.GSFMemory;
import wordlists.IWordListFileRead;
import wordlists.Manager;
import wordlists.WordList;
import wordlists.exceptions.InvalidCountException;

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

    Word[] expectedResult = new Word[] {
      new Word("aaaa", "Description 1."),
      new Word("aaab", "Description 2."),
      new Word("aaac", "Description 3. Same key as in 4."),
      new Word("aaac 1", "Description 4. Same key as in 3."),
      new Word("aabc", "Description 5.")
      };

    TestUtils.assertEquals(expectedResult, result);
    }

  @Test
  public void testUnicodeSearch() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("ąabh", 4);

    Assert.assertEquals(result.size(), 4);

    Word[] expectedResult = new Word[] {
      new Word("ąabi", 
          "Description 10. Testing unicode characters and collation."),
      new Word("ąačc", 
          "Description 11. Testing unicode characters and collation."),
      new Word("bbaa", "Description 12."),
      new Word("bbab", "Description 13.")
      };

    TestUtils.assertEquals(expectedResult, result);
    }
  
  @Test
  public void testSearchInTheEnd() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("dbai", 5);

    Assert.assertEquals(3, result.size());

    Word[] expectedResult = new Word[] {
      new Word("dbai", "Description 25."),
      new Word("eaa", 
          "Description 26.  Testing if shorter is before longer."),
      new Word("eaaa", "Description 27.")
      };

    TestUtils.assertEquals(expectedResult, result);
    }

  @Test
  public void testIndentifierIncrement() throws Exception {
    this.fileLoad();

    LinkedList<Word> result = wordList.search("bbag", 8);

    Assert.assertEquals(8, result.size());

    Word[] expectedResult = new Word[] {
      new Word("bbag", "Description 18."),
      new Word("bbah", "Description 19."),
      new Word("caab", "Description 20. Same key in 23, 24, 25, 26 and 27"),
      new Word("caab 1",
          "Description 21. Same key in 23, 24, 25, 26 and 27"),
      new Word("caab 2", 
          "Description 22. Same key in 23, 24, 25, 26 and 27"),
      new Word("caab 3",
          "Description 23. Same key in 23, 24, 25, 26 and 27"),
      new Word("caab 4",
          "Description 24. Same key in 23, 24, 25, 26 and 27"),
      new Word("dbai", "Description 25.")
      };

    TestUtils.assertEquals(expectedResult, result);
    
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
