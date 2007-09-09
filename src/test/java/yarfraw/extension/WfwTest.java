package yarfraw.extension;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.wfw.elements.WellFormedWebExtension;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class WfwTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    WellFormedWebExtension ext = new WellFormedWebExtension();
    ext.setComment("wfw comments");
    ext.setCommentRss("wfw rss comment");
    
    File f = new File("testTmpOutput/wfw.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toWellFormedWebElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toWellFormedWebElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    WellFormedWebExtension ext2 = ExtensionUtils.extractWellFormedWebExtension(c2.getOtherElements());
    
    assertTrue(EqualsBuilder.reflectionEquals(ext2, ext));
  }
}