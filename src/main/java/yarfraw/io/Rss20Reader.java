package yarfraw.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.IOUtils;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss20.elements.TRss;
import yarfraw.mapping.backward.impl.ToChannelRss10Impl;
import yarfraw.mapping.backward.impl.ToChannelRss20Impl;
import yarfraw.utils.Utils;
/**
 * Provides a set of function to facilitate reading of an RSS 2.0 feed.
 * @author jliang
 *
 */
public class Rss20Reader extends AbstractBaseIO{
  
  private Unmarshaller _rss20Unmarshaller;
  private Unmarshaller _rss10Unmarshaller;
  private Unmarshaller _atom10Unmarshaller;
  
  private HttpURL _httpUrl = null;
  private HttpClientParams _httpClientParams = null;
  
  public Rss20Reader(File file){
    super(file);
  }
  
  public Rss20Reader(String pathName){
    this(new File(pathName));
  }
  
  public Rss20Reader(URI uri){
    this(new File(uri));
  }
  
  public Rss20Reader(HttpURL httpUrl){
    _httpUrl = httpUrl;
  }
  
  public Rss20Reader(HttpURL httpUrl, HttpClientParams params){
    _httpUrl = httpUrl;
    _httpClientParams = params;
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
  
  /**
   * Reads a channel from the feed file with a custom {@link ValidationEventHandler}
   * 
   * @throws YarfrawException if read operation failed.
   */
  public Channel readChannel(ValidationEventHandler validationEventHandler) throws YarfrawException{
    Unmarshaller u;
    InputStream input = null;
    try {
      input = getStream();
      u = getUnMarshaller();
      u.setEventHandler(validationEventHandler);
      return toChannel(u.unmarshal(input));
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to unmarshal file", e);
    }
    catch (HttpException e) {
      throw new YarfrawException("Unable to read from remote url", e);
    }
    catch (IOException e) {
      throw new YarfrawException("Unable to read", e);
    }finally{
      IOUtils.closeQuietly(input);
    }
  }
  
  @SuppressWarnings("unchecked")
  private Channel toChannel(Object o) throws YarfrawException{
    if(_format == FeedFormat.RSS20){
      return ToChannelRss20Impl.getInstance().execute(((JAXBElement<TRss>)o).getValue().getChannel());
    }else if(_format == FeedFormat.RSS10){
      return  ToChannelRss10Impl.getInstance().execute((RDF)o);
    }else if(_format == FeedFormat.ATOM10){
      //TODO;
      throw new UnsupportedOperationException("TODO");
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
  }
  
  
  
  private InputStream getStream() throws HttpException, IOException{
    InputStream stream = null;
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
  
  /**
   * Reads a channel from the feed file.
   * 
   * @throws YarfrawException if read operation failed.
   */
  public Channel readChannel() throws YarfrawException{
    return readChannel(null);
  }
  
  
  private Unmarshaller getUnMarshaller() throws JAXBException{
    Unmarshaller ret = _rss20Unmarshaller;
    if(_format == FeedFormat.RSS20){
      if(_rss20Unmarshaller==null){
        _rss20Unmarshaller = JAXBContext.newInstance(Utils.RSS20_JAXB_CONTEXT).createUnmarshaller();
      }
      ret = _rss20Unmarshaller;
    }else if(_format == FeedFormat.RSS10){
      if(_rss10Unmarshaller==null){
        _rss10Unmarshaller = JAXBContext.newInstance(Utils.RSS10_JAXB_CONTEXT).createUnmarshaller();
      }
      ret = _rss10Unmarshaller;
    }else if(_format == FeedFormat.ATOM10){
      if(_atom10Unmarshaller==null){
        _atom10Unmarshaller = JAXBContext.newInstance(Utils.ATOM10_JAXB_CONTEXT).createUnmarshaller();
      }
      ret = _atom10Unmarshaller;
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
    
    return ret;
  }
}