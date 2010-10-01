package wordlists;

import java.util.LinkedList;

import utils.Word;

/**
 * Interface defining the most generic word list.
 */
public interface IWordList {

  /**
   * Simple search in word list. 
   *
   * Searches for given word (or its part) in word list and returns the 
   * nearest count or less words with they definitions.
   *
   * @param word – searched word or its part.
   * @param count – how many results to return.
   * @return list of words and they definitions; list length is less or 
   * equal to count.
   */
  LinkedList<Word> search(String word, int count) throws Exception;

  }

