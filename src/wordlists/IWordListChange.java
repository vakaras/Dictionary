/**
 * Interface defining word list, which can be changed.
 */
public interface IWordListChange {

  /**
   * Add new word definition to word list.
   * @param word – word, for which definition is added.
   * @param definition – word definition.
   * @return unique word indentifier, by which later search could be 
   * performed.
   */
  String addWord(String word, String definition);

  /**
   * Change word definition.
   * @param word – word, which definition is needed to update.
   * @param definition – word new definition.
   * @return old word definition.
   */
  String updateWord(String word, String definition);

  /**
   * Remove word definition from word list.
   * @param word – word, which definition to remove.
   * @return definition of removed word.
   */
  String eraseWord(String word);

  }
