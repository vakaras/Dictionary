package wordlists.exceptions;

/**
 * Exception which is thrown, when invalid word identifier is passed to 
 * function.
 */
public class InvalidIdentifierException 
    extends NonCriticalWordListException {

  private static final long serialVersionUID = 1L;

  public InvalidIdentifierException() {
    this("");
    }
  
  public InvalidIdentifierException(String message) {
    super(message);
    }
  
  }
