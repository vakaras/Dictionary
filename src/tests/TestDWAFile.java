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

  public void testSimpleSearch() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", 1);
    }

  public void testSearch() throws Exception {
    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("aaa", 5);

    super.assertEquals(result.size(), 4);

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

    }
  
  public void testUnicodeSearch() throws Exception {

    wordList.load("tests/test.dwa");

    LinkedList<Word> result = wordList.search("ąa", 5);

    super.assertEquals(result.size(), 4);

    Word i = result.get(0);
    super.assertEquals("ąabf", i.getWord());
    super.assertEquals(
        "Description 8.  Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(1);
    super.assertEquals("ąabg", i.getWord());
    super.assertEquals(
        "Description 9.  Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(2);
    super.assertEquals("ąabh", i.getWord());
    super.assertEquals(
        "Description 10. Testing unicode characters and collation.",
        i.getDescription());

    i = result.get(3);
    super.assertEquals("ąačc", i.getWord());
    super.assertEquals(
        "Description 11. Testing unicode characters and collation.",
        i.getDescription());
    }
  
  }
