package yarfraw.extension;

import java.io.File;
import java.math.BigInteger;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.slash.elements.SlashExtension;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class SlashTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    SlashExtension ext = new SlashExtension();
    ext.setComments(new BigInteger("177"));
    ext.setDepartment("department");
    ext.setHitParade("177,155,105,33,6,3,0");
    ext.setSection("articles");
    
    File f = new File("testTmpOutput/slash.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toSlahsElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toSlahsElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    SlashExtension ext2 = ExtensionUtils.extractSlashExtension(c2.getOtherElements());
    
    assertTrue(EqualsBuilder.reflectionEquals(ext2, ext));
  }
}