package yarfraw.rss20;

import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.utils.FeedFormatDetector;

/**
 * Util class tests.
 * 
 * @author jliang
 *
 */
public class UtilsTest extends TestCase{
  @Test
  public void testFormatDetection() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/digg.xml");
      assertEquals(FeedFormat.RSS20, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  @Test
  public void testFormatDetection2() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/rss10/rdf.xml");
      assertEquals(FeedFormat.RSS10, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  
  @Test
  public void testFormatDetection3() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/atom10/atom10.xml");
      assertEquals(FeedFormat.ATOM10, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  
  public void testOtherElementSearch() throws Exception {
    FeedReader r = new FeedReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    Channel c = r.readChannel();
//    <digg:diggCount>429</digg:diggCount>
    Element e = c.getItems().get(0).getElementByNS("http://digg.com/docs/diggrss/", "diggCount");
    assertEquals("429", e.getTextContent());
//  <my:newElement xmlns:my="http://my.company.com/">new element</my:newElement>
    e = c.getElementByNS("http://my.company.com/", "newElement");
    assertEquals("new element", e.getTextContent());
  }
  
}