package yarfraw.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

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
  private static final Log LOG = LogFactory.getLog(FeedFormatDetector.class);
  private static final String RSS = "rss";
  private static final String VERSION = "version";
  private static final String VERSION_20 = "2.0";
  private static final String RDF = "RDF";
  private static final String RDF_NS_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
  private static final String ATOM10_XMLNS = "http://www.w3.org/2005/Atom";
  private static final String ATOM03_XMLNS = "http://purl.org/atom/ns#";
  private static final String FEED = "feed";
  
  private static final FormatDetectionHandler FormatDetectionHandler_NON_STRICT = new FormatDetectionHandler(false);
  private static final FormatDetectionHandler FormatDetectionHandler_STRICT = new FormatDetectionHandler(true);
  
  /**
   * Determines the format of the input feed stream. 
   * <br/>
   * Officially, Yarfraw currently only supports {@link FeedFormat these} formats. But, the format detector
   *  will report RSS 0.9x formats as RSS 2.0 because the FeedReader is able to read them using the RSS 2.0 parser.
   *  If you want a stricter format detector, you can use <code>getFormat(InputStream stream, boolean strictFormatDetection)</code>
   *  to pass in a strict enforcement flag to tell the detector you want strict format detection.
   *  
   * @param stream input stream of a feed
   * @return the format of the feed
   * @throws YarfrawException if unable to detect the format, this usually means the detector 
   * failed to parse the input stream.
   */
  public static FeedFormat getFormat(InputStream stream) throws YarfrawException{
    return getFormat(stream, false); //non-strict format detection
  }

  /**
   * Determines the format of the input feed stream. 
   * <br/>
   * Officially, Yarfraw currently only supports {@link FeedFormat these} formats. But, the format detector
   *  will report RSS 0.9x formats as RSS 2.0 because the FeedReader is able to read them using the RSS 2.0 parser.
   *  If you want a stricter format detector, you can use <code>getFormat(InputStream stream, boolean strictFormatDetection)</code>
   *  to pass in a strict enforcement flag to tell the detector you want strict format detection.
   *  
   * 
   * @param strictFormatDetection whether to use 'strict' format detection. if set to true, the method will only report
   * RSS 2.0 when the root element is 'rss' and it has a version 2.0 attribute: &lt;rss version="2.0">
   * @param stream input stream of a feed
   * @return the format of the feed
   * @throws YarfrawException if unable to detect the format, this usually means the detector 
   * failed to parse the input stream.
   */
  public static FeedFormat getFormat(InputStream stream, boolean strictFormatDetection) throws YarfrawException{
    if(stream == null){
      throw new IllegalArgumentException("Null stream received");
    }
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(stream, strictFormatDetection? FormatDetectionHandler_STRICT : FormatDetectionHandler_NON_STRICT);
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

  
  private static class FormatDetectionHandler extends DefaultHandler2{
    private boolean _strict = false;
    public FormatDetectionHandler(boolean strict){
      _strict = strict;
    }
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws EarlyTerminationException{

    //just check the root element is enough 
      if(RSS.equals(localName)){
        String version = attributes.getValue(StringUtils.EMPTY, VERSION);
        if(!VERSION_20.equals(version) ){
          if(!_strict){
            LOG.warn("Input RSS feed is of version "+version+", reading it as version 2.0. Version 2.0 should be backward compatibile");
          }else{
            //strict format detection, version number has to match
            throw new EarlyTerminationException(FeedFormat.UNKNOWN);
          }
        }
        throw new EarlyTerminationException(FeedFormat.RSS20);
      }else if (RDF.equals(localName) && RDF_NS_URI.equals(uri)) {
        throw new EarlyTerminationException(FeedFormat.RSS10);
      }else if (FEED.equals(localName)&& ATOM10_XMLNS.equals(uri)) {
        throw new EarlyTerminationException(FeedFormat.ATOM10);
      }else if(FEED.equals(localName) && ATOM03_XMLNS.equals(uri)){
        throw new EarlyTerminationException(FeedFormat.ATOM03);
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