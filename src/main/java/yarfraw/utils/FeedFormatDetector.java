package yarfraw.rss20.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;

/**
 * A (somewhat) primitive RSS feed format detection utility class.<br/>
 * It checks the root element of the input xml stream to determines the format
 * of a feed.
 *   
 * @author jliang
 *
 */
public class FeedFormatDetector{

  private static final String RSS = "rss";
  private static final String VERSION = "version";
  private static final String RDF = ":RDF";
  
  private static final FormatDetectionHandler FormatDetectionHandler = new FormatDetectionHandler();
  
  /**
   * Determines the format of the input feed stream. <br/>
   * 
   * @param stream input stream of a feed
   * @return the format of the feed
   * @throws YarfrawException if unable to detect the format, this usually means the detector 
   * failed to parse the input stream.
   */
  public static FeedFormat getFormat(InputStream stream) throws YarfrawException{
    if(stream == null){
      throw new IllegalArgumentException("Null stream received");
    }
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(stream, FormatDetectionHandler);
      //we should never get to here
      return FeedFormat.UNKNOWN;
    }
    catch (ParserConfigurationException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }
    catch(EarlyTerminationException e){
      return e.getFormat(); //should always get to here
    }
    catch (SAXException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }
    catch (IOException e) {
      throw new YarfrawException("Format Detection Failed", e);
    }
  }
  
  private static class FormatDetectionHandler extends DefaultHandler{
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws EarlyTerminationException{
       
    //just check the root element is enough
      if(RSS.equals(qName)
              && attributes.getValue(StringUtils.EMPTY, VERSION) != null ){
        throw new EarlyTerminationException(FeedFormat.RSS20);
      }else if (StringUtils.isNotEmpty(qName) && qName.endsWith(RDF)) {
        throw new EarlyTerminationException(FeedFormat.RSS10);
      }else{
       //does not recognize the format from the root element, the format must be unknown
        throw new EarlyTerminationException(FeedFormat.UNKNOWN);
      }
    }
  }
  
  /**
   * An exception to be thrown for letting us to terminate the parsing prematurely
   */
  private static class EarlyTerminationException extends SAXException{
    private static final long serialVersionUID = 1L;
    private FeedFormat _format;
    public EarlyTerminationException(FeedFormat format){
      _format = format;
    }
    public FeedFormat getFormat(){
      return _format;
    }
  }
}