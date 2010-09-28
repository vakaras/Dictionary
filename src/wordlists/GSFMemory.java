package wordlists;

import java.lang.Character;
import java.lang.Integer;

import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.text.Collator;
//import java.util.ListIterator;

import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//import java.io.DataOutputStream;
//import java.io.BufferedOutputStream;
//import java.io.FileOutputStream;
//import java.io.BufferedWriter;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;

import utils.Word;
import utils.CharacterCollator;

/**
 * Internal class representing single trie node.
 */
class Node {

  private String definition = null;
  private TreeMap<Character, Node> next = null;
  private Integer address = null;

  @SuppressWarnings("unchecked")
  public Node () {
    CharacterCollator collator = CharacterCollator.getInstance();
                                        // FIXME: Find the reason of warning
                                        // and remove suppress.
                                        // FIXME: Collator must be WordList
                                        // dependent, not program dependent.
    next = new TreeMap<Character, Node>(collator);
    }

  /**
   * Returns Node, which is represented by given symbol. If node doesn't 
   * exists throws an exception.
   * @param symbol – node indentifier.
   * @return Node specified by symbol.
   */
  public Node getNextNode(Character symbol) throws Exception {
    // FIXME: Write normal exception handling and throwing.
    if (!this.next.containsKey(symbol))
      throw new Exception("Node doesn't exist.");
    return this.next.get(symbol);
    }
  
  /** 
   * Returns Node, which is represented by given symbol. If node doesn't
   * exists, then creates it.
   * @param symbol – node indentifier.
   * @return Node specified by symbol.
   */
  public Node getCreateNextNode(Character symbol) throws Exception {
    // FIXME: Write normal exception handling and throwing.
    if (!this.next.containsKey(symbol)) {
      Node node = new Node();
      this.next.put(symbol, node);
      }
    return this.next.get(symbol);
    }

  public void assignNextNode(Character symbol, Node node) throws Exception {

    if (this.next.containsKey(symbol)) {
      throw new Exception("Node already assigned!");
      }

    this.next.put(symbol, node);

    return;
    }

  public String getDefinition() {
    return this.definition;
    }
  
  public void setDefinition(String definition) {
    this.definition = definition;
    }
  
  /**
   * Returns true, if exists word, which ends here.
   */
  public boolean isWord() {
    return this.definition != null;
    }

  public LinkedList<Character> getLettersList() {

    Set<Character> keySet = this.next.keySet();
    LinkedList<Character> keys = new LinkedList<Character>();

    for (Character key : keySet)
      keys.add(key);

    return keys;
    }
  
  /**
   * Returns address of first byte of this node representation in file.
   * @return address or null if unknown.
   */
  public Integer getAddress() {
    return this.address;
    }
  
  public void setAddress(Integer address) {
    this.address = address;
    }
  
  }

/**
 * Class implementing word list working with GSF files. (Loads them into
 * memory.)
 */
public class GSFMemory //extends WordList implements
//  IWordListChange, IWordListFileWrite, IWordListFileRead 
{

  // FIXME: Make synchronized!!!

  private Node root = new Node();
  private String filename = null;

  private void write(Node node, RandomAccessFile out) throws Exception {

    for (Character l : node.getLettersList())
      this.write(node.getNextNode(l), out);

    node.setAddress(new Integer((int) out.getFilePointer()));
                                        // TODO: Check if returned offset is
                                        // of byte which is going to be 
                                        // written. Not which was written.
    
    System.out.print("Node " + (node.isWord()?"word ":""));
    System.out.println("("+node.getAddress()+"):");
    for (Character l : node.getLettersList()) {
      out.writeChar(l.charValue());
      out.writeInt(node.getNextNode(l).getAddress());
      System.out.print("  "+l.toString()+": ");
      System.out.println(node.getNextNode(l).getAddress());
      }
    
    if (node.isWord()) {
      out.writeChar(1);
      out.writeUTF(node.getDefinition());
      }
    else {
      out.writeChar(0);
      }

    }

  public void save(String filename) throws Exception {
    //DataOutputStream out = null;
    RandomAccessFile out = null;

    try {
      out = new RandomAccessFile(filename, "rw");
      //out = new DataOutputStream(
      //    new BufferedOutputStream(new FileOutputStream(filename)));

      System.out.println("Writing GSF to file: " + filename);

      out.writeBytes("GSF");
      out.writeInt(0);                  // Placeholder for root node 
                                        // address.
      this.write(this.root, out);       // Dump all nodes.
      out.seek(3);
      out.writeInt(this.root.getAddress());
      System.out.println("Root node address: " + this.root.getAddress());
      }
    finally {
      if (out != null)
        out.close();
      }
    
    this.filename = filename;

    return;
    }

  public void addWord(String word, String definition) throws Exception {

    Node node = this.root;

    for (char c : word.toCharArray()) {
      Character letter = new Character(c);
      node = node.getCreateNextNode(letter);
      }

    if (node.isWord()) {
      throw new Exception("Word " + word + " already exists!");
      }
    else {
      node.setDefinition(definition);
      }
    
    return;
    }

  public void updateWord(String word, String definition) throws Exception {

    Node node = this.root;

    for (char c : word.toCharArray()) {
      Character letter = new Character(c);
      node = node.getNextNode(letter);  // FIXME: Make normal exceptions.
      }
    
    if (node.isWord()) {
      node.setDefinition(definition);
      }
    else {
      throw new Exception("Word not found!");
      }

    return;
    }

  public void eraseWord(String word) throws Exception {

    Node node = this.root;

    for (char c : word.toCharArray()) {
      Character letter = new Character(c);
      node = node.getNextNode(letter);  // FIXME: Make normal exceptions.
      }
    
    if (node.isWord()) {
      node.setDefinition(null);         // FIXME: Make normal node erase.
      }
    else {
      throw new Exception("Word not found!");
      }

    return;
    }

  public String getWordDefinition(String word) throws Exception {

    Node node = this.root;

    for (char c : word.toCharArray()) {
      Character letter = new Character(c);
      node = node.getNextNode(letter);  // FIXME: Make normal exceptions.
      }
    
    if (!node.isWord()) {
      throw new Exception("Word not found!");
      }

    return node.getDefinition();
    }

  public void load(String filename) throws Exception {

    this.filename = filename;

    if (this.filename.endsWith(".dwa")) {
      this.loadDWA();
      }
    else if (this.filename.endsWith(".gsf")) {
      this.loadGSF();
      }
    else {
      throw new Exception("Unknown file type!");
      }
    
    return;
    }

  private void loadGSF() throws Exception {

    RandomAccessFile in = null;

    try {
      in = new RandomAccessFile(this.filename, "r");

      this.root = new Node();

      in.seek(3);                       // Skip "GSF".
      Integer rootAddress = new Integer(in.readInt());

      TreeMap<Integer, Node> nodes = new TreeMap<Integer, Node>();

      try {
        while (true) {
          Node node = new Node();

          node.setAddress(new Integer((int) in.getFilePointer()));

          while (true) {
            char l = in.readChar();
            if (((int) l) == 0) {
              break;
              }
            else if (((int) l) == 1) {
              String definition = in.readUTF();
              node.setDefinition(definition);
              break;
              }
            Integer address = new Integer(in.readInt());
            if (!nodes.containsKey(address)) {
              throw new Exception("Node is missing!");
              }
            node.assignNextNode(new Character(l), nodes.get(address));
            }
          if (nodes.containsKey(node.getAddress())) {
            throw new Exception("Node already read!");
            }
          nodes.put(node.getAddress(), node);
          }
        }
      catch (java.io.EOFException e) {
        }

      this.root = nodes.get(rootAddress);
      }
    finally {
      if (in != null)
        in.close();
      }
    
    return;
    }

  private void loadDWA() throws Exception {

    BufferedReader in = null; 

    try {
      in = new BufferedReader( 
          new InputStreamReader( 
            new FileInputStream(this.filename), "UTF8"));

      this.root = new Node();

      while (true) {
        String str = in.readLine();
        if (str == null)
          break;
        int splitPoint = str.indexOf('=');
        String word = str.substring(0, splitPoint);
        String description = str.substring(splitPoint+1);
        while (true) {
          try {
            this.addWord(word, description);  
            break;
            }
          catch (Exception e) {
            if (word.length() > 2 && word.charAt(word.length()-2) == ' ' &&
                  Character.isDigit(word.charAt(word.length()-1))) {
              char digit = word.charAt(word.length()-1);
              String number = new String(new char[] {digit});
              number = (new Integer(Integer.parseInt(number)+1)
                  ).toString();
              word = word.substring(0, word.length()-1) + number;
              }
            else {
              word = word + " 1";
              }
            }
          }

        }

      }
    finally {
      if (in != null)
        in.close();
      }
    
    return;
    }

  }
