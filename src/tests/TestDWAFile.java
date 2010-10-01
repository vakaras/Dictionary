package tests;

import java.util.LinkedList;
import java.util.ListIterator;

import junit.framework.*;

import utils.Word;


public class TestDWAFile extends TestCase {

  private wordlists.DWAFile wordList;

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
    }

  public void testConcurentSearch() throws Exception {

    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("ąa", 100);
    }

  }
