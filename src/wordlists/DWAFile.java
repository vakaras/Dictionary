package wordlists;

import java.util.LinkedList;
import java.text.Collator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.io.IOException;
import wordlists.exceptions.*;

import utils.Word;

/**
 * Class implementing word list working with DWA files, while not loading 
 * them into memory.
 *
 * Note that before wordlist can be used, DWA file must be reffered by 
 * calling method load.
 */
public class DWAFile extends WordList implements IWordListFileRead {

  private String filename = null;

  /**
   * Look at WordList class documentation.
   */
  public LinkedList<Word> search(String word, int count) 
    throws IOException, InvalidCountException, WrongFileFormatException {

    if (count < 1) {
      throw new InvalidCountException("Count must be possitive number. " +
          count + " was passed.");
      }

    LinkedList<Word> result = new LinkedList<Word>();
    BufferedReader in = null;
    Collator collator = Collator.getInstance();
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.

    try {
      in = new BufferedReader(
        new InputStreamReader(
          new FileInputStream(filename), "UTF8"));
      String oldIdentifier = null;
      String parentIdentifier = null;
      while (true) {
        String str = in.readLine();
        if (str == null)
          break;
        int splitPoint = str.indexOf('=');
        String w = str.substring(0, splitPoint);
        String d = str.substring(splitPoint+1);
        if (w.length() == 0) {
          throw new WrongFileFormatException("Empty identifier.");
          }
        if (d.length() == 0) {
          throw new WrongFileFormatException("Empty definition.");
          }
        //System.out.print("read: \"" + w + "\" --> \"");
        w = Word.clearWordIdentifier(w);
        //System.out.print(w + "\" --> \"");
        if (parentIdentifier != null && 
            collator.compare(parentIdentifier, w) == 0) {
          w = Word.increaseWordIdentifier(oldIdentifier);
          oldIdentifier = w;
          }
        else {
          parentIdentifier = w;
          oldIdentifier = w;
          }
        //System.out.println(w + "\"");
        if (collator.compare(word, w) <= 0) {
          result.add(new Word(w, d));
          count--;
          if (count <= 0)
            break;
          }
        }
      }
    finally {
      if (in != null) {
        in.close();
        }
      }

    return result;
    }
  
  /**
   * Look at IWordListFileRead interface documentation.
   */
  public void load(String filename) {
    this.filename = filename;
    }
  
  }
