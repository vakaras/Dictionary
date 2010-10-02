package wordlists.exceptions;

/**
 * Exception which is thrown, when trying to add word with identifier which 
 * already exists in word list.
 */
public class DuplicateIdentifierException 
    extends NonCriticalWordListException {

  private static final long serialVersionUID = 1L;

  public DuplicateIdentifierException() {
    this("");
    }
  
  public DuplicateIdentifierException(String message) {
    super(message);
    }
  
  }
