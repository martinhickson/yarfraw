package yarfraw.io;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
/**
 * Provides a set of function to facilitate modifications to an RSS 2.0 feed.
 * <br/>
 * *Note* This class is not thread safe.
 * @author jliang
 *
 */
public class Rss20Appender{
  private Rss20Writer _writer;
  private Rss20Reader _reader;
  private int _numItemToKeep = -1;
  
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
  public Rss20Appender setNumItemToKeep(int numItemToKeep) {
    _numItemToKeep = numItemToKeep < 0 ? -1 : numItemToKeep;
    return this;
  }
  
  public Rss20Appender(File file) {
    _writer = new Rss20Writer(file);
    _reader = new Rss20Reader(file);
  }
  public Rss20Appender(String pathName){
    this(new File(pathName));
  }
  
  public Rss20Appender(URI uri){
    this(new File(uri));
  } 
  
  /**
   * Adds an item to the end of the current feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public Rss20Appender addItem(Item item)  throws YarfrawException{
    return addAllItems(Arrays.asList(item));
  }
  
  private List<Item> trimItemsList(List<Item> items){
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
  public Rss20Appender addAllItems(List<Item> items) throws YarfrawException{
    Channel ch = readChannel();
    ch.getItems().addAll(items);
    ch.setItems(trimItemsList(ch.getItems()));
    _writer = new Rss20Writer(_reader._file);
    _writer.writeChannel(ch);
    return this;
  }
  
  /**
   * Adds all items to the end of the current feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public Rss20Appender addAllItems(Item...items) throws YarfrawException{
    return addAllItems(Arrays.asList(items));
  }
  
  /**
   * Remove the item at index <code>index</code> from the feed.
   * 
   * @throws YarfrawException if the appender failed to read or write the feed file.
   */
  public Rss20Appender removeItem(int index) throws YarfrawException{
    Channel ch = readChannel();
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
  public Rss20Appender setItem(int index, Item item) throws YarfrawException{
    Channel ch = readChannel();
    ch.getItems().set(index, item);
    _writer.writeChannel(ch);
    return this;
  }
  
  private Channel readChannel() throws YarfrawException{
    Channel ch = _reader.readChannel(); 
    if(ch.getItems() == null){
      ch.setItems(new ArrayList<Item>());
    }
    return ch;
  }
}