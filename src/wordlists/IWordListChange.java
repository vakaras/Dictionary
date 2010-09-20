package wordlists;

/**
 * Interface defining word list, which can be changed.
 */
public interface IWordListChange {

  /**
   * Add new word definition to word list.
   * @param word – word, for which definition is added (unique for word 
   * list).
   * @param definition – word definition.
   */
  void addWord(String word, String definition) throws Exception;

  /**
   * Change word definition.
   * @param word – word, which definition is needed to update. (Unique, 
   * matches fully.)
   * @param definition – word new definition.
   */
  void updateWord(String word, String definition) throws Exception;

  /**
   * Remove word definition from word list.
   * @param word – word, which definition to remove.
   */
  void eraseWord(String word) throws Exception;

  /** Get word definition. Word must match fully.
   * @param word – word, which definition to return.
   */
  String getWordDefinition(String word) throws Exception;

  }
