package tests.runnable;

import java.util.LinkedList;

import utils.Word;

public class RunnableAdd implements Runnable {

  private wordlists.IWordListChange wordList = null;
  private Throwable exception = null;
  private String word = null;
  private String definition = null;

  public RunnableAdd(
      wordlists.IWordListChange wordList, String word, String definition) {
    this.wordList = wordList;
    this.word = word;
    this.definition = definition;
    }
  
  public Throwable getException() {
    return this.exception;
    }
  
  public void run() {
    try {
      //System.out.println("Add started: \"" + this.word + "\"");
      this.wordList.addWord(this.word, this.definition);
      //System.out.println("Add finished: \"" + this.word + "\"");
      }
    catch (Throwable e) {
      this.exception = e;
      }
    }
    
  }
