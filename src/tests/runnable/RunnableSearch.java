package tests.runnable;

import java.util.LinkedList;

import utils.Word;

public class RunnableSearch implements Runnable {

  private wordlists.IWordList wordList = null;
  private String request = null;
  private int count = 0;
  private LinkedList<Word> result = null;
  private Throwable exception = null;

  public RunnableSearch(wordlists.IWordList wordList, String request, 
      int count) {
    this.wordList = wordList;
    this.request = request;
    this.count = count;
    }
  
  public LinkedList<Word> getResult() {
    return this.result;
    }

  public Throwable getException() {
    return this.exception;
    }
  
  public void run() {
    try {
      //System.out.println("Search started: \"" + this.request + "\"");
      this.result = this.wordList.search(this.request, this.count);
      //System.out.println("Search finished: \"" + this.request + "\"");
      }
    catch (Throwable e) {
      this.exception = e;
      }
    }
    
  }
