package wordlists.exceptions;

/**
 * Exception which indicates that word list was damaged and it have to be
 * reloaded before using again.
 */
public class DamagedWordListException extends Exception {

  private static final long serialVersionUID = 1L;

  public DamagedWordListException() {
    this("");
    }
  
  public DamagedWordListException(String message) {
    super(message);
    }
  
  }
