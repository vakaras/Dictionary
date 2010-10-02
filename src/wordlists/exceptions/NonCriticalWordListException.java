package wordlists.exceptions;

/**
 * Exception which indicates problem which is not critical and after which
 * word list can be used without any changes.
 */
public class NonCriticalWordListException extends Exception {

  private static final long serialVersionUID = 1L;

  public NonCriticalWordListException() {
    this("");
    }
  
  public NonCriticalWordListException(String message) {
    super(message);
    }
  
  }
