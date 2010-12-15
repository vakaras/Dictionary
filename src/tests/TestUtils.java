package tests;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

import junit.framework.Assert;

import utils.Word;

public class TestUtils {

  public static void assertEquals(
      LinkedList<Word> expected, LinkedList<Word> actual) {

    junit.framework.Assert.assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      Word expectedWord = expected.get(i);
      Word actualWord = actual.get(i);
      junit.framework.Assert.assertEquals(expectedWord.getWord(), actualWord.getWord());
      junit.framework.Assert.assertEquals(expectedWord.getDescription(), 
          actualWord.getDescription());
      }
    
    }

  public static void assertEquals(
      Word[] expected, LinkedList<Word> actual) {

    junit.framework.Assert.assertEquals(expected.length, actual.size());

    for (int i = 0; i < expected.length; i++) {
      Word expectedWord = expected[i];
      Word actualWord = actual.get(i);
      junit.framework.Assert.assertEquals(expectedWord.getWord(), actualWord.getWord());
      junit.framework.Assert.assertEquals(
          expectedWord.getDescription(), 
          actualWord.getDescription());
      }
    
    }

  public static void assertFilesEquals(
      String oldFileName, String newFileName, String[] expectedDiffOutput) 
      throws Exception {

    Process diff = Runtime.getRuntime().exec(
        new String[] {"diff", oldFileName, newFileName});

    BufferedReader out = null;
    try {
      out = new BufferedReader(
          new InputStreamReader(diff.getInputStream()));

      for (String line : expectedDiffOutput) {
        junit.framework.Assert.assertEquals(line, out.readLine());
        }
      
      junit.framework.Assert.assertEquals(null, out.readLine());
      }
    finally {
      if (out != null) {
        out.close();
        }
      }

    }
  
  public static void deleteFile(String fileName) throws Exception {
    File f = new File(fileName);
    junit.framework.Assert.assertTrue(f.delete());
    }

  }
