package yarfraw.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
import yarfraw.mapping.forward.impl.ToAtom10ChannelImpl;
import yarfraw.mapping.forward.impl.ToRss10ChannelImpl;
import yarfraw.mapping.forward.impl.ToRss10ChannelItemImpl;
import yarfraw.mapping.forward.impl.ToRss20ChannelImpl;
import yarfraw.utils.CommonUtils;
/**
 * Provides a set of function to facilitate writing to a feed.
 * <br/>
 * *Note* This class is not thread safe.
 * @author jliang
 *
 */
public class FeedWriter extends AbstractBaseIO{
  private static final ObjectFactory RSS20_FACTORY = new ObjectFactory();
  private static final yarfraw.generated.rss10.elements.ObjectFactory RSS10_FACTORY = 
    new yarfraw.generated.rss10.elements.ObjectFactory();

  private static Marshaller _rss20Marshaller;
  private static Marshaller _rss10Marshaller;
  private static Marshaller _atom10Marshaller;
  
  
  public FeedWriter(File file){
    super(file);
  }
  
  public FeedWriter(String pathName){
    this(new File(pathName));
  }
  
  public FeedWriter(URI uri){
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
   * Writes a channel to a output stream.
   * 
   * @throws YarfrawException if write operation failed.
   */
  public static void writeChannel(FeedFormat format, Channel channel, OutputStream outputStream) throws YarfrawException{
    if(format == null || channel == null ||outputStream==null){
      throw new YarfrawException("format, channel, or outputStream is null");
    }
    try {
      Marshaller m = getMarshaller(format, true);
      m.marshal(getJaxbElementFromFormat(format, channel), outputStream);
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to write channel", e);
    }
  }
  
  /**
   * Writes a channel to the feed file.
   * 
   * @throws YarfrawException if write operation failed.
   */
  public void writeChannel(Channel channel, ValidationEventHandler validationEventHandler) throws YarfrawException{
    FileOutputStream out = null;
    try {
      Marshaller m = getMarshaller(_format, validationEventHandler != null);
      m.setEventHandler(validationEventHandler);
      out = new FileOutputStream(_file);
      m.marshal(getJaxbElementFromFormat(_format, channel), out);
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to write channel", e);
    }
    catch (FileNotFoundException e) {
      throw new YarfrawException("Unable to write channel", e);
    }finally{
      IOUtils.closeQuietly(out);
    }
  }
  
  private static Object getJaxbElementFromFormat(FeedFormat format, Channel channel) throws YarfrawException{
    if(format == FeedFormat.RSS20){
      TRss rss = RSS20_FACTORY.createTRss();
      rss.setVersion(2.0d);
      rss.setChannel(ToRss20ChannelImpl.getInstance().execute(channel).getValue());
      return RSS20_FACTORY.createRss(rss);
    }else if(format == FeedFormat.RSS10){
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
    }else if(format == FeedFormat.ATOM10){
      return ToAtom10ChannelImpl.getInstance().execute(channel);
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
  }
  
  private static synchronized Marshaller getMarshaller(FeedFormat format, boolean createNewInstance) throws JAXBException{
    Marshaller ret = _rss20Marshaller;
    if(format == FeedFormat.RSS20){
      if(createNewInstance){
        return JAXBContext.newInstance(CommonUtils.RSS20_JAXB_CONTEXT).createMarshaller();
      }
      if(_rss20Marshaller==null){
        _rss20Marshaller = JAXBContext.newInstance(CommonUtils.RSS20_JAXB_CONTEXT).createMarshaller();
      }
      ret = _rss20Marshaller;
    }else if(format == FeedFormat.RSS10){
      if(createNewInstance){
        return JAXBContext.newInstance(CommonUtils.RSS10_JAXB_CONTEXT).createMarshaller();
      }
      if(_rss10Marshaller==null){
        _rss10Marshaller = JAXBContext.newInstance(CommonUtils.RSS10_JAXB_CONTEXT).createMarshaller();
      }
      ret = _rss10Marshaller;
    }else if(format == FeedFormat.ATOM10){
      if(createNewInstance){
        return JAXBContext.newInstance(CommonUtils.ATOM10_JAXB_CONTEXT).createMarshaller();
      }
      if(_atom10Marshaller==null){
        _atom10Marshaller = JAXBContext.newInstance(CommonUtils.ATOM10_JAXB_CONTEXT).createMarshaller();
      }
      ret = _atom10Marshaller;
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
    
    return ret;
  }
}