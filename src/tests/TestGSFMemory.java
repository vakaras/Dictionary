package tests;

import java.util.LinkedList;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

import junit.framework.*;

import utils.Word;


public class TestGSFMemory extends TestCase {

  private wordlists.GSFMemory wordList;

  private void printResult(LinkedList<Word> result) {

    for (Word w: result) {
      System.out.println(w.getWord() + " ---- " + w.getDescription());
      }

    }

  public void setUp() {
    wordList = new wordlists.GSFMemory();
    }
  
  public void testFileLoad() throws Exception {
    wordList.load("tests/test.dwa");
    }

  public void testSimpleSearch() throws Exception {
    wordList.load("tests/test.dwa");
    wordList.search("aaaa", 1);
    }

  public void testSearch() throws Exception {
    wordList.load("tests/test.dwa");

    wordList.save("tests/test.gsf");
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

    wordList.load("tests/test.dwa");

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

  public void testSave() throws Exception {

    // TODO: Test case, when one word is prefix of other. With Unicode too.
    
    wordList.load("tests/test.dwa");
    wordList.save("tests/test.gsf");

    wordlists.GSFMemory wordListTemp = new wordlists.GSFMemory();
    wordListTemp.load("tests/test.gsf");
    wordListTemp.save("tests/test.new.dwa");

    // Check if files content is not different.
    Process diff = Runtime.getRuntime().exec(
        new String[] {"diff", "tests/test.dwa", "tests/test.new.dwa"});

    BufferedReader out = new BufferedReader( 
        new InputStreamReader(diff.getInputStream()));

    //String[] expectedOutput = new String[] { 
    //  "4c4",
    //  "< aaac=Description 4. Same key as in 3.",
    //  "---",
    //  "> aaac 1=Description 4. Same key as in 3.",
    //  "21,24c21,24",
    //  "< caab=Description 21. Same key in 23, 24, 25, 26 and 27",
    //  "< caab=Description 22. Same key in 23, 24, 25, 26 and 27",
    //  "< caab=Description 23. Same key in 23, 24, 25, 26 and 27",
    //  "< caab=Description 24. Same key in 23, 24, 25, 26 and 27",
    //  "---",
    //  "> caab 1=Description 21. Same key in 23, 24, 25, 26 and 27",
    //  "> caab 2=Description 22. Same key in 23, 24, 25, 26 and 27",
    //  "> caab 3=Description 23. Same key in 23, 24, 25, 26 and 27",
    //  "> caab 4=Description 24. Same key in 23, 24, 25, 26 and 27" };
    //for (String line : expectedOutput) {
    //  super.assertEquals(line, out.readLine());
    //  }
    super.assertEquals(null, out.readLine());

    // Delete file.
    File f = new File("tests/test.new.dwa");
    super.assertTrue(f.delete());

    f = new File("tests/test.gsf");
    super.assertTrue(f.delete());
    }
  
  }
