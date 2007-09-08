package yarfraw.rss20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;

public class CoverageTest extends TestCase{

  private static final Map<QName, String> AttrMap = new HashMap<QName, String>();
  private static final QName NAME1 = new QName("http://my.com", "name1");
  private static final QName NAME2 = new QName("http://my.com", "name2");
  private static final QName NAME3 = new QName("http://my.com", "name3");
  private static final QName NAME4 = new QName("http://my.com", "name4");
  private static final List<Element> ELEMENTS = new ArrayList<Element> ();
  private static DocumentBuilder BUILDER = null;
  private static void init() throws Exception{
    BUILDER = DocumentBuilderFactory.newInstance().newDocumentBuilder();  
    AttrMap.clear();
    AttrMap.put(NAME1, "jay");
    AttrMap.put(NAME2, "jay2");
    AttrMap.put(NAME3, "jay3");
    ELEMENTS.clear();
    ELEMENTS.add(BUILDER.newDocument().createElementNS(NAME1.getNamespaceURI(), NAME1.getLocalPart()));
    ELEMENTS.add(BUILDER.newDocument().createElementNS(NAME2.getNamespaceURI(), NAME2.getLocalPart()));
    ELEMENTS.add(BUILDER.newDocument().createElementNS(NAME3.getNamespaceURI(), NAME3.getLocalPart()));
  }
  
  @Test
  public void testRss20() throws Exception{
    Cloud c = new Cloud();
    try {
      c.validate(FeedFormat.RSS20);
      fail("this should fail");
    }
    catch (Exception e) {
      //success
    }
    
    c = new Cloud("domain", "123", "abc", "blah", "soap");
    
    try {
      c.setProtocol("blah");
      c.validate(FeedFormat.RSS20);
      fail("this should fail");
    }
    catch (Exception e) {
      //success
    }
    
    FeedReader r = new FeedReader(Thread.currentThread()
            .getContextClassLoader().getResource("yarfraw/rss20cover.xml").toURI());
    ChannelFeed ch = r.readChannel();
    setupChannel(ch);
    ch.validate(FeedFormat.RSS20);
    FeedWriter w = new FeedWriter("testTmpOutput/rss20/testRss20.xml");
    w.writeChannel(ch);
  }
  
  @Test
  public void setupChannel(ChannelFeed o) throws Exception{
    init();

    o.setOtherAttributes(AttrMap);
    assertNotNull(o.getOtherAttributes());
    assertEquals(3, o.getOtherAttributes().entrySet().size());
    o.addOtherAttributes(NAME4, "jay4");
    assertEquals("jay4", o.getAttributeValueByLocalName("name4"));
    assertEquals("jay4", o.getAttributeValueByQName(NAME4));
    assertEquals(4, o.getOtherAttributes().entrySet().size());
    
    o.setOtherElements(ELEMENTS);
    assertEquals(3, o.getOtherElements().size());
    assertNotNull(o.getElementByNS(NAME1.getNamespaceURI(), NAME1.getLocalPart()));
    assertNotNull(o.getElementByNS(NAME2.getNamespaceURI(), NAME2.getLocalPart()));
    assertNotNull(o.getElementByNS(NAME3.getNamespaceURI(), NAME3.getLocalPart()));
    
    o.setOtherAttributes(null);
    o.setOtherElements(null);
    assertNull(o.getOtherAttributes());
    assertNull(o.getOtherElements());
    o.addOtherAttributes(NAME4, "jay4");
    o.addOtherElement(ELEMENTS.get(0));
    
    o.addOtherElement("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
            "<p><i>[Update: The Atom draft is finished.]</i></p>"+
            "</div>");
    
    o.setAbout("about");
    assertNotNull(o.getAbout());
    o.setResource("res");
    assertNotNull(o.getResource());
    o.setLang(Locale.ENGLISH);
    assertEquals(o.getLang(), Locale.ENGLISH.getLanguage());
  }
  
}