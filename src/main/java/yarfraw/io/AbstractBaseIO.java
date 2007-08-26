package yarfraw.io;

import java.io.File;
import java.net.URI;

import yarfraw.core.datamodel.FeedFormat;

abstract class AbstractBaseIO{
  protected File _file;
  protected FeedFormat _format = FeedFormat.RSS20; //default
  public AbstractBaseIO(){}
  
  public AbstractBaseIO(File file, FeedFormat format){
    if(file == null){
      throw new IllegalArgumentException("File cannot be null");
    }
    _file = file;
    setFormat(format);
  }
  
  public AbstractBaseIO(String pathName, FeedFormat format){
    this(new File(pathName), format);
  }
  
  public AbstractBaseIO(URI uri, FeedFormat format){
    this(new File(uri), format);
  }
  
  public AbstractBaseIO(File file){
    this(file, FeedFormat.RSS20);
  }
  
  public AbstractBaseIO(String pathName){
    this(new File(pathName), FeedFormat.RSS20);
  }
  
  public AbstractBaseIO(URI uri){
    this(new File(uri), FeedFormat.RSS20);
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