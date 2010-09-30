package tests;

import java.util.LinkedList;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

import junit.framework.*;

import utils.Word;


public class TestGSFFile extends TestCase {

  private wordlists.GSFFile wordList;

  private void printResult(LinkedList<Word> result) {

    for (Word w: result) {
      System.out.println(w.getWord() + " ---- " + w.getDescription());
      }

    }

  public void setUp() throws Exception {

    wordlists.GSFMemory wordListMem = new wordlists.GSFMemory();
    wordListMem.load("tests/test.dwa");
    wordListMem.save("tests/test.gsf");

    wordList = new wordlists.GSFFile();
    }

  public void tearDown() throws Exception {
    File f = new File("tests/test.new.dwa");
    f = new File("tests/test.gsf");
    f.delete();
    }
  
  public void testFileLoad() throws Exception {
    wordList.load("tests/test.gsf");
    }

  public void testSimpleSearch() throws Exception {
    wordList.load("tests/test.gsf");
    wordList.search("aaaa", 1);
    }

  public void testSearch() throws Exception {
    wordList.load("tests/test.gsf");

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
    super.assertEquals("aaac 1", i.getWord());
    super.assertEquals("Description 4. Same key as in 3.",
        i.getDescription());

    i = result.get(4);
    super.assertEquals("aabc", i.getWord());
    super.assertEquals("Description 5.",
        i.getDescription());

    }
  
  public void testUnicodeSearch() throws Exception {

    wordList.load("tests/test.gsf");

    LinkedList<Word> result = wordList.search("ąabh", 4);

    super.assertEquals(4, result.size());

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

  }
