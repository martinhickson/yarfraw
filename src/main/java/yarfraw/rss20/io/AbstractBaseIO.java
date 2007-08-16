package yarfraw.rss20.io;

import java.io.File;
import java.net.URI;

import yarfraw.core.datamodel.FeedFormat;

abstract class AbstractBaseIO{
  protected File _file;
  protected FeedFormat _format = null;
  
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
  
  public FeedFormat getFormat() {
    return _format;
  }
  public void setFormat(FeedFormat format) {
    _format = format;
  }
  
  
}