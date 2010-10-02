package wordlists.exceptions;

/**
 * Exception which is thrown, when invalid count is passed to search 
 * function.
 */
public class InvalidCountException extends NonCriticalWordListException {

  private static final long serialVersionUID = 1L;

  public InvalidCountException() {
    this("");
    }
  
  public InvalidCountException(String message) {
    super(message);
    }
  
  }
