package yarfraw.rss20.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.rss20.io.Rss20Reader;

/**
 * Convenient utilities methods.
 *  
 * @author jliang
 *
 */
public class Rss20Utils{
    
  /**
   * Read all Rss feed and return them in a list that is in the same order.
   * 
   * @param files - {@link File}s pointing to Rss 2.0 feed file.
   * @return - a list of {@link Channel} 
   * @throws YarfrawException - If there is a failure reading any of the feeds.
   */
  public static List<Channel> readAll(File... files) throws YarfrawException{
    List<Channel> ret = new ArrayList<Channel>();
    if(!ArrayUtils.isEmpty(files)){
      for(File f : files){
        Rss20Reader reader = new Rss20Reader(f);
        ret.add(reader.readChannel());
      }
    }
    return ret;
  }
  
  /**
   * Read a Rss feed in to a {@link Channel} data object.
   * @param file - {@link File} pointing to a Rss 2.0 feed file.
   * @return - A {@link Channel} data object representation of the feed.
   * @throws YarfrawException - If there is a failure reading the feeds.
   */
  public static Channel read(File file) throws YarfrawException{
    List<Channel> ret = readAll(file);
    return ret.size() == 0 ? null : ret.get(0);
  }
  
}