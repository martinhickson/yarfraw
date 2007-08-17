package yarfraw.rss20.io;

import java.io.File;
import java.net.URI;

import yarfraw.core.datamodel.FeedFormat;

abstract class AbstractBaseIO{
  protected File _file;
  protected FeedFormat _format = FeedFormat.RSS20; //default
  public AbstractBaseIO(){}
  public AbstractBaseIO(File file){
    if(file == null){
      throw new IllegalArgumentException("File cannot be null");
    }
    _file = file;
  }
  
  public AbstractBaseIO(String pathName){
    this(new File(pathName));
  }
  
  public AbstractBaseIO(URI uri){
    this(new File(uri));
  }
  public File getFile() {
    return _file;
  }
  public void setFile(File file) {
    _file = file;
  }
  
  /**
   * The {@link FeedFormat} this writer should be using.<br/>
   * if this is not set, the default is RSS 2.0 format. <code>null</code> format is ignored  
   * <p/>
   * rss 2.0 is recommended, use other format if you really need to
   */
  public FeedFormat getFormat() {
    return _format;
  }
  /**
   * The {@link FeedFormat} this writer should be using.<br/>
   * if this is not set, the default is RSS 2.0 format. <code>null</code> format is ignored
   * <p/>
   * rss 2.0 is recommended, use other format if you really need to
   *  
   */
  public void setFormat(FeedFormat format) {
    if(format != null){
      _format = format;
    } 
  }
  
}