package wordlists;

import java.util.LinkedList;

import utils.Word;

/**
 * Abstract class inmplementing the most generic word list.
 */
public abstract class WordList implements IWordList {

  public abstract LinkedList<Word> search(String word, int count) 
    throws Exception;

  }
