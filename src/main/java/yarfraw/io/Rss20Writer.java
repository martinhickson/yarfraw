package yarfraw.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.io.IOUtils;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TRss;
import yarfraw.mapping.forward.impl.ToRss10ChannelImpl;
import yarfraw.mapping.forward.impl.ToRss10ChannelItemImpl;
import yarfraw.mapping.forward.impl.ToRss20ChannelImpl;
import yarfraw.utils.Utils;
/**
 * Provides a set of function to facilitate writing to an RSS 2.0 feed.
 * <br/>
 * *Note* This class is not thread safe.
 * @author jliang
 *
 */
public class Rss20Writer extends AbstractBaseIO{
  private static final ObjectFactory RSS20_FACTORY = new ObjectFactory();
  private static final yarfraw.generated.rss10.elements.ObjectFactory RSS10_FACTORY = 
    new yarfraw.generated.rss10.elements.ObjectFactory();
  private Marshaller _rss20Marshaller;
  private Marshaller _rss10Marshaller;
  private Marshaller _atom10Marshaller;
  
  
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
      out = new FileOutputStream(_file);
      m.marshal(getJaxbElementFromFormat(channel), out);
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to write channel", e);
    }
    catch (FileNotFoundException e) {
      throw new YarfrawException("Unable to write channel", e);
    }finally{
      IOUtils.closeQuietly(out);
    }
  }
  
  private Object getJaxbElementFromFormat(Channel channel) throws YarfrawException{
    if(_format == FeedFormat.RSS20){
      TRss rss = RSS20_FACTORY.createTRss();
      rss.setVersion(2.0d);
      rss.setChannel(ToRss20ChannelImpl.getInstance().execute(channel).getValue());
      return RSS20_FACTORY.createRss(rss);
    }else if(_format == FeedFormat.RSS10){
      RDF rdf = RSS10_FACTORY.createRDF();
      List<Object> elementList = rdf.getChannelOrItemOrTextinput();
      elementList.add(ToRss10ChannelImpl.getInstance().execute(channel));
      if(channel.getItems() != null){
        for(Item item : channel.getItems()){
          if(item != null){
            elementList.add(ToRss10ChannelItemImpl.getInstance().execute(item));
          }
        }
      }
      return rdf;
    }else if(_format == FeedFormat.ATOM10){
      //TODO
      throw new UnsupportedOperationException("Not yet implemented");
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
  }
  
  private Marshaller getMarshaller() throws JAXBException{
    Marshaller ret = _rss20Marshaller;
    if(_format == FeedFormat.RSS20){
      if(_rss20Marshaller==null){
        _rss20Marshaller = JAXBContext.newInstance(Utils.RSS20_JAXB_CONTEXT).createMarshaller();
      }
      ret = _rss20Marshaller;
    }else if(_format == FeedFormat.RSS10){
      if(_rss10Marshaller==null){
        _rss10Marshaller = JAXBContext.newInstance(Utils.RSS10_JAXB_CONTEXT).createMarshaller();
      }
      ret = _rss10Marshaller;
    }else if(_format == FeedFormat.ATOM10){
      if(_atom10Marshaller==null){
        _atom10Marshaller = JAXBContext.newInstance(Utils.ATOM10_JAXB_CONTEXT).createMarshaller();
      }
      ret = _atom10Marshaller;
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
    
    return ret;
  }
}