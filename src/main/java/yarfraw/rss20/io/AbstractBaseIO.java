package yarfraw.rss20.io;

import java.io.File;
import java.net.URI;

abstract class AbstractBaseIO{
  protected File _file;
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
  
}