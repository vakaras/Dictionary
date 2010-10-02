package wordlists.exceptions;

/**
 * Exception which is thrown, when is detected that word list file format
 * is wrong.
 */
public class WrongFileFormatException extends DamagedWordListException {

  private static final long serialVersionUID = 1L;

  public WrongFileFormatException() {
    this("");
    }
  
  public WrongFileFormatException(String message) {
    super(message);
    }
  
  }
