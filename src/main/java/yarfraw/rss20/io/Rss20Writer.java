package yarfraw.rss20.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.io.IOUtils;

import yarfraw.rss20.datamodel.Channel;
import yarfraw.rss20.datamodel.YarfrawException;
import generated.ObjectFactory;
import generated.TRss;
import yarfraw.rss20.utils.Utils;
/**
 * Provides a set of function to facilitate writing to an RSS 2.0 feed.
 * <br/>
 * *Note* This class is not thread safe.
 * @author jliang
 *
 */
public class Rss20Writer extends AbstractBaseIO{
  
  Marshaller _m;
  
  public Rss20Writer(File file){
    super(file);
  }
  
  public Rss20Writer(String pathName){
    this(new File(pathName));
  }
  
  public Rss20Writer(URI uri){
    this(new File(uri));
  }
  
  /**
   * Writes a channel to the feed file with a custom {@link ValidationEventHandler}
   * 
   * @throws YarfrawException if write operation failed.
   */
  public void writeChannel(Channel channel) throws YarfrawException{
    writeChannel(channel, null);
  }
  
  /**
   * Writes a channel to the feed file.
   * 
   * @throws YarfrawException if write operation failed.
   */
  public void writeChannel(Channel channel, ValidationEventHandler validationEventHandler) throws YarfrawException{
    FileOutputStream out = null;
    try {
      Marshaller m = getMarshaller();
      m.setEventHandler(validationEventHandler);
      TRss rss = new TRss();
      rss.setVersion(2.0d);
      rss.setChannel(channel.toTChannelJAXB().getValue());
      out = new FileOutputStream(_file);
      m.marshal(new ObjectFactory().createRss(rss), out);
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to write channel", e);
    }
    catch (FileNotFoundException e) {
      throw new YarfrawException("Unable to write channel", e);
    }finally{
      IOUtils.closeQuietly(out);
    }
  }
  
  private Marshaller getMarshaller() throws JAXBException{
    if(_m==null){
      _m = JAXBContext.newInstance(Utils.JAXB_CONTEXT).createMarshaller();
    }
    return _m;
  }
}