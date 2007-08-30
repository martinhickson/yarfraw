package yarfraw.coverage;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Text;
import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.Source;
import yarfraw.core.datamodel.Text.TextType;

/**
 * Random tests to invoke code that was reported no covered in cobertura' coverage report
 * @author jliang
 *
 */
public class CoreDateModelTest extends TestCase{
  @Test
  public void testItem() throws Exception{
    Item item = Item.create();
    item.setCategoryString(new HashSet<String>(Arrays.asList("cat1", "cat2")));
    assertTrue("expect 2 categories", item.getCategory().size()==2);
    assertTrue("expect 2 categories", item.getCategoryString().size()==2 && item.getCategoryString().contains("cat1"));
    
    item = new Item("title", "www.link.com", "desc", "author", 
            new HashSet<CategorySubject>(Arrays.asList(new CategorySubject("cat"))), "www.comments.com", 
            null, null, new Date(System.currentTimeMillis()), new Source("www.url.com", "source"));
    item = new Item("title", new URI("www.link.com"), "desc", "author", 
            new HashSet<CategorySubject>(Arrays.asList(new CategorySubject("cat"))), new URI("www.comments.com"), 
            null, null, new Date(System.currentTimeMillis()), new Source(new URI("www.url.com"), "source"));
    
  }
  @Test
  public void testImage() throws Exception{
    try {
      Image img = Image.create();
      img.setHeight(600);
      fail("height out of range");
    }
    catch (Exception e) {
      //success
    }
    try {
      Image img = Image.create();
      img.setWidth(200);
      fail("width out of range");
    }
    catch (Exception e) {
      //success
    }
  }
  
  @Test 
  public void testEnclosure() throws Exception{
    Channel c = Channel.create().additem(new Item().setEnclosure(
            new Enclosure("http://someurl.com/file", 100, "mineType", "optional value")));
    assertTrue("enclosure is expected",c.getItems().get(0).getEnclosure() != null);
    c = Channel.create().additem(new Item().setEnclosure(
            new Enclosure(new URI("http://someurl.com/file"), 100, "mineType", "value")));
    assertTrue("enclosure is expected",c.getItems().get(0).getEnclosure() != null);
  }
  
  @Test
  public void testAtom() throws Exception{
    AtomAttributes attr = new AtomAttributes("base", new Locale("en"));
    attr.setOtherAttributes(new HashMap<QName, String>());
    assertTrue("empty attr map expected", attr.getOtherAttributes().entrySet().size()==0);
    assertTrue("attribute was not built correctly", attr.getBase().equals("base"));
    assertTrue("attribute was not built correctly", attr.getLang().equals(Locale.ENGLISH));
    
    Link link = new Link("href", "rel", "type", "hreflang", "title", 100);
    assertTrue("link was not built correctly", link.getHref() != null);
    assertTrue("link was not built correctly", link.getRel() != null);
    assertTrue("link was not built correctly", link.getType() != null);
    assertTrue("link was not built correctly", link.getHreflang() != null);
    assertTrue("link was not built correctly", link.getTitle() != null);
    assertTrue("link was not built correctly", link.getLength() != null);
    
    Text text = new Text("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
            "<p><i>[Update: The Atom draft is finished.]</i></p>"+
            "</div>");
    assertTrue("text was not built correctly", text.getXhtmlDiv() != null);
    assertTrue("text was not built correctly", text.getType() == TextType.xhtml);
    text.setXhtmlDiv("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
            "<p><i>[Update: The Atom draft is finished.]</i></p>"+
            "</div>");
    assertTrue("text was not built correctly", text.getXhtmlDiv() != null);
  }
  
  @Test
  public void testChannel() throws Exception{
    Channel c = Channel.create();
    assertTrue("no locale", c.getLanguage()!=null);
    assertTrue("no pubDate", c.getPubDate()!=null);
    c = new Channel("title", "http://link.com", "desc");
    assertTrue("no title", c.getTitle()!=null);
    assertTrue("no link", c.getLink()!=null);
    assertTrue("no description", c.getDescription()!=null);
    
    c = new Channel("title", new URI("http://link.com"), "desc");
    assertTrue("no title", c.getTitle()!=null);
    assertTrue("no link", c.getLink()!=null);
    assertTrue("no description", c.getDescription()!=null);
    
    c.setPubDate(null);
    assertTrue("pubDate should be null", c.getPubDate()==null);
    c.setLastBuildDate(null);
    assertTrue("lastBuildDate should be null", c.getPubDate()==null);
    
    assertTrue("no element expected",  c.getElementByNS("", "")==null);
    c.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    c.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    assertTrue("element expected",  c.getOtherElements().size()==1);
    assertTrue("attribute expected",  c.getOtherAttributes().containsValue("test"));
    
    c.setCategoryString(new HashSet<String>(Arrays.asList("cat1", "cat2")));
    assertTrue("2 categories expected",  c.getCategory().size()==2);
    
    try {
      c.setTtl(-1);
      fail("Ttl cannot be negative");
    }
    catch (Exception e) {
      //success;
    }
    
    c.setCloud("rpc.sys.com", 1234, "path?", "procedure", "soap");
    assertTrue("cloud is null", c.getCloud()!=null);
    c.setTextInput("title", "desc", "name", new URI("www.google.com"));
    assertTrue("textinput is null", c.getTexInput()!=null);
    c.setSkipHours(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
    assertTrue("skip hour is empty", c.getSkipHours().size()==3);
    
    try {
      c.addSkipHour(100);
      fail("skip hour cannot be out of range");
    }
    catch (Exception e) {
      //success;
    }
    
    
  }
}