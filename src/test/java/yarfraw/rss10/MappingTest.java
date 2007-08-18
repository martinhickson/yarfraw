package yarfraw.rss10;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.io.FeedWriter;
import yarfraw.mapping.backward.ToChannelRss10;
import yarfraw.mapping.backward.impl.ToChannelRss10Impl;
import yarfraw.mapping.forward.impl.ToRss10ChannelImpl;
import yarfraw.mapping.forward.impl.ToRss10ChannelItemImpl;
import yarfraw.utils.Rss10Utils;

/**
 * Some unit tests.
 * 
 * @author jliang
 *
 */
public class MappingTest extends TestCase{
    
  @Test
  public void testMapping() throws Exception{
    
    Channel c = BuilderTest.buildChannel();
    
    ToChannelRss10 mapper = ToChannelRss10Impl.getInstance();
    RDF rdf = new RDF();
    TRss10Channel ch10 = ToRss10ChannelImpl.getInstance().execute(c).getValue();
    rdf.getChannelOrItemOrTextinput().add(new ObjectFactory().createChannel(ch10));
    for(Item item : c.getItems()){
      rdf.getChannelOrItemOrTextinput().add(ToRss10ChannelItemImpl.getInstance().execute(item));
    }
    Channel c2 =  mapper.execute(rdf);

    assertTrue("Copyright not equal!", EqualsBuilder.reflectionEquals(c.getCopyright(), c2.getCopyright()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategory(), c2.getCategory()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));

    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage(), c2.getImage()));
    
    assertTrue("Items list not equal!", EqualsBuilder.reflectionEquals(c.getItems(), c2.getItems()));
    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));

    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("ManagingEditor not equal!", EqualsBuilder.reflectionEquals(c.getManagingEditor(), c2.getManagingEditor()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput(), c2.getTexInput()));
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    assertTrue("TTL not equal!", EqualsBuilder.reflectionEquals(c.getTtl(), c2.getTtl()));
    assertTrue("WebMaster not equal!", EqualsBuilder.reflectionEquals(c.getWebMaster(), c2.getWebMaster()));

    
    File tmp = File.createTempFile("testMapping", ".xml");
    FeedWriter writer = new FeedWriter(tmp);
    writer.setFormat(FeedFormat.RSS10);
    c = Rss10Utils.read(new File( Thread.currentThread().getContextClassLoader().getResource("yarfraw/rss10/rdfModule.xml").toURI()));
    writer.writeChannel(c);
    c2 = Rss10Utils.read(tmp);
    
    assertTrue("Copyright not equal!", EqualsBuilder.reflectionEquals(c.getCopyright(), c2.getCopyright()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategory(), c2.getCategory()));
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Cloud not equal!", EqualsBuilder.reflectionEquals(c.getCloud(), c2.getCloud()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));
    assertTrue("Docs not equal!", EqualsBuilder.reflectionEquals(c.getDocs(), c2.getDocs()));
    assertTrue("Generator not equal!", EqualsBuilder.reflectionEquals(c.getGenerator(), c2.getGenerator()));
    
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getDescription(), c2.getImage().getDescription()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getRdfAttributes(), c2.getImage().getRdfAttributes()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getHeight(), c2.getImage().getHeight()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getWidth(), c2.getImage().getWidth()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getLink(), c2.getImage().getLink()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getUrl(), c2.getImage().getUrl()));
    assertTrue("Image not equal!", EqualsBuilder.reflectionEquals(c.getImage().getTitle(), c2.getImage().getTitle()));
    
    assertTrue("Items list not equal!", EqualsBuilder.reflectionEquals(c.getItems(), c2.getItems()));
    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));
    assertTrue("LastBuildDate not equal!", EqualsBuilder.reflectionEquals(c.getLastBuildDate(), c2.getLastBuildDate()));
    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("ManagingEditor not equal!", EqualsBuilder.reflectionEquals(c.getManagingEditor(), c2.getManagingEditor()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));    
    assertTrue("Otherelements not equal!", EqualsBuilder.reflectionEquals(c.getOtherElements(), c2.getOtherElements()));
    assertTrue("Otherattributes not equal!", EqualsBuilder.reflectionEquals(c.getOtherAttributes(), c2.getOtherAttributes()));
    assertTrue("SkipDays not equal!", EqualsBuilder.reflectionEquals(c.getSkipDays(), c2.getSkipDays()));
    assertTrue("SkipHours not equal!", EqualsBuilder.reflectionEquals(c.getSkipHours(), c2.getSkipHours()));
    
    
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput().getRdfAttributes(), c2.getTexInput().getRdfAttributes()));
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput().getDescription(), c2.getTexInput().getDescription()));
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput().getLink(), c2.getTexInput().getLink()));
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput().getName(), c2.getTexInput().getName()));
    
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    assertTrue("TTL not equal!", EqualsBuilder.reflectionEquals(c.getTtl(), c2.getTtl()));
    assertTrue("WebMaster not equal!", EqualsBuilder.reflectionEquals(c.getWebMaster(), c2.getWebMaster()));
    
  }
 
}


