package wordlists;

import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import utils.Word;


/**
 * Class implementing word list working with DWA Files. (Not loading it to 
 * memory.)
 */
public class DWAFile extends WordList implements IWordListFileRead {

  private String filename = null;

  public LinkedList<Word> search(String word, int count) 
    throws Exception {

    LinkedList<Word> result = new LinkedList<Word>();

    BufferedReader in = new BufferedReader(
        new InputStreamReader(
          new FileInputStream(filename), "UTF8"));

    while (true) {
      String str = in.readLine();
      if (str == null)
        break;
      int splitPoint = str.indexOf('=');
      String w = str.substring(0, splitPoint);
      String d = str.substring(splitPoint+1);
      if (w.startsWith(word)) {
        result.add(new Word(w, d));
        count--;
        if (count <= 0)
          break;
        }
      }

    return result;
    }
  
  public void load(String filename) {
    this.filename = filename;
    }
  
  }
