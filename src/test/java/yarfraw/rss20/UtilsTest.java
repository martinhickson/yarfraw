package yarfraw.rss20;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.io.FeedReader;

/**
 * Util class tests.
 * 
 * @author jliang
 *
 */
public class UtilsTest extends TestCase{
  
  public void testOtherElementSearch() throws Exception {
    FeedReader r = new FeedReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    ChannelFeed c = r.readChannel();
//    <digg:diggCount>429</digg:diggCount>
    Element e = c.getItems().get(0).getElementByNS("http://digg.com/docs/diggrss/", "diggCount");
    assertEquals("429", e.getTextContent());
//  <my:newElement xmlns:my="http://my.company.com/">new element</my:newElement>
    e = c.getElementByNS("http://my.company.com/", "newElement");
    assertEquals("new element", e.getTextContent());
  }
  
}