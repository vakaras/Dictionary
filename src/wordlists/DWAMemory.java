package wordlists;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.Comparator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.IOException;
import wordlists.exceptions.*;

import utils.Word;
import utils.StringCollator;

/**
 * Class implementing word list working with DWA files, which it loads into
 * memory.
 *
 * Note that before word list can be used, DWA file must be reffered by
 * calling method load.
 */
public class DWAMemory extends WordList implements 
  IWordListChange, IWordListFileWrite, IWordListFileRead {

  private String filename = null;
  private Comparator<Object> collator = null;
  private LinkedList<Word> data = null;

  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock readLock = lock.readLock();
  private final Lock writeLock = lock.writeLock();

  /**
   * Default constructor.
   */
  public DWAMemory() {
    this.collator = StringCollator.getInstance();
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.
    }

  /**
   * Look at WordList class documentation.
   */
  public LinkedList<Word> search(String word, int count) 
      throws InvalidCountException {

    if (count < 1) {
      throw new InvalidCountException(
          "Count must be possitive number. " + count + " was passed.");
      }

    LinkedList<Word> result = new LinkedList<Word>();

    this.readLock.lock();
    try {
      for (Word w: this.data) {
        if (this.collator.compare(word, w.getWord()) <= 0) {
          result.add(w);
          count--;
          if (count <= 0)
            break;
          }
        }
      }
    finally {
      this.readLock.unlock();
      }

    return result;
    }

  /**
   * Look at IWordListFileRead interface documentation.
   */
  public void load(String filename) 
      throws IOException, WrongFileFormatException {

    this.filename = filename;
    this.data = new LinkedList<Word>();

    BufferedReader in = null;
    
    this.writeLock.lock();
    try {
      in = new BufferedReader(new InputStreamReader( 
            new FileInputStream(this.filename), "UTF8"));

      String oldIdentifier = null;
      String parentIdentifier = null;
      while (true) {
        String str = in.readLine();
        if (str == null)
          break;
        int splitPoint = str.indexOf('=');
        // TODO: Check cases, when WrongFileFormatException should be 
        // thrown.
        if (splitPoint == -1) {
          throw new WrongFileFormatException(
              "Separator '=' was not found.");
          }
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
            this.collator.compare(parentIdentifier, w) == 0) {
          w = Word.increaseWordIdentifier(oldIdentifier);
          oldIdentifier = w;
          }
        else {
          parentIdentifier = w;
          oldIdentifier = w;
          }
        //System.out.println(w + "\"");
        this.data.add(new Word(w, d));
        }
      }
    finally {
      if (in != null) {
        in.close();
        }
      this.writeLock.unlock();
      }

    }

  /**
   * Look at IWordListFileWrite interface documentation.
   */
  public void save(String filename) throws IOException {

    BufferedWriter out = null;

    this.readLock.lock();
    try {
      out = new BufferedWriter(new OutputStreamWriter( 
            new FileOutputStream(filename),"UTF8"));
      for (Word word: this.data) {
        String w = Word.clearWordIdentifier(word.getWord());
        out.write(w + "=" + word.getDescription());
        out.newLine();
        }
      }
    finally {
      if (out != null) {
        out.close();
        }
      this.readLock.unlock();
      }

    this.filename = filename;
    }

  /**
   * Look at IWordListFileChange interface documentation.
   */
  public void addWord(String word, String definition) 
      throws InvalidIdentifierException, InvalidDefinitionException, 
        DuplicateIdentifierException {

    if (this.collator.compare(word.trim(), "") == 0) {
      throw new InvalidIdentifierException(
          "Identifier can not be empty.");
      }
    if (this.collator.compare(definition.trim(), "") == 0) {
      throw new InvalidDefinitionException(
          "Definition can not be empty.");
      }
    
    this.writeLock.lock();
    try {
      ListIterator<Word> iter = this.data.listIterator(0);

      while (iter.hasNext()) {
        Word w = iter.next();
        if (this.collator.compare(word, w.getWord()) == 0) {
          throw new DuplicateIdentifierException(
              "Word \"" + word + "\" already exists!");
          }
        if (this.collator.compare(word, w.getWord()) < 0) {
          iter.previous();
          iter.add(new Word(word, definition));
          return;
          }
        }

      iter.add(new Word(word, definition));
      }
    finally {
      this.writeLock.unlock();
      }
    
    return;
    }

  /**
   * Look at IWordListFileChange interface documentation.
   */
  public void updateWord(String word, String definition) 
      throws InvalidDefinitionException, IdentifierNotExistsException {

    if (this.collator.compare(definition.trim(), "") == 0) {
      throw new InvalidDefinitionException(
          "Definition can not be empty.");
      }

    this.writeLock.lock();
    try {
      ListIterator<Word> iter = this.data.listIterator(0);

      while (iter.hasNext()) {
        Word w = iter.next();
        if (this.collator.compare(word, w.getWord()) == 0) {
          iter.set(new Word(word, definition));
          return;
          }
        }
      
      throw new IdentifierNotExistsException(
          "Identifier \"" + word + "\" was not found.");
      }
    finally {
      this.writeLock.unlock();
      }
    }

  /**
   * Look at IWordListFileChange interface documentation.
   */
  public void eraseWord(String word) throws IdentifierNotExistsException {

    this.writeLock.lock();
    try {
      ListIterator<Word> iter = this.data.listIterator(0);

      while (iter.hasNext()) {
        Word w = iter.next();
        if (this.collator.compare(word, w.getWord()) == 0) {
          iter.remove();
          return;
          }
        }
      
      throw new IdentifierNotExistsException(
          "Identifier \"" + word + "\" was not found.");
      }
    finally {
      this.writeLock.unlock();
      }
    }

  /**
   * Look at IWordListFileChange interface documentation.
   */
  public String getWordDefinition(String word) 
      throws IdentifierNotExistsException {

    this.readLock.lock();
    try {
      for (Word w: this.data) {
        if (word.equals(w.getWord())) {
          return w.getDescription();
          }
        }

      throw new IdentifierNotExistsException(
          "Identifier \"" + word + "\" was not found.");
      }
    finally {
      this.readLock.unlock();
      }
    }

  }
