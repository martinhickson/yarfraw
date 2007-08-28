package yarfraw.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.utils.FeedFormatDetector;
/**
 * Provides a set of function to facilitate parsing of a RSS feed.
 * @author jliang
 *
 */
abstract class AbstractBaseFeedParser extends AbstractBaseIO{
  
  protected HttpURL _httpUrl = null;
  protected HttpClientParams _httpClientParams = null;
  public AbstractBaseFeedParser(File file, FeedFormat format){
    super(file, format);
  }
  
  public AbstractBaseFeedParser(String pathName, FeedFormat format){
    super(new File(pathName), format);
  }
  
  public AbstractBaseFeedParser(URI uri, FeedFormat format){
    super(new File(uri), format);
  } 
  public AbstractBaseFeedParser(File file){
    super(file);
  }
  
  public AbstractBaseFeedParser(String pathName){
    this(new File(pathName));
  }
  
  public AbstractBaseFeedParser(URI uri){
    this(new File(uri));
  }
  
  public AbstractBaseFeedParser(HttpURL httpUrl) throws YarfrawException, IOException{
    this(httpUrl, null);
  }
  
  public AbstractBaseFeedParser(HttpURL httpUrl, HttpClientParams params) throws YarfrawException, IOException{
    _httpUrl = httpUrl;
    _httpClientParams = params;
    //detect format automatically
    _format = FeedFormatDetector.getFormat(getStream());
  }
  
  public HttpClientParams getHttpClientParams() {
    return _httpClientParams;
  }

  public void setHttpClientParams(HttpClientParams httpClientParams) {
    _httpClientParams = httpClientParams;
  }

  /**
   * Is the reader reading the feed from a remote http link.
   * @return true if reading remotely<br/>
   * false if reading from local file
   */
  public boolean isRemoteRead(){
    return _httpUrl != null;
  }
    
  
  protected InputStream getStream() throws IOException{
    InputStream stream;
    if(isRemoteRead()){
      GetMethod get = new GetMethod(_httpUrl.toString());
      get.setFollowRedirects(true);
      HttpClient client = new HttpClient();
      if(_httpClientParams != null){
        client.setParams(_httpClientParams);
      }
      client.executeMethod(get);
      stream = get.getResponseBodyAsStream();
    }else{
      return new FileInputStream(_file);
    }
    
    return stream; 
  }

  
}