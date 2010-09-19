package wordlists;

import java.util.LinkedList;

import utils.Word;

/**
 * Interface defining the most generic word list.
 */
public interface IWordList {

  /**
   * Simple search in word list.
   * @param word – searched word part.
   * @return word definition.
   */
  LinkedList<Word> search(String word, int count) throws Exception;

  }

