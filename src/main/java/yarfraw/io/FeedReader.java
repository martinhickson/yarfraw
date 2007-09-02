package yarfraw.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.IOUtils;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss20.elements.TRss;
import yarfraw.mapping.backward.impl.ToChannelAtom10Impl;
import yarfraw.mapping.backward.impl.ToChannelRss10Impl;
import yarfraw.mapping.backward.impl.ToChannelRss20Impl;
import yarfraw.utils.CommonUtils;
/**
 * Provides a set of function to facilitate reading of a RSS feed.
 * 
 * @author jliang
 *
 */
public class FeedReader  extends AbstractBaseFeedParser{
  
  public FeedReader(File file, FeedFormat format){
    super(file, format);
  }
  
  public FeedReader(String pathName, FeedFormat format){
    super(new File(pathName), format);
  }
  
  public FeedReader(URI uri, FeedFormat format){
    super(new File(uri), format);
  }  
  public FeedReader(File file){
    super(file);
  }
  
  public FeedReader(String pathName){
    super(new File(pathName));
  }
  
  public FeedReader(URI uri){
    super(new File(uri));
  }
  
  public FeedReader(HttpURL httpUrl, HttpClientParams params) throws YarfrawException, IOException{
    super(httpUrl, params);
  }
  
  public FeedReader(HttpURL httpUrl) throws YarfrawException, IOException{
    super(httpUrl, null);
  }
    /**
   * Reads a channel from the feed file with a custom {@link ValidationEventHandler}
   * 
   * @throws YarfrawException if read operation failed.
   */
  public static ChannelFeed readChannel(FeedFormat format, InputStream inputStream) throws YarfrawException{
    Unmarshaller u;
    try {
      u = getUnMarshaller(format);
      return toChannel(format, u.unmarshal(inputStream));
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to unmarshal file", e);
    }
  }
  
  /**
   * Reads a channel from the feed file with a custom {@link ValidationEventHandler}
   * 
   * @throws YarfrawException if read operation failed.
   */
  public ChannelFeed readChannel(ValidationEventHandler validationEventHandler) throws YarfrawException{
    Unmarshaller u;
    InputStream input = null;
    try {
      input = getStream();
      u = getUnMarshaller(_format); 
      if(validationEventHandler != null){
        u.setEventHandler(validationEventHandler);
      }
      return toChannel(_format, u.unmarshal(input));
    } catch (JAXBException e) {
      throw new YarfrawException("Unable to unmarshal file", e);
    }
    catch (HttpException e) {
      throw new YarfrawException("Unable to read from remote url", e);
    }
    catch (IOException e) {
      throw new YarfrawException("Unable to read", e);
    }finally{
      IOUtils.closeQuietly(input);
    }
  }
  
  @SuppressWarnings("unchecked")
  private static ChannelFeed toChannel(FeedFormat format, Object o) throws YarfrawException{
    if(format == FeedFormat.RSS20){
      return ToChannelRss20Impl.getInstance().execute(((JAXBElement<TRss>)o).getValue().getChannel());
    }else if(format == FeedFormat.RSS10){
      return  ToChannelRss10Impl.getInstance().execute((RDF)o);
    }else if(format == FeedFormat.ATOM10){
      return ToChannelAtom10Impl.getInstance().execute(((JAXBElement<FeedType>)o).getValue());
    }else{
      throw new UnsupportedOperationException("Unknown Feed Format");
    }
  }
  
  /**
   * Reads a channel from the feed file.
   * 
   * @throws YarfrawException if read operation failed.
   */
  public ChannelFeed readChannel() throws YarfrawException{
    return readChannel(null);
  }
  
  private static class WarningHandler implements ValidationEventHandler{

    public boolean handleEvent(ValidationEvent event) {
      DefaultValidationEventHandler d = new DefaultValidationEventHandler();
      d.handleEvent(event);
      return event.getSeverity()== ValidationEvent.FATAL_ERROR;
    } 
    
  }
  
  private static synchronized Unmarshaller getUnMarshaller(FeedFormat format) throws JAXBException{
    if(format == FeedFormat.RSS20){
      Unmarshaller u = JAXBContext.newInstance(CommonUtils.RSS20_JAXB_CONTEXT).createUnmarshaller();
      u.setEventHandler(new WarningHandler());
      return u;
    }else if(format == FeedFormat.RSS10){
      Unmarshaller u = JAXBContext.newInstance(CommonUtils.RSS10_JAXB_CONTEXT).createUnmarshaller();
      u.setEventHandler(new WarningHandler());
      return u;
    }else if(format == FeedFormat.ATOM10){
      Unmarshaller u = JAXBContext.newInstance(CommonUtils.ATOM10_JAXB_CONTEXT).createUnmarshaller();
      u.setEventHandler(new WarningHandler());
      return u;
    }else{
      throw new UnsupportedOperationException("UnSupported Feed Format");
    }
  }
}