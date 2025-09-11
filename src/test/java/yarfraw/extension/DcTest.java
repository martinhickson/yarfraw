package yarfraw.extension;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.rss10.elements.DcType;
import yarfraw.generated.rss10.elements.DublinCoreExtension;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class DcTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    DublinCoreExtension ext = new DublinCoreExtension();
    DcType dc = new DcType();
    dc.setLang("en");
    dc.setValue("value");
    dc.setAbout("http://somewhere");

    ext.setCoverage(dc);
    ext.setCreator(dc);
    ext.setDate(dc);
    ext.setDescription(dc);
    ext.setFormat(dc);
    ext.setIdentifier(dc);
    ext.setLanguage(dc);
    ext.setPublisher(dc);
    ext.setRelation(dc);
    ext.setRights(dc);
    ext.setSource(dc);
    ext.setTitle(dc);
    ext.setType(dc);
    File f = new File("testTmpOutput/dc.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toDublinCoreElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toDublinCoreElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    DublinCoreExtension ext2 = ExtensionUtils.extractDublinCoreExtension(c2.getOtherElements());
    assertTrue(EqualsBuilder.reflectionEquals(ext.getCoverage(),ext2.getCoverage()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getCoverage(),ext2.getCoverage()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getCreator(), ext2.getCreator()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getDate(), ext2.getDate()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getDescription(), ext2.getDescription()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getFormat(), ext2.getFormat()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getIdentifier(), ext2.getIdentifier()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getLanguage(), ext2.getLanguage()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getPublisher(), ext2.getPublisher()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getRelation(), ext2.getRelation()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getRights(), ext2.getRights()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getSource(), ext2.getSource()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getTitle(),ext2.getTitle()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getType(),ext2.getType()));
    
    
  }
}