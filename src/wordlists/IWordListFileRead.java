package wordlists;

/**
 * Interface definining word list, which before using must be loaded from
 * file.
 */
public interface IWordListFileRead {

  /**
   * Load word list from file.
   * @param filename – path to file, from which to load word list.
   */
  void load(String filename);

  }
