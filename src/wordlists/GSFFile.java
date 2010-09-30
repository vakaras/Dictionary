package wordlists;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;

import java.io.RandomAccessFile;

import utils.Word;
import utils.CharacterCollator;

/**
 * Internal class representing single trie node.
 */
class FileNode {

  private String definition = null;
  private String word = null;
  private TreeMap<Character, Integer> next = null;
  private Integer address;
  private RandomAccessFile file;

  public FileNode(RandomAccessFile in, Integer address) throws Exception {
    this(in, address, "");
    }
  
  private FileNode(RandomAccessFile in, Integer address, String word) 
      throws Exception {
    CharacterCollator collator = CharacterCollator.getInstance();
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.
    this.next = new TreeMap<Character, Integer>(collator);
    this.word = word;
    this.address = address;
    this.file = in;

    in.seek(address);
    
    while (true) {
      char l = in.readChar();
      if (((int) l) == 0) {
        break;
        }
      else if (((int) l) == 1) {
        this.definition = in.readUTF();
        break;
        }
      this.next.put(new Character(l), new Integer(in.readInt()));
      }

    }
  
  public FileNode getNextNode(Character symbol) throws Exception {
    if (!this.next.containsKey(symbol)) {
      throw new Exception("Node doesn't exist.");
      }
    return new FileNode(this.file, this.next.get(symbol), 
        word + symbol.toString());
    }
  
  public String getDefinition() {
    return this.definition;
    }

  public String getWord() {
    return this.word;
    }
  
  public boolean isWord() {
    return this.definition != null;
    }
  
  public LinkedList<Character> getLettersList() {
    Set<Character> keySet = this.next.keySet();
    LinkedList<Character> keys = new LinkedList<Character>();

    for (Character key : keySet) {
      keys.add(key);
      }
    
    return keys;
    }
  
  public Integer getAddress() {
    return this.address;
    }

  }

/**
 * Class implementing word list working with GSF files. (Not loading them
 * into memory.)
 */
public class GSFFile extends WordList implements IWordListFileRead {

  private String filename = null;
  private RandomAccessFile file = null;

  public LinkedList<Word> search(String word, int count) throws Exception {

    LinkedList<Word> result = new LinkedList<Word>();

    RandomAccessFile in = null;

    try {
      in = new RandomAccessFile(this.filename, "r");
      in.seek(3);
      FileNode root = new FileNode(in, new Integer(in.readInt()));
      this.search(root, word, 0, count, result);
      }
    finally {
      if (in != null) {
        in.close();
        }
      }

    return result;
    }

  /**
   * Recursively search for a word.
   * @param node – node, which is now being processed.
   * @param request – word to search for.
   * @param index – index of letter, which is being processed.
   * @param left – how many definitions left to find.
   * @param result – list, to which result will be added.
   * @return how many words were added to result.
   */
  private int search(FileNode node, String request, int index, int left, 
      LinkedList<Word> result) throws Exception {

    int added = 0;

    if (request.length() > index) {
      Character letter = new Character(request.charAt(index));

      CharacterCollator collator = CharacterCollator.getInstance();

      for (Character l : node.getLettersList()) {
        if (collator.compare(letter, l) == 0) {
          int addedNow = this.search(node.getNextNode(l), request, index+1,
              left, result);
          added += addedNow;
          left -= addedNow;
          if (left <= 0) {
            return added;
            }
          }
        if (collator.compare(letter, l) < 0) {
          int addedNow = this.search(node.getNextNode(l), "", index+1,
              left, result);
          added += addedNow;
          left -= addedNow;
          if (left <= 0) {
            return added;
            }
          }
        }
      }
    else {
      if (node.isWord()) {
        result.add(new Word(node.getWord(), node.getDefinition()));
        added++;
        left--;
        if (left <= 0) {
          return added;
          }
        }
      for (Character l : node.getLettersList()) {
        int addedNow = this.search(node.getNextNode(l), request, index+1,
            left, result);
        added += addedNow;
        left -= addedNow;
        if (left <= 0) {
          return added;
          }
        }
      }

    return added;
    }

  public void load(String filename) {
    this.filename = filename;
    return;
    }

  }
