package yarfraw.io.parser;

import java.util.EnumSet;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.FeedFormat;

/**
 * This is a simple example implementation of {@link FeedSAXParser}. <br />
 * <p/>
 * This parser is based on the rss 2.0 specs but does supports all {@link FeedFormat}s, 
 * it ignores most of the optional rdf/atom attributes. 
 * It also maps the enclosure link in an atom feed to an {@link Enclosure}
 * object (instead of to an {@link AtomLink}.
 * 
 * @author jliang
 *
 */
public class FeedSAXParserSimpleImpl extends FeedSAXParser{

  protected Channel _channel = null;
  protected Map<String, String> _prefixMap;
  protected CoreRssElementEnum _currentElement = null;
  protected EnumSet<CoreRssElementEnum> _elementsOfInterest;
  
  public FeedSAXParserSimpleImpl(){
    _elementsOfInterest = EnumSet.allOf(CoreRssElementEnum.class);
  }
  
  public FeedSAXParserSimpleImpl(EnumSet<CoreRssElementEnum> elementsOfInterest){
    _elementsOfInterest = elementsOfInterest;
  }
  
  @Override
  public Channel getChannel() {
    return _channel;
  }

  @Override
  public void startDocument ()throws SAXException{
    _channel = new Channel();
  }
  
  @Override
  public void startElement (String uri, String localName,
          String qName, Attributes atts) throws SAXException{
    for(CoreRssElementEnum element : _elementsOfInterest){
      if(SimpleParserUtils.isElement(element, uri, localName, qName, _format)){
        _currentElement = element;
        break;
      }
    }
  }
  @Override
  public void endElement (String uri, String localName,
          String qName) throws SAXException{
    _currentElement = null; //protect current element
  }
}

class SimpleParserUtils{
  public static boolean isElement(CoreRssElementEnum element, 
          String uri, String localName, String qName, FeedFormat format){
    QName expected = element.getName(format);
    boolean sameUri =StringUtils.isEmpty(uri)? true : uri.equals(expected.getNamespaceURI());
    boolean sameLocalName = expected.getLocalPart().equals(localName) 
            || (qName != null && qName.endsWith(expected.getLocalPart())); 
      return sameUri && sameLocalName;
  }
  
}