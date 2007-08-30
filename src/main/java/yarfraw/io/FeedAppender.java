package yarfraw.io;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.YarfrawException;
/**
 * Provides a set of function to facilitate modifications to an RSS 2.0 feed.
 * <br/>
 * *Note* This class is not thread safe.
 * @author jliang
 *
 */
public class FeedAppender{
  private FeedWriter _writer;
  private FeedReader _reader;
  private int _numItemToKeep = -1;

  public FeedAppender(File file, FeedFormat format) {
    _writer = new FeedWriter(file);
    _reader = new FeedReader(file);
    setFormat(format);
  }
  public FeedAppender(String pathName, FeedFormat format){
    this(new File(pathName), format);
  }
  
  public FeedAppender(URI uri, FeedFormat format){
    this(new File(uri), format);
  }
  
  public FeedAppender(File file) {
    this(file, FeedFormat.RSS20);
  }
  public FeedAppender(String pathName){
    this(new File(pathName), FeedFormat.RSS20);
  }
  
  public FeedAppender(URI uri){
    this(new File(uri), FeedFormat.RSS20);
  } 
  
  /**
   * The {@link FeedFormat} this writer should be using.<br/>
   * if this is not set, the default is RSS 2.0 format. <code>null</code> format is ignored  
   * <p/>
   * rss 2.0 is recommended, use other format if you really need to
   */
  public FeedFormat getFormat() {
    return _reader.getFormat();
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
      _reader.setFormat(format);
      _writer.setFormat(format);
    } 
  }
  /**
   * Maximum number of items to keep in a feed. If the number of actual items
   * in the feed is greater than this number, then the appender will remove 
   * items from the beginning of the items list to ensure there is at most 
   * <code>numItemToKeep</code> items in the channel.
   * 
   */
  public int getNumItemToKeep() {
    return _numItemToKeep;
  }
  /**
   * Maximum number of items to keep in a feed. If the number of actual items
   * in the feed is greater than this number, then the appender will remove 
   * items from the beginning of the items list to ensure there is at most 
   * <code>numItemToKeep</code> items in the channel.
   * 
   */
  public FeedAppender setNumItemToKeep(int numItemToKeep) {
    _numItemToKeep = numItemToKeep < 0 ? -1 : numItemToKeep;
    return this;
  }
  

  
  /**
   * Adds an item to the end of the current feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public FeedAppender addItem(ItemEntry item)  throws YarfrawException{
    return addAllItems(Arrays.asList(item));
  }
  
  private List<ItemEntry> trimItemsList(List<ItemEntry> items){
    if(_numItemToKeep != -1 && CollectionUtils.isNotEmpty(items)
            && items.size() > _numItemToKeep){
      return items.subList(items.size() - _numItemToKeep, items.size());
    }
    return items;
  }
  
  /**
   * Adds all items to the end of the current feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public FeedAppender addAllItems(List<ItemEntry> items) throws YarfrawException{
    ChannelFeed ch = readChannel();
    ch.getItems().addAll(items);
    ch.setItems(trimItemsList(ch.getItems()));
    _writer = new FeedWriter(_reader._file);
    _writer.writeChannel(ch);
    return this;
  }
  
  /**
   * Adds all items to the end of the current feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public FeedAppender addAllItems(ItemEntry...items) throws YarfrawException{
    return addAllItems(Arrays.asList(items));
  }
  
  /**
   * Remove the item at index <code>index</code> from the feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public FeedAppender removeItem(int index) throws YarfrawException{
    ChannelFeed ch = readChannel();
    ch.getItems().remove(index);
    ch.setItems(trimItemsList(ch.getItems()));
    _writer.writeChannel(ch);
    return this;
  }

  /**
   * Set the item at index <code>index</code> to be the input <code>item</code>
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public FeedAppender setItem(int index, ItemEntry item) throws YarfrawException{
    ChannelFeed ch = readChannel();
    ch.getItems().set(index, item);
    _writer.writeChannel(ch);
    return this;
  }
  
  private ChannelFeed readChannel() throws YarfrawException{
    ChannelFeed ch = _reader.readChannel(); 
    if(ch.getItems() == null){
      ch.setItems(new ArrayList<ItemEntry>());
    }
    return ch;
  }
}