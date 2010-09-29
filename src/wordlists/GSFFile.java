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
      FileNode root = new FileNode(in, 3);
      this.search(root, word, 0, count, result);
      }
    finally {
      if (in != null) {
        in.close();
        }
      }

    return result;
    }

  private int search(FileNode node, String request, int index, int left, 
      LinkedList<Word> result) throws Exception {
    throw new Exception("Not implemented!");
    }

  public void load(String filename) {
    this.filename = filename;
    return;
    }

  }
