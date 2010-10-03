package wordlists.exceptions;

/**
 * Exception which is thrown, when is tried to create node which already
 * exists.
 */
public class DuplicateNodeException extends DamagedWordListException {

  private static final long serialVersionUID = 1L;

  public DuplicateNodeException() {
    this("");
    }
  
  public DuplicateNodeException(String message) {
    super(message);
    }
  
  }
