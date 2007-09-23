package yarfraw.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;

/**
 * This is a special {@link FeedReader} that supports "conditional get".
 * <p>
 * When a client first asks this reader to read from a remote source, this
 * reader automatically holds a reference to the returned {@link ChannelFeed}.
 * <br/>
 * When a client asks this reader to read again from the remote source, this reader
 * first queries the server to check if there's any new changes (by checking its last
 * modified date). If there is new changes, it reads and parse the updated feed and updates
 * the cache {@link ChannelFeed}. If there is no new changes since the last read, it returns
 * the cached {@link ChannelFeed} since there's no new changes.
 * </p>
 * <p>
 * Since this reader only need to perform parsing when there's new changes, it will
 * have better performance than the normal {@link FeedReader} class.
 * </p> 
 * Note that this class is only useful for reading from a remote source. 
 * 
 * <p>
 * for more information about conditional get, see 
 * &lt;a href="http://fishbowl.pastiche.org/2002/10/21/http_conditional_get_for_rss_hackers" />
 * </p>
 * @author jliang
 *
 */
public class CachedFeedReader extends FeedReader{
  
  private static final String ETAG = "ETag";
  private static final String LAST_MODIFIED = "Last-Modified";
  private static final int NOT_MODIFIED_STATUS_CODE = 304;
  private static final String IF_NONE_MATCH = "If-None-Match";
  private static final String IF_MODIFIED_SINCE = "If-Modified-Since";
  private ChannelFeed _cachedChannelFeed;
  private String _lastModified;
  private String _eTag;
  
  /**
   * Reads a channel from a local or remote feed.
   * <br/>
   * This method performs conditional get, if the remote is not modified
   * since last request, the cached feed will be returned.
   * 
   * @return a {@link ChannelFeed} object
   * @throws YarfrawException if read operation failed.
   */
  @Override
  public ChannelFeed readChannel() throws YarfrawException{
    try {
      _cachedChannelFeed = super.readChannel(null);
    } catch (NotModfiedException e) {
      //no changes since last request, no need to update cache
      //just return the cached feed
    }
    return _cachedChannelFeed;
  }
  
  /**
   * Reads a channel from a local or remote feed with a custom {@link ValidationEventHandler}
   *<br/>
   * This method performs conditional get, if the remote is not modified
   * since last request, the cached feed will be returned.
   * 
   * @param validationEventHandler a custom {@link javax.xml.bind.ValidationEventHandler}
   * @return a {@link ChannelFeed} object
   * @throws YarfrawException if read operation failed.
   */
  public ChannelFeed readChannel(ValidationEventHandler validationEventHandler) throws YarfrawException{
    try {
      _cachedChannelFeed = super.readChannel(validationEventHandler);
    } catch (NotModfiedException e) {
      //no changes since last request, no need to update cache
      //just return the cached feed
    }
    return _cachedChannelFeed;
  }
  //a special exception to signal there's no need changes to be parsed
  //the reader can just return the cached version
  class NotModfiedException extends RuntimeException{
    private static final long serialVersionUID = 1L;}
  
  
  @Override
  protected InputStream getStream() throws IOException{
    InputStream stream;
    GetMethod get = new GetMethod(_httpUrl.toString());
    HttpClient client = new HttpClient();
    if(_httpClientParams != null){
      client.setParams(_httpClientParams);
    }
    
    //there's a cached version of the feed, add the request
    //header to perform conditional get
    if(_lastModified != null || _eTag != null){
      get.addRequestHeader(new Header(IF_MODIFIED_SINCE, _lastModified));
      get.addRequestHeader(new Header(IF_NONE_MATCH, _eTag));
    }
    client.executeMethod(get);
    //get the headers
    Header lastModified = get.getResponseHeader(LAST_MODIFIED);
    _lastModified = lastModified == null ? null : lastModified.getValue();
    Header eTag = get.getResponseHeader(ETAG);
    _eTag = eTag == null ? null : eTag.getValue();
    
    if(get.getStatusCode()==NOT_MODIFIED_STATUS_CODE){
      throw new NotModfiedException();
    }
    stream = get.getResponseBodyAsStream();
    return stream; 
  }
    
  public ChannelFeed getCachedChannelFeed() {
    return _cachedChannelFeed;
  }

  public void setCachedChannelFeed(ChannelFeed cachedChannelFeed) {
    _cachedChannelFeed = cachedChannelFeed;
  }

  //constructors from super class, this class is only for remote reads  
  /**
   * Constructs a {@link CachedFeedReader} to read from a local file.
   * <br/>
   * Note the {@link FeedFormat} will be set to default which is RSS 2.0
   * @param uri - the uril that points to the file
   */
  public CachedFeedReader(URI uri){
    super(new File(uri));
  }
  
  /**
   * Constructs a {@link CachedFeedReader} to read from a remote source using Http.
   * <br/>
   * Format detection will be automatically performed. 
   * @param httpUrl - the {@link HttpURL} of the remote source
   * @param params - any {@link HttpClientParams}
   * @throws YarfrawException - if parse failed
   * @throws IOException - if format detection failed
   */
  public CachedFeedReader(HttpURL httpUrl, HttpClientParams params) throws YarfrawException, IOException{
    super(httpUrl, params);
  }
  
  /**
   * Constructs a {@link CachedFeedReader} to read from a remote source using Http.
   * <br/>
   * Format detection will be automatically performed.
   * @param httpUrl - the {@link HttpURL} of the remote source
   * @throws YarfrawException - if parse failed
   * @throws IOException - if format detection failed
   */
  public CachedFeedReader(HttpURL httpUrl) throws YarfrawException, IOException{
    super(httpUrl, null);
  }
  
}