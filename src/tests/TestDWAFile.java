package tests;

import java.util.LinkedList;
import java.util.ListIterator;

import junit.framework.*;

import utils.Word;


public class TestDWAFile extends TestCase {

  private wordlists.DWAFile wordList;

  private void assertEquals(LinkedList<Word> expected, 
      LinkedList<Word> actual) {

    super.assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      Word expectedWord = expected.get(i);
      Word actualWord = actual.get(i);
      super.assertEquals(expectedWord.getWord(), actualWord.getWord());
      super.assertEquals(expectedWord.getDescription(), 
          actualWord.getDescription());
      }
    
    }

  public void setUp() {
    wordList = new wordlists.DWAFile();
    }
  
  public void testFileLoad() {
    wordList.load("tests/test.dwa");
    }

  public void testFileDoesNotExist() throws Exception {
    try {
      wordList.load("tests/notExist.dwa");
      wordList.search("aaaa", 1);
      super.fail("Should have gotten FileNotFoundException!");
      }
    catch (java.io.FileNotFoundException e) {
      }
    }

  public void testInvalidCount() throws Exception {
    try {
      wordList.load("tests/test.dwa");
      wordList.search("aaaa", 0);
      super.fail("Should have gotten InvalidCountException!");
      }
    catch (wordlists.exceptions.InvalidCountException e) {
      }
    try {
      wordList.load("tests/test.dwa");
      wordList.search("aaaa", -2);
      super.fail("Should have gotten InvalidCountException!");
      }
    catch (wordlists.exceptions.InvalidCountException e) {
      }
    }

  public void testSimpleSearch() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", 1);
    }

  public void testSearch() throws Exception {
    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("aaa", 5);

    super.assertEquals(5, result.size());

    Word i = result.get(0);
    super.assertEquals("aaaa", i.getWord());
    super.assertEquals("Description 1.", i.getDescription());

    i = result.get(1);
    super.assertEquals("aaab", i.getWord());
    super.assertEquals("Description 2.", i.getDescription());

    i = result.get(2);
    super.assertEquals("aaac", i.getWord());
    super.assertEquals("Description 3. Same key as in 4.", 
        i.getDescription());

    i = result.get(3);
    super.assertEquals("aaac", i.getWord());
    super.assertEquals("Description 4. Same key as in 3.",
        i.getDescription());

    i = result.get(4);
    super.assertEquals("aabc", i.getWord());
    super.assertEquals("Description 5.",
        i.getDescription());

    }
  
  public void testUnicodeSearch() throws Exception {

    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("ąabh", 4);

    super.assertEquals(result.size(), 4);

    Word i = result.get(0);
    super.assertEquals("ąabi", i.getWord());
    super.assertEquals(
        "Description 10. Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(1);
    super.assertEquals("ąačc", i.getWord());
    super.assertEquals(
        "Description 11. Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(2);
    super.assertEquals("bbaa", i.getWord());
    super.assertEquals("Description 12.", i.getDescription());

    i = result.get(3);
    super.assertEquals("bbab", i.getWord());
    super.assertEquals("Description 13.", i.getDescription());

    }

  public void testSearchInTheEnd() throws Exception {

    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("bbai", 5);

    super.assertEquals(4, result.size());

    Word i = result.get(0);
    super.assertEquals("bbai", i.getWord());
    super.assertEquals("Description 20.", i.getDescription());

    i = result.get(1);
    super.assertEquals("caa", i.getWord());
    super.assertEquals(
        "Description 21.  Testing if shorter is before longer.", 
        i.getDescription());

    i = result.get(2);
    super.assertEquals("caaa", i.getWord());
    super.assertEquals("Description 22.", i.getDescription());

    i = result.get(3);
    super.assertEquals("caab", i.getWord());
    super.assertEquals("Description 23.", i.getDescription());

    }

  class RunnableSearch implements Runnable {

    private wordlists.WordList wordList = null;
    private TestCase test = null;
    private String request = null;
    private int count = 0;
    private LinkedList<Word> result = null;
    private Throwable exception = null;

    public RunnableSearch(wordlists.WordList wordList, TestCase test,
        String request, int count) {
      this.wordList = wordList;
      this.test = test;
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

  public void testConcurentSearch() throws Throwable {

    wordList.load("tests/test.dwa");

    LinkedList<Word> expectedResultLong = wordList.search("", 100);
    LinkedList<Word> expectedResultShort = wordList.search("aa", 2);

    RunnableSearch r1 = new RunnableSearch(wordList, this, "", 100);
    RunnableSearch r2 = new RunnableSearch(wordList, this, "aa", 2);

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
