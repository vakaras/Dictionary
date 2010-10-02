package wordlists;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;

import java.io.RandomAccessFile;
import java.io.IOException;

import wordlists.exceptions.*;

import utils.Word;
import utils.CharacterCollator;


/**
 * Class implementing word list working with GSF files, while not loading 
 * them into memory.
 *
 * Note that before wordlist can be used, DWA file must be reffered by
 * calling method load.
 */
public class GSFFile extends WordList implements IWordListFileRead {

  private String filename = null;

  /**
   * Internal class representing single trie node.
   */
  class Node {

    private String definition = null;
    private String word = null;
    private TreeMap<Character, Integer> next = null;
    private Integer address;
    private RandomAccessFile file;

    /**
     * Reads Node from GSF file.
     *
     * @param in – GSF file.
     * @param address – node offset in file.
     */
    public Node(RandomAccessFile in, Integer address) throws IOException {
      this(in, address, "");
      }
    
    /**
     * Reads Node from GSF file.
     *
     * @param in – GSF file.
     * @param address – node offset in file.
     * @param word – what word this node represents.
     */
    private Node(RandomAccessFile in, Integer address, String word) 
        throws IOException {
      CharacterCollator collator = CharacterCollator.getInstance();
                                        // FIXME: Collator must 
                                        // be WordList dependent, 
                                        // not program dependent.
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
    
    /**
     * Returns next node from GSF file.
     *
     * @param symbol – "pointer" to next node in trie.
     * @return next Node by symbol in trie.
     */
    public Node getNextNode(Character symbol) 
        throws NodeNotExistsException, IOException {
      if (!this.next.containsKey(symbol)) {
        throw new NodeNotExistsException(
            "Node referenced by \'" + symbol + "\' doesn't exist.");
        }
      return new Node(this.file, this.next.get(symbol), 
          word + symbol.toString());
      }
    
    /**
     * Returns word definition.
     *
     * @return Word definition or null if isWord is false.
     */
    public String getDefinition() {
      return this.definition;
      }

    /**
     * Returns word identifier.
     *
     * @return Word identifier, which must be unique in this word list.
     */
    public String getWord() {
      return this.word;
      }
    
    /** 
     * Returns if this node represents word.
     *
     * @return true if represent, false – otherwise.
     */
    public boolean isWord() {
      return this.definition != null;
      }
    
    /**
     * Returns symbols with which other trie nodes could be reached from 
     * this node.
     *
     * @return list of letters, which could be used for getNextNode.
     */
    public LinkedList<Character> getLettersList() {
      Set<Character> keySet = this.next.keySet();
      LinkedList<Character> keys = new LinkedList<Character>();

      for (Character key : keySet) {
        keys.add(key);
        }
      
      return keys;
      }
    
    /**
     * Returns node offset in file.
     *
     * @return node offset in file, which was passed to constructor.
     */
    public Integer getAddress() {
      return this.address;
      }

    }

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

    RandomAccessFile in = null;

    try {
      in = new RandomAccessFile(this.filename, "r");
      in.seek(3);
      Node root = new Node(in, new Integer(in.readInt()));
      this.search(root, word, 0, count, result);
      }
    catch (NodeNotExistsException e) {
      throw new WrongFileFormatException(
          "NodeNotExistsException was cached in GSFFile.");
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
  private int search(Node node, String request, int index, int left, 
      LinkedList<Word> result) throws IOException, NodeNotExistsException {

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

  /**
   * Look at IWordListFileRead interface documentation.
   */
  public void load(String filename) {
    this.filename = filename;
    return;
    }

  }
