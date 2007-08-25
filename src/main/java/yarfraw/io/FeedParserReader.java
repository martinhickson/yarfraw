package yarfraw.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpURL;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.mapping.backward.ToChannelDOM;
import yarfraw.utils.XMLUtils;
/**
 * Provides a set of function to facilitate parsing of a RSS feed using a custom parser.
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
  
  public Channel parseChannel(ToChannelDOM toChannelDOMMapper) throws YarfrawException{
    try {
      Document doc = XMLUtils.parseXml(getStream(), false, true);
      toChannelDOMMapper.setFeedFormat(_format);
      return toChannelDOMMapper.execute(doc);
    }
    catch (ParserConfigurationException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }catch (SAXException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }catch (IOException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }
  }
}