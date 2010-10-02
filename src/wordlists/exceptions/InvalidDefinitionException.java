package wordlists.exceptions;

/**
 * Exception which is thrown, when invalid definition is passed to 
 * function.
 */
public class InvalidDefinitionException 
    extends NonCriticalWordListException {

  private static final long serialVersionUID = 1L;

  public InvalidDefinitionException() {
    this("");
    }
  
  public InvalidDefinitionException(String message) {
    super(message);
    }
  
  }
