package wordlists;

import java.util.LinkedList;
import java.util.ListIterator;
import java.text.Collator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import utils.Word;

/**
 * Class implementing word list working with DWA files. (Loads them into
 * memory.)
 */
public class DWAMemory extends WordList implements 
  IWordListChange, IWordListFileWrite, IWordListFileRead {
  // FIXME: Exceptions types.
  // FIXME: Make synchronized!!!

  private String filename = null;
  private LinkedList<Word> data = null;

  public LinkedList<Word> search(String word, int count)
    throws Exception {

    LinkedList<Word> result = new LinkedList<Word>();

    Collator collator = Collator.getInstance();
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.

    for (Word w: this.data) {
      if (collator.compare(word, w.getWord()) <= 0) {
        result.add(w);
        count--;
        if (count <= 0)
          break;
        }
      }

    return result;
    }

  public void load(String filename) throws Exception {

    this.filename = filename;
    this.data = new LinkedList<Word>();

    BufferedReader in = new BufferedReader(
        new InputStreamReader(
          new FileInputStream(this.filename), "UTF8"));

    while (true) {
      String str = in.readLine();
      if (str == null)
        break;
      int splitPoint = str.indexOf('=');
      String w = str.substring(0, splitPoint);
      String d = str.substring(splitPoint+1);
      this.data.add(new Word(w, d));
      }

    }

  public void save(String filename) throws Exception {

    BufferedWriter out = null;
    try {
      out = new BufferedWriter(
          new OutputStreamWriter(
            new FileOutputStream(filename),"UTF8"));
      for (Word w: this.data) {
        out.write(w.getWord()+"="+w.getDescription());
        out.newLine();
        }
      }
    finally {
      if (out != null)
        out.close();
      }

    this.filename = filename;
    }

  public void addWord(String word, String definition) throws Exception {

    Collator collator = Collator.getInstance();
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.
  
    ListIterator<Word> iter = this.data.listIterator(0);

    while (iter.hasNext()) {
      Word w = iter.next();
      if (word.equals(w.getWord())) {
        throw new Exception("Word already exists!");
        }
      if (collator.compare(word, w.getWord()) < 0) {
        // TODO: Check if works!
        iter.add(new Word(word, definition));
        return;
        }
      }

    iter.add(new Word(word, definition));
    return;
    }

  public void updateWord(String word, String definition) throws Exception {

    ListIterator<Word> iter = this.data.listIterator(0);

    while (iter.hasNext()) {
      Word w = iter.next();
      if (word.equals(w.getWord())) {
        iter.set(new Word(word, definition));
        return;
        }
      }
    
    throw new Exception("Word not found!");
    }

  public void eraseWord(String word) throws Exception {

    ListIterator<Word> iter = this.data.listIterator(0);

    while (iter.hasNext()) {
      Word w = iter.next();
      if (word.equals(w.getWord())) {
        iter.remove();
        return;
        }
      }
    
    throw new Exception("Word not found!");
    }

  public String getWordDefinition(String word) throws Exception {

    for (Word w: this.data) {
      if (word.equals(w.getWord())) {
        return w.getDescription();
        }
      }

    throw new Exception("Word not found!");
    }

  }
