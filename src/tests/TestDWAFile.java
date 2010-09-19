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
    super.assertEquals(i.getWord(), "aaaa");
    super.assertEquals(i.getDescription(), "Description 1.");

    i = result.get(1);
    super.assertEquals(i.getWord(), "aaab");
    super.assertEquals(i.getDescription(), "Description 2.");

    i = result.get(2);
    super.assertEquals(i.getWord(), "aaac");
    super.assertEquals(i.getDescription(), 
        "Description 3. Same key as in 4.");

    i = result.get(3);
    super.assertEquals(i.getWord(), "aaac");
    super.assertEquals(i.getDescription(), 
        "Description 4. Same key as in 3.");

    }
  
  }
