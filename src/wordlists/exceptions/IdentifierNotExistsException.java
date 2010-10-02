package wordlists.exceptions;

/**
 * Exception which is thrown, when there isn't word in word list, which has
 * given identifier.
 */
public class IdentifierNotExistsException 
    extends NonCriticalWordListException {

  private static final long serialVersionUID = 1L;

  public IdentifierNotExistsException() {
    this("");
    }
  
  public IdentifierNotExistsException(String message) {
    super(message);
    }
  
  }
