package yarfraw.io;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.SAXParser;

import org.apache.commons.httpclient.HttpURL;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
/**
 * Provides a set of function to facilitate parsing of a RSS feed.
 * @author jliang
 *
 */
public class FeedParserReader extends AbstractBaseFeedParser{
  
  public FeedParserReader(File file){
    super(file);
  }
  
  public FeedParserReader(String pathName){
    super(new File(pathName));
  }
  
  public FeedParserReader(URI uri){
    super(new File(uri));
  }
  
  public FeedParserReader(HttpURL httpUrl){
    super(httpUrl, null);
  }
  
  public Channel parseChannel(SAXParser saxParser) throws YarfrawException{
    
    return null;
  }
  
}