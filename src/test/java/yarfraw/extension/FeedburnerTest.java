package yarfraw.extension;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;

import yarfraw.generated.feedburner.elements.FeedburnerExtension;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class FeedburnerTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    FeedburnerExtension ext = new FeedburnerExtension();
    ext.setBrowserFriendly("some browser friendly test");
    ext.setOrigLink("http://theoriginallink");
    
    File f = new File("testTmpOutput/feedburner.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toFeedburnerElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toFeedburnerElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    FeedburnerExtension ext2 = ExtensionUtils.extractFeedburnerExtension(c2.getOtherElements());
    assertEquals(ext.getBrowserFriendly(), ext2.getBrowserFriendly());
    assertEquals(ext.getOrigLink(), ext2.getOrigLink());
    
  }
}