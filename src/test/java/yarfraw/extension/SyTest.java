package yarfraw.extension;

import java.io.File;
import java.math.BigInteger;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.rss10.elements.SyndicationExtension;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class SyTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    SyndicationExtension ext = new SyndicationExtension();
    ext.setUpdateBase("someupdate base");
    ext.setUpdateFrequency(new BigInteger("2"));
    ext.setUpdatePeriod(UpdatePeriodEnum.DAILY);
    
    File f = new File("testTmpOutput/sy.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toSyndicationElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toSyndicationElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    SyndicationExtension ext2 = ExtensionUtils.extractSyndicationExtension(c2.getOtherElements());
    
    assertTrue(EqualsBuilder.reflectionEquals(ext2, ext));
  }
}