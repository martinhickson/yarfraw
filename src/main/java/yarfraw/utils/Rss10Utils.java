package yarfraw.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

/**
 * Convenient utilities methods.
 *  
 * @author jliang
 *
 */
public class Rss10Utils{
    
  /**
   * Read all Rss feed and return them in a list that is in the same order.
   * 
   * @param files - {@link File}s pointing to Rss 2.0 feed file.
   * @return - a list of {@link ChannelFeed} 
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<ChannelFeed> readAll(File... files) throws YarfrawException{
    List<ChannelFeed> ret = new ArrayList<ChannelFeed>();
    if(!ArrayUtils.isEmpty(files)){
      for(File f : files){
        FeedReader reader = new FeedReader(f);
        reader.setFormat(FeedFormat.RSS10);
        ret.add(reader.readChannel());
      }
    }
    return ret;
  }
  
  /**
   * Read a Rss feed in to a {@link ChannelFeed} data object.
   * @param file - {@link File} pointing to a Rss 2.0 feed file.
   * @return - A {@link ChannelFeed} data object representation of the feed.
   * @throws YarfrawException - If there is a failure reading the feeds.
   */
  public static ChannelFeed read(File file) throws YarfrawException{
    List<ChannelFeed> ret = readAll(file);
    return ret.size() == 0 ? null : ret.get(0);
  }
  
}