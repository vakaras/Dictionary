package wordlists.exceptions;

/**
 * Exception which is thrown, when is detected that word list file format
 * is wrong.
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
