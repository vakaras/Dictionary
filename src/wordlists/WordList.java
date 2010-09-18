package wordlists;

/**
 * Abstract class inmplementing the most generic word list.
 */
public abstract class WordList implements IWordList {

  public abstract String search(String word) throws Exception;

  }
