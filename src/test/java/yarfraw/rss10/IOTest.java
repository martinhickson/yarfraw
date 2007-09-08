package yarfraw.rss10;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.CommonUtils;
/**
 * Some unit tests for Reader/Writer/Appender
 * 
 * @author jliang
 *
 */
public class IOTest extends TestCase{

  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/theserverside-rss2.xml").toURI());
    ChannelFeed c = r.readChannel();
    
    File f = new File("testTmpOutput/rss10/testRead.xml");
    FeedWriter w = new FeedWriter(f, FeedFormat.RSS10);
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
    
    r.setFormat(FeedFormat.RSS10);
    r.setFile(f);
    ChannelFeed c2 = r.readChannel();

    c.setUid((Id)null);
    assertEquals(c.getItems().size(), c2.getItems().size());
    for(int  i=0; i< c.getItems().size(); i++){
      ItemEntry i1 = c.getItems().get(i);
      ItemEntry i2 = c2.getItems().get(i);
      assertEquals(i1.getOtherElements().size(), i2.getOtherElements().size());
      i1.setUid((Id)null);
      i1.setOtherElements(null);
      i2.setUid((Id)null);
      i2.setOtherElements(null);
      
      assertEquals(CommonUtils.tryParseDate(i1.getPubDate()), CommonUtils.tryParseDate(i2.getPubDate()));
      //different date format
      i1.setPubDate(null);
      i2.setPubDate(null);
      
      assertEquals(CommonUtils.tryParseDate(i1.getUpdatedDate()), CommonUtils.tryParseDate(i2.getUpdatedDate()));
      //different date format
      i1.setUpdatedDate(null);
      i2.setUpdatedDate(null);
    }
    
    assertEquals(c.getOtherElements().size(), c2.getOtherElements().size());
    c.setOtherElements(null);
    c2.setOtherElements(null);
    //not supported by rss 1.0
    c.setLastBuildOrUpdatedDate(null);
    c.setGenerator((Generator)null);
    //not supported by rss 2.0
    c2.setAbout(null);
    c2.getImageOrIcon().setAbout(null);
    
    assertEquals(CommonUtils.tryParseDate(c.getPubDate()), CommonUtils.tryParseDate(c.getPubDate()));
    //different date format
    c.setPubDate(null);
    c2.setPubDate(null);
    
    assertEquals(CommonUtils.tryParseDate(c.getLastBuildOrUpdatedDate()), CommonUtils.tryParseDate(c.getLastBuildOrUpdatedDate()));
    //different date format
    c.setLastBuildOrUpdatedDate(null);
    c2.setLastBuildOrUpdatedDate(null);
    
    assertEquals(c, c2);
  }
  
  @Test
  public void testContent() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/rss10/content.xml").toURI(), FeedFormat.RSS10);
    ChannelFeed c = r.readChannel();
    ItemEntry i = c.getItems().get(0);
    assertEquals("The Example Item", i.getTitleText());
    assertNotNull(i.getElementByLocalName("encoded"));
    assertNotNull(i.getElementByLocalName("encoded").getTextContent());
  }
}


