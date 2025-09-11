package yarfraw.utils.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

public class FeedReaderUtils{
  private FeedReaderUtils(){}
  private static final Log LOG = LogFactory.getLog(FeedReaderUtils.class);
  private static class FeedReaderCaller implements Callable<ChannelFeed>{
    private HttpURL _url;
    public FeedReaderCaller(HttpURL url){
      _url = url;
    }
    public ChannelFeed call() throws YarfrawException, IOException {
      return new FeedReader(_url).readChannel();
    }
  }
  
  /**
   *  Static method for reading feed(s) remotely. This method will submit a Callable object to the input
   *  ExecutorService for every input url and will only return when it finishes reading all requested feeds.
   *  The ChannelFeeds in the returned list will in the exact same order as the input url. 
   *  If for whatever reasons the method fails to read a feed, the corresponding ChannelFeed in the list will be null. 
   *  <br/>
   *  This method will detect the formats automatically, but since it does not remember any states, 
   *  it has to performance format detection every time it is called.
   *  It is recommended that you keep an instance of the reader in memory so the reader can re-use the detected
   *  formats.
   * @param files - {@link File}s pointing to Rss feed files. 
   * @param executorService - @see {@link ExecutorService}
   * @param urls - @see {@link HttpURL}
   * @return - a list of {@link ChannelFeed}
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<ChannelFeed> readAll(ExecutorService executorService, HttpURL... urls) 
  throws YarfrawException{
    List<ChannelFeed> ret = new ArrayList<ChannelFeed>();
    List<Future<ChannelFeed>> futures = new ArrayList<Future<ChannelFeed>>(); 
    if(!ArrayUtils.isEmpty(urls)){
      for(final HttpURL url : urls){
        futures.add(executorService.submit(new FeedReaderCaller(url)));
      }
    }
    for(Future<ChannelFeed> f : futures){
      try {
        ret.add(f.get());
      }
      catch (InterruptedException e) {
        LOG.error("Interrupted exception received", e);
        ret.add(null);
      }
      catch (ExecutionException e) {
        LOG.error("Execution exception received", e);
        ret.add(null);
      }
    }
    return ret;
  }
  
  /**
   * Read all Rss feed and return them in a list that is in the same order.
   * 
   * @param files - {@link File}s pointing to Rss feed files.
   * @param format - {@link FeedFormat}
   * @return - a list of {@link ChannelFeed} 
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<ChannelFeed> readAll(FeedFormat format, File... files) throws YarfrawException{
    List<ChannelFeed> ret = new ArrayList<ChannelFeed>();
    if(!ArrayUtils.isEmpty(files)){
      for(File f : files){
        FeedReader reader = new FeedReader(f, format);
        ret.add(reader.readChannel());
      }
    }
    return ret;
  }
  
  /**
   * Read a Rss feed in to a {@link ChannelFeed} data object.
   * 
   * @param file - {@link File} pointing to a Rss feed file.
   * @param format - {@link FeedFormat}
   * @return - A {@link ChannelFeed} data object representation of the feed.
   * @throws YarfrawException - If there is a failure reading the feeds.
   */
  public static ChannelFeed read(FeedFormat format, File file) throws YarfrawException{
    List<ChannelFeed> ret = readAll(format, file);
    return ret.size() == 0 ? null : ret.get(0);
  }
}