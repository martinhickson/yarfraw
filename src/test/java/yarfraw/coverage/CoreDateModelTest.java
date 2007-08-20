package yarfraw.coverage;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.Source;

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
            new HashSet<Category>(Arrays.asList(new Category("cat"))), "www.comments.com", 
            null, null, new Date(System.currentTimeMillis()), new Source("www.url.com", "source"));
    item = new Item("title", new URI("www.link.com"), "desc", "author", 
            new HashSet<Category>(Arrays.asList(new Category("cat"))), new URI("www.comments.com"), 
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