package wordlists;

/**
 * Interface defining word list, which could be written to file.
 */
public interface IWordListFileWrite {

  /**
   * Save word list to file.
   * @param filename – path to file to which save the dictionary.
   */
  void save(String filename) throws Exception;

  }
