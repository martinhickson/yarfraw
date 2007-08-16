package yarfraw.rss20.datamodel;
/**
 * Indicates mal-formness of the rss data model. 
 * @author jliang
 *
 */
public class ValidationException extends Exception{

  private static final long serialVersionUID = 1L;
  public ValidationException(String message){
    super(message);
  }
  
  public ValidationException(String message, Throwable t){
    super(message, t);
  }
}