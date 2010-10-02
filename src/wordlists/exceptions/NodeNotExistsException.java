package wordlists.exceptions;

/**
 * Exception which is thrown, when is tried to access node which doesn't
 * exist.
 */
public class NodeNotExistsException extends DamagedWordListException {

  private static final long serialVersionUID = 1L;

  public NodeNotExistsException() {
    this("");
    }
  
  public NodeNotExistsException(String message) {
    super(message);
    }
  
  }
