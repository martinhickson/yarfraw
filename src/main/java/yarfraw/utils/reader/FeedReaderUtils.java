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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

public class FeedReaderUtils{
  private FeedReaderUtils(){}
  private static final Log LOG = LogFactory.getLog(FeedReaderUtils.class);
  private static class FeedReaderCaller implements Callable<Channel>{
    private HttpURL _url;
    public FeedReaderCaller(HttpURL url){
      _url = url;
    }
    public Channel call() throws YarfrawException, IOException {
      return new FeedReader(_url).readChannel();
    }
  }
  
  /**
   * Read all Rss feed using an {@link ExecutorService} and return them in a list that is in the same order.
   * @param files - {@link File}s pointing to Rss feed files. 
   * @param executorService - @see {@link ExecutorService}
   * @param urls - @see {@link HttpURL}
   * @return - a list of {@link Channel}
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<Channel> readAll(ExecutorService executorService, HttpURL... urls) 
  throws YarfrawException{
    List<Channel> ret = new ArrayList<Channel>();
    List<Future<Channel>> futures = new ArrayList<Future<Channel>>(); 
    if(!ArrayUtils.isEmpty(urls)){
      for(final HttpURL url : urls){
        futures.add(executorService.submit(new FeedReaderCaller(url)));
      }
    }
    for(Future<Channel> f : futures){
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
   * @return - a list of {@link Channel} 
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<Channel> readAll(FeedFormat format, File... files) throws YarfrawException{
    List<Channel> ret = new ArrayList<Channel>();
    if(!ArrayUtils.isEmpty(files)){
      for(File f : files){
        FeedReader reader = new FeedReader(f, format);
        ret.add(reader.readChannel());
      }
    }
    return ret;
  }
  
  /**
   * Read a Rss feed in to a {@link Channel} data object.
   * 
   * @param file - {@link File} pointing to a Rss feed file.
   * @param format - {@link FeedFormat}
   * @return - A {@link Channel} data object representation of the feed.
   * @throws YarfrawException - If there is a failure reading the feeds.
   */
  public static Channel read(FeedFormat format, File file) throws YarfrawException{
    List<Channel> ret = readAll(format, file);
    return ret.size() == 0 ? null : ret.get(0);
  }
}