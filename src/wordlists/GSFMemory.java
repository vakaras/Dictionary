package wordlists;

import java.lang.Character;
import java.lang.Integer;

import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.ListIterator;
//import java.text.Collator;

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
  private TreeMap<String, Node> next = new TreeMap<String, Node>();
  private Integer address = null;

  /**
   * Returns Node, which is represented by given symbol. If node doesn't 
   * exists throws an exception.
   * @param symbol – node indentifier.
   * @return Node specified by symbol.
   */
  public Node getNextNode(char symbol) throws Exception {
    String tmp = new String(new char[] {symbol});
    return this.getNextNode(tmp);
    }
  
  /** 
   * Returns Node, which is represented by given symbol. If node doesn't
   * exists throws an exception.
   * @param symbol – string, node indentifier, which length is 1.
   * @return Node specified by symbol.
   */
  private Node getNextNode(String symbol) throws Exception {
    assert symbol.length() == 1;
    throw new Exception("Not implemented!");
    }

  /** 
   * Returns Node, which is represented by given symbol. If node doesn't
   * exists, then creates it.
   * @param symbol – node indentifier.
   * @return Node specified by symbol.
   */
  public Node getCreateNextNode(char symbol) throws Exception {
    String tmp = new String(new char[] {symbol});
    return this.getCreateNextNode(tmp);
    }

  /** 
   * Returns Node, which is represented by given symbol. If node doesn't
   * exists, then creates it.
   * @param symbol – String, node indentifier, which length is 1.
   * @return Node specified by symbol.
   */
  private Node getCreateNextNode(String symbol) throws Exception {
    assert symbol.length() == 1;
    throw new Exception("Not implemented!");
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

  public char[] getLettersList() {
    Set<String> stringKeys = this.next.keySet();

    char[] keys = new char[stringKeys.size()];

    int i = 0;
    for (String key : stringKeys) {
      keys[i] = key.charAt(0);
      }

    return keys;
    }
  
  /**
   * Returns address of first byte of this node representation in file.
   * @return address or null if unknown.
   */
  public Integer getAddress() {
    return this.address;
    }
  
  public setAddress(Intger address) {
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

  }
