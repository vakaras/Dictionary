package wordlists;

import java.util.LinkedList;

import utils.Word;

/**
 * Abstract class inmplementing the most generic word list.
 */
public abstract class WordList implements IWordList {

  /**
   * Look at IWordList interface documentation.
   */
  public abstract LinkedList<Word> search(String word, int count) 
    throws Exception;

  }
