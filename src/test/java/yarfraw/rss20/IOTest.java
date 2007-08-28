package yarfraw.rss20;

import java.io.File;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.io.FeedAppender;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.Rss20Utils;
/**
 * Some unit tests for Reader/Writer/Appender
 * 
 * @author jliang
 *
 */
public class IOTest extends TestCase{

  @Test
  public void testBuilder() throws Exception{
    Channel c = BuilderTest.buildChannel();
    FeedWriter w = new FeedWriter(File.createTempFile("yarfraw", ".xml"));
    c.setTitle("<test>test</test>");
    w.writeChannel(c);
    w.writeChannel(c, new ValidationEventHandler(){
      public boolean handleEvent(ValidationEvent event) {
        System.out.println(event);
        return false;
      }
      
    });
  }
  
  @Test
  public void testBuilder2() throws Exception{
    Channel c = BuilderTest.buildChannel();
    FeedWriter w = new FeedWriter(File.createTempFile("yarfraw", ".xml"));
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
  }
  
  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    Channel c = r.readChannel();
    r.readChannel(new ValidationEventHandler(){

      public boolean handleEvent(ValidationEvent event) {
        // TODO Auto-generated method stub
        return false;
      }
      
    });
    assertTrue("Title is not the same", "digg".equals(c.getTitle()));
    assertTrue("language is not the same", "en-us".equals(c.getLanguage().getLanguage().toLowerCase()));
    assertTrue("Link is not the same", "http://digg.com/".equals(c.getLink().toString()));
    //TODO: put more asserts
  }
  
  @Test
  public void testRead2() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/yarfraw.xml").toURI());
    assertTrue(!r.isRemoteRead());
    Channel c = r.readChannel();
    Channel c2 = BuilderTest.buildChannel();
    assertTrue("Copyright not equal!", EqualsBuilder.reflectionEquals(c.getCopyright(), c2.getCopyright()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategory(), c2.getCategory()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Cloud not equal!", EqualsBuilder.reflectionEquals(c.getCloud(), c2.getCloud()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));
    assertTrue("Docs not equal!", EqualsBuilder.reflectionEquals(c.getDocs(), c2.getDocs()));
    assertTrue("Generator not equal!", EqualsBuilder.reflectionEquals(c.getGenerator(), c2.getGenerator()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage(), c2.getImage()));
    assertTrue("Items list not equal!", EqualsBuilder.reflectionEquals(c.getItems(), c2.getItems()));
    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));
    assertTrue("LastBuildDate not equal!", EqualsBuilder.reflectionEquals(c.getLastBuildDate(), c2.getLastBuildDate()));
    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("ManagingEditor not equal!", EqualsBuilder.reflectionEquals(c.getManagingEditor(), c2.getManagingEditor()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    assertTrue("SkipDays not equal!", EqualsBuilder.reflectionEquals(c.getSkipDays(), c2.getSkipDays()));
    assertTrue("SkipHours not equal!", EqualsBuilder.reflectionEquals(c.getSkipHours(), c2.getSkipHours()));
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput(), c2.getTexInput()));
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    assertTrue("TTL not equal!", EqualsBuilder.reflectionEquals(c.getTtl(), c2.getTtl()));
    assertTrue("WebMaster not equal!", EqualsBuilder.reflectionEquals(c.getWebMaster(), c2.getWebMaster()));
  }
  

  @Test
  public void testRead3() throws Exception{  
    File f1 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    File f2 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/reddit.xml").toURI());
    File f3 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/theserverside-rss2.xml").toURI());
    List<Channel> channels = Rss20Utils.readAll(f1, f2, f3);
    assertEquals(3, channels.size());
  }
  
  @Test
  public void testRemoteRead() throws Exception{  
    
    try{
      FeedReader reader = new FeedReader(new HttpURL("http://digg.com/rss/index.xml"));
      assertTrue(reader.isRemoteRead());
      Channel c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
  }
  
  @Test
  public void testRemoteRead2() throws Exception{
    FeedReader reader = null;
    try{
      reader = new FeedReader(new HttpURL("http://digg.com/rss/index.xml"));
      assertTrue(reader.isRemoteRead());
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
      reader.setHttpClientParams(params);
      Channel c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
    
    
    try{
      reader = new FeedReader(new HttpURL("http://www.twit.tv/node/feed"));
      assertTrue(reader.isRemoteRead());
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
      reader.setHttpClientParams(params);
      Channel c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
    
    try {
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout(20);
      reader = new FeedReader(new HttpURL("http://nowhere.com"));
      reader.readChannel();
      fail("should failed");
    }
    catch (Exception e) {
      // success;
    }
  }
  
  @Test
  public void testAppend() throws Exception{
    File f = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    FeedAppender a = new FeedAppender(f);
    Item item = BuilderTest.buildChannel().getItems().get(0);
    a.addItem(item);
    Channel c = Rss20Utils.read(f);
    assertEquals(item, c.getItems().get(c.getItems().size()-1));
    int oldSize = c.getItems().size();
    a.removeItem(oldSize-1);
    c = Rss20Utils.read(f);
    assertEquals(oldSize-1, c.getItems().size());
  }
  
  @Test
  public void testAppend2() throws Exception{
    File f = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    File copy = File.createTempFile("YarfrawDiggCopy", ".xml");
    
    FeedWriter w = new FeedWriter(copy);
    w.writeChannel(new FeedReader(f).readChannel());
    
    FeedAppender a = new FeedAppender(copy);
    a.setNumItemToKeep(10);
    
    a.addItem(BuilderTest.buildChannel().getItems().get(0));
    
    FeedReader r = new FeedReader(copy);
    assertEquals(10, r.readChannel().getItems().size());
    
    a.addAllItems(BuilderTest.buildChannel().getItems());
    
    assertEquals(10, r.readChannel().getItems().size());
    
    a.setItem(0, BuilderTest.buildChannel().getItems().get(1));
    
    assertEquals("item not set correctly", r.readChannel().getItems().get(0), BuilderTest.buildChannel().getItems().get(1));
    
    a.addAllItems(BuilderTest.buildChannel().getItems().get(1));
    
    assertEquals("item not added correctly", r.readChannel().getItems().get(9), BuilderTest.buildChannel().getItems().get(1));
  }
  
  
}


