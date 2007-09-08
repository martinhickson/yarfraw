package yarfraw.extension;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.itunes.elements.ItunesCategoryType;
import yarfraw.generated.itunes.elements.ItunesExtension;
import yarfraw.generated.itunes.elements.ItunesImageType;
import yarfraw.generated.itunes.elements.ItunesOwnerType;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class itunesTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    ItunesExtension ext = new ItunesExtension();
    ext.setAuthor("itune author");
    ext.setBlock("itunes block");
    ext.setDuration("itune duration");
    ItunesCategoryType cat = new ItunesCategoryType();
    cat.setText("cat");
    ext.getCategory().add(cat);
    ext.getCategory().add(cat);
    //<duration> is not allowed under channel, read the specs
    //http://www.apple.com/itunes/store/podcaststechspecs.html
    ext.setDuration("10:10:20");
    ext.setExplicit("yes");
    ext.setKeywords("some,key, words");
    ItunesImageType image = new ItunesImageType();
    image.setHref("http://someurl");
    image.setRel("ref");
    ext.setImage(image);
    ItunesOwnerType owner = new ItunesOwnerType();
    owner.setEmail("owner@email.com");
    owner.setName("pwner");
    ext.setOwner(owner);
    ext.setSubtitle("subtitle");
    ext.setSummary("summary");

    File f = new File("testTmpOutput/itunes.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toItunesElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toItunesElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    ItunesExtension ext2 = ExtensionUtils.extractItunesExtension(c2.getOtherElements());

    assertEquals(ext.getDuration(), ext2.getDuration());
    assertEquals(ext.getExplicit(), ext2.getExplicit());
    ext.getImage().setValue(""); //replace null with empty string
    assertEquals(ToStringBuilder.reflectionToString(ext.getImage(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(ext2.getImage(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(ext.getKeywords(), ext2.getKeywords());
    assertEquals(ToStringBuilder.reflectionToString(ext.getOwner(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(ext2.getOwner(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(ext.getSubtitle(), ext2.getSubtitle());
    assertEquals(ext.getSummary(), ext2.getSummary());
    
  }
}