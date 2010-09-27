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
//import java.io.DataOutputStream;
//import java.io.BufferedOutputStream;
//import java.io.FileOutputStream;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.io.BufferedWriter;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;

import utils.Word;

/**
 * Internal class representing single trie node.
 */
class Node {

  private String description = null;
  private TreeMap<Character, Node> next = null;
  private Integer address = null;

  public Node () {
    Collator collator = Collator.getInstance();
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

  public String getDescription() {
    return this.description;
    }
  
  public void setDescription(String description) {
    this.description = description;
    }
  
  /**
   * Returns true, if exists word, which ends here.
   */
  public boolean isWord() {
    return this.description != null;
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
    
    for (Character l : node.getLettersList()) {
      out.writeChar(l.charValue());
      out.writeInt(node.getNextNode(l).getAddress());
      }
    
    if (node.isWord()) {
      out.writeChar(1);
      out.writeUTF(node.getDescription());
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

      out.writeBytes("GSF");
      out.writeInt(0);                  // Placeholder for root node 
                                        // address.
      this.write(this.root, out);       // Dump all nodes.
      }
    finally {
      if (out != null)
        out.close();
      }
    
    this.filename = filename;
    }

  }
