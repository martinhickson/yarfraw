package yarfraw.rss10;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
/**
 * Some unit tests for Reader/Writer/Appender
 * 
 * @author jliang
 *
 */
public class IOTest extends TestCase{

  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    ChannelFeed c = r.readChannel();
    
    File f = File.createTempFile("rss10test", ".xml");
    FeedWriter w = new FeedWriter(f);
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
    
    r.setFormat(FeedFormat.RSS10);
    r.setFile(f);
    ChannelFeed c2 = r.readChannel();
    assertEquals("digg", c2.getTitle());
    assertEquals("digg", c2.getDescription());
    
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomAttributes(), c2.getAtomAttributes()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomId(), c2.getAtomId()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomLinks(), c2.getAtomLinks()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomTextAttributes(), c2.getAtomTextAttributes()));
    
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
  
  
}


