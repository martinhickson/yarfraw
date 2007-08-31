package yarfraw.coverage;
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

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.Text;
import yarfraw.core.datamodel.TextInput;
import yarfraw.core.datamodel.Text.TextType;

public class CoreModelTest extends TestCase{
  
  
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
  public void testCoreModel() throws Exception{
    ChannelFeed c = new ChannelFeed();
    assertNull(c.getLangAsLocale());
    c.setLang(Locale.ENGLISH);
    assertEquals(c.getLang(), Locale.ENGLISH.getLanguage());
    assertEquals(c.getLangAsLocale(), Locale.ENGLISH);
    
    c.addOtherAttributes(new QName("http://my.com", "name"), "jay");
    assertEquals("jay", c.getAttributeValueByLocalName("name"));
    assertEquals("jay", c.getAttributeValueByQName(new QName("http://my.com", "name")));
    assertNull(c.getAttributeValueByQName(new QName("", "name")));
    assertNull(c.getAttributeValueByLocalName("blah"));
    
    c.addOtherElement("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
            "<p><i>[Update: The Atom draft is finished.]</i></p>"+
          "</div>");
    
    assertNotNull(c.getElementByNS("http://www.w3.org/1999/xhtml", "div"));
    assertEquals("div", c.getElementByNS("http://www.w3.org/1999/xhtml", "div").getLocalName());
    assertEquals("http://www.w3.org/1999/xhtml", c.getElementByNS("http://www.w3.org/1999/xhtml", "div").getNamespaceURI());
    Link l1 = new Link("http://href.com", "rel", "type", "en", "title", 123);
    Link l2 = new Link().setHref("http://href.com")
                        .setRel("rel")
                        .setType("type")
                        .setHreflang(Locale.ENGLISH)
                        .setTitle("title")
                        .setLength(123);
    
    assertEquals(l1, l2);
  }
  
  
  //FIXME: can probably use reflection the do these, but it's easier to copy and paste
//  private static void testModel(Object o, Class klass) throws Exception{
//    klass.cast(o);
//  }
  
  @Test
  public void testCategorySubject() throws Exception{
    init();
    CategorySubject o = new CategorySubject();
    
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
  

  @Test
  public void testChannelFeed() throws Exception{
    init();
    ChannelFeed o = new ChannelFeed();
    
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
  
  @Test
  public void testItemEntry() throws Exception{
    init();
    ItemEntry o = new ItemEntry();
    
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
  
  @Test
  public void testContent() throws Exception{
    init();
    Content o = new Content();
    
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
    
    o.setLang(Locale.ENGLISH);
    assertEquals(o.getLang(), Locale.ENGLISH.getLanguage());
  }
  
  @Test
  public void testPerson() throws Exception{
    init();
    Person o = new Person();
    
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
    
    o.setLang(Locale.ENGLISH);
    assertEquals(o.getLang(), Locale.ENGLISH.getLanguage());
  }
  
  @Test
  public void testText() throws Exception{
    init();
    Text o = new Text(TextType.text);
    o.setType(TextType.text);
    o.setXhtmlDiv("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
            "<p><i>[Update: The Atom draft is finished.]</i></p>"+
            "</div>");
    assertNotNull(o.getXhtmlDiv());
    o.setLang(Locale.ENGLISH);
    assertEquals(o.getLang(), Locale.ENGLISH.getLanguage());
  }
  
  @Test
  public void testTextInput() throws Exception{
    init();
    TextInput o = new TextInput();
    
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
    
  }
  
  @Test
  public void testGenerator() throws Exception{
    init();
    Generator o = new Generator("");
    
    o.setOtherAttributes(AttrMap);
    assertNotNull(o.getOtherAttributes());
    assertEquals(3, o.getOtherAttributes().entrySet().size());
    o.addOtherAttributes(NAME4, "jay4");
    assertEquals("jay4", o.getAttributeValueByLocalName("name4"));
    assertEquals("jay4", o.getAttributeValueByQName(NAME4));
    assertEquals(4, o.getOtherAttributes().entrySet().size());
    
    o.setOtherAttributes(null);
    
    o.addOtherAttributes(NAME4, "jay4");
    
    
  }
}