package yarfraw.rss20.datamodel;


public class YarfrawException extends Exception{

  private static final long serialVersionUID = 1L;
  public YarfrawException(String message){
    super(message);
  }
  
  public YarfrawException(String message, Throwable t){
    super(message, t);
  }
}