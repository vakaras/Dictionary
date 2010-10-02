package tests;

import java.util.LinkedList;

import org.junit.Assert;

import utils.Word;

public class TestUtils {

  public static void assertEquals(LinkedList<Word> expected, 
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

  }
