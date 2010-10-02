package tests;

import java.util.LinkedList;
import java.util.Collection;

import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import org.junit.Assert;

import wordlists.*;
import wordlists.exceptions.*;

/**
 * Tester class for classes which implements IWordListChange interface.
 */
@RunWith(value=Parameterized.class)
public class TestIWordListChange {

  private IWordListChange wordList = null;
  private String className = null;
  private String testFile = null;
  private String fileExtension = null;

  /**
   * Creates testing environment.
   *
   * If given class object implements IWordListChange interface, then sets 
   * wordList field to point to that object. If not, leaves it null.
   *
   * @param className – the name of class to be used as wordList.
   * @param fileExtension – test file extension.
   */
  public TestIWordListChange(String className, String fileExtension) 
      throws Exception {

    this.className = className;
    this.fileExtension = fileExtension;
    this.testFile = "tests/test" + this.fileExtension;

    Object o = Class.forName(className).newInstance();

    this.wordList = (IWordListChange) o;
    }

  /**
   * Gives word lists, which need to be tested.
   */
  @Parameters
  public static Collection getChangeableWordLists() {
    return Manager.getChangeableWordListsWithFileExtensions();
    }

  @Before
  public void setUp() throws Exception {
    // Making an assumption that all WordLists implements 
    // IWordListFileRead interface.
    ((IWordListFileRead) this.wordList).load(this.testFile);
    }

  @Test
  public void testGetWordDefinition() throws Exception {

    Assert.assertEquals(
        "Description 6.", this.wordList.getWordDefinition("aabd"));

    Assert.assertEquals(
        "Description 10. Testing unicode characters and collation.", 
        this.wordList.getWordDefinition("ąabi"));

    Assert.assertEquals(
        "Description 20. Same key in 23, 24, 25, 26 and 27",
        this.wordList.getWordDefinition("caab"));

    Assert.assertEquals(
        "Description 23. Same key in 23, 24, 25, 26 and 27",
        this.wordList.getWordDefinition("caab 3"));

    }

  @Test(expected=IdentifierNotExistsException.class)
  public void testGetWordDefinitionNotExist() throws Exception {

    this.wordList.getWordDefinition("abbb");

    }

  @Test
  public void testEraseWord() throws Exception {

    this.wordList.eraseWord("aabc");
    this.wordList.eraseWord("ąabi");

    // Making an assumption that all WordLists that implements 
    // IWordListChange interface also implements IWordListFileWrite 
    // interface.
    ((IWordListFileWrite) this.wordList).save("tests/test.new.dwa");

    TestUtils.assertFilesEquals(
        "tests/test.dwa", "tests/test.new.dwa",
        new String[] { 
          "5d4",
          "< aabc=Description 5.",
          "10d8",
          "< ąabi=Description 10. Testing unicode characters and collation."
          }
        );

    TestUtils.deleteFile("tests/test.new.dwa");
    }

  @Test(expected=IdentifierNotExistsException.class)
  public void testEraseWordNotExist() throws Exception {

    this.wordList.eraseWord("abbb");

    }

  @Test
  public void testUpdateWord() throws Exception {

    this.wordList.updateWord("aabc", "Updated description 01");
    this.wordList.updateWord("ąabi", "Updated unicode description 02");

    // Making an assumption that all WordLists that implements 
    // IWordListChange interface also implements IWordListFileWrite 
    // interface.
    ((IWordListFileWrite) this.wordList).save("tests/test.new.dwa");

    TestUtils.assertFilesEquals(
        "tests/test.dwa", "tests/test.new.dwa",
        new String[] {
          "5c5",
          "< aabc=Description 5.",
          "---",
          "> aabc=Updated description 01",
          "10c10",
          "< ąabi=Description 10. " +
            "Testing unicode characters and collation.",
          "---",
          "> ąabi=Updated unicode description 02",
          }
        );

    TestUtils.deleteFile("tests/test.new.dwa");
    }

  @Test(expected=IdentifierNotExistsException.class)
  public void testUpdateWordNotExist() throws Exception {

    this.wordList.updateWord("abbb", "Some update...");

    }

  @Test(expected=InvalidDefinitionException.class)
  public void testUpdateWordInvalidDefinition() throws Exception {

    this.wordList.updateWord("aabc", "");

    }

  @Test
  public void testAddWord() throws Exception {

    this.wordList.addWord("ąaaa", "Added word 01");
    this.wordList.addWord("aabg", "Added word 02");
    this.wordList.addWord("bbag 1", "Added word 04");
    this.wordList.addWord("čaab", "Added word 05");
    this.wordList.addWord("bba", "Added word 03");

    // Making an assumption that all WordLists that implements 
    // IWordListChange interface also implements IWordListFileWrite 
    // interface.
    ((IWordListFileWrite) this.wordList).save("tests/test.new.dwa");

    TestUtils.assertFilesEquals(
        "tests/test.dwa", "tests/test.new.dwa",
        new String[] {
          "1a2",
          "> ąaaa=Added word 01",
          "8a10",
          "> aabg=Added word 02",
          "11a14",
          "> bba=Added word 03",
          "18a22",
          "> bbag=Added word 04",
          "24a29",
          "> čaab=Added word 05",
          }
        );

    TestUtils.deleteFile("tests/test.new.dwa");
    }

  @Test(expected=InvalidIdentifierException.class)
  public void testAddWordInvalidIdentifier() throws Exception {

    this.wordList.addWord("", "Some definition.");

    }

  @Test(expected=InvalidDefinitionException.class)
  public void testAddWordInvalidDefinition() throws Exception {

    this.wordList.addWord("abbb", "");

    }

  @Test(expected=DuplicateIdentifierException.class)
  public void testAddWordDuplicateIdentifier() throws Exception {

    this.wordList.addWord("aaaa", "Some definition.");

    }

  }
