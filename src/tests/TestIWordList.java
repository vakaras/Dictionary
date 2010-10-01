package tests;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import org.junit.Assert;

import utils.Word;

/**
 * Tester class for classes which implement IWordList interface.
 */
@RunWith(value=Parameterized.class)
public class TestIWordList {

  private wordlists.IWordList wordList = null;
  private String className = null;

  /**
   * Creates testing environment.
   *
   * If given class object implements IWordList interface, then sets 
   * wordList field to point to that object. If not, leaves it null.
   *
   * @param className – the name of class to be used as wordList.
   */
  public TestIWordList(String className) throws Exception {

    this.className = className;

    Object o = Class.forName(className).newInstance();

    if (o instanceof wordlists.IWordList) {
      this.wordList = (wordlists.IWordList) o;
      }
    else {
      this.wordList = null;
      }
    
    }

  /**
   * Gives word lists, which need to be tested.
   */
  @Parameters
  public static Collection getWordLists() {
    // TODO: Make external. Also return just that, what is requested.
    // (To remove checking from constructor and each method.)
    return Arrays.asList( new Object[][] {
      { "wordlists.DWAFile" },
      { "wordlists.DWAMemory" },
      { "wordlists.GSFFile" },
      { "wordlists.GSFMemory" },
      });
    }

  @Test
  public void dummyTest() throws Exception {
    if (this.wordList == null) {
      System.out.println(this.className + " not implements IWordList");
      }
    else {
      System.out.println(this.className + " implements IWordList");
      }
    }
  
  }
