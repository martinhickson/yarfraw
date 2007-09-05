package yarfraw.coverage;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.googlebase.elements.GoogleBaseExtension;
import yarfraw.io.FeedReader;
import yarfraw.utils.extension.ExtensionUtils;

public class ExtensionTestSlow extends TestCase{
  @Test
  public void testGoogleBase2() throws Exception {
    ChannelFeed c  = FeedReader.readChannel(FeedFormat.RSS20, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/extension/housing2.xml"));
    
    for(ItemEntry i : c.getItems()){
      GoogleBaseExtension g = ExtensionUtils.extractGoogleBaseExtension(i.getOtherElements());
      assertNotNull(g.getArea());
      assertNotNull(g.getBathrooms());
      assertNotNull(g.getBedrooms());
      assertNotNull(g.getExpirationDate());
      assertNotNull(g.getImageLink().get(0));
      assertNotNull(g.getLocation());
      assertNotNull(g.getPrice());
      assertNotNull(g.getPriceType());
      assertNotNull(g.getPropertyType().get(0));
      assertNotNull(g.getPublishDate());
      assertNotNull(g.getSchoolDistrict());
      assertNotNull(g.getYear());
      assertTrue(g.getAny().size() >= 9);
    }
  }
  @Test
  public void testGoogleBase3() throws Exception {
    ChannelFeed c  = FeedReader.readChannel(FeedFormat.RSS20, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/extension/events2.xml"));
    
    for(ItemEntry i : c.getItems()){
      GoogleBaseExtension g = ExtensionUtils.extractGoogleBaseExtension(i.getOtherElements());
      assertNotNull(g.getEventDateRange());
    }
  }
  @Test
  public void testGoogleBase4() throws Exception {
    ChannelFeed c  = FeedReader.readChannel(FeedFormat.RSS20, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/extension/jobs2.xml"));
    
    for(ItemEntry i : c.getItems()){
      GoogleBaseExtension g = ExtensionUtils.extractGoogleBaseExtension(i.getOtherElements());
      assertNotNull(g.getEducation().get(0));
    }
  }
  @Test
  public void testGoogleBase5() throws Exception {
    ChannelFeed c  = FeedReader.readChannel(FeedFormat.RSS20, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/extension/personals2.xml"));
    
    for(ItemEntry i : c.getItems()){
      GoogleBaseExtension g = ExtensionUtils.extractGoogleBaseExtension(i.getOtherElements());
      assertNotNull(g.getGender());
    }
  }
  @Test
  public void testGoogleBase6() throws Exception {
    ChannelFeed c  = FeedReader.readChannel(FeedFormat.ATOM10, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/extension/travel-atom1.xml"));
    
    for(ItemEntry i : c.getItems()){
      GoogleBaseExtension g = ExtensionUtils.extractGoogleBaseExtension(i.getOtherElements());
      assertNotNull(g.getExpirationDate());
      assertNotNull(g.getPrice());
    }
  }
}