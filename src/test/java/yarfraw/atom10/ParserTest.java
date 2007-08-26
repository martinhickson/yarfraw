package yarfraw.atom10;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.io.FeedParserReader;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.mapping.backward.impl.parser.ToChannelDOMParserAtomImpl;

public class ParserTest extends TestCase{

  @Test
  public void testBuild() throws Exception{

    Channel ch = BuilderTest.buildChannel();
    ch.addCategory("somecategory");
    ch.getItems().get(0).setDescription("some description");
    ch.getItems().get(0).addCategory("some item cat");
    ch.getItems().get(0).setAtomId(new AtomId("some uri"));
    File f = File.createTempFile("test", ".xml");
    FeedWriter writer = new FeedWriter(f);
    writer.setFormat(FeedFormat.ATOM10);
    writer.writeChannel(ch);
                                          
    FeedReader r = new FeedReader(f, FeedFormat.ATOM10);
    Channel c = r.readChannel();
    FeedParserReader pr = new FeedParserReader(f);
    Channel c2 = pr.parseChannel(new ToChannelDOMParserAtomImpl());
    
    Item i1 = c.getItems().get(0);
    Item i2 = c.getItems().get(0);
    assertEquals(c.getItems().size(), c2.getItems().size());
    assertEquals(i1.getAtomLinks(), i2.getAtomLinks());
    assertEquals(i1.getTitle(), i2.getTitle());
    assertEquals(i1.getDescription(), i2.getDescription());
    assertEquals(i1.getAuthor(), i2.getAuthor());
    assertEquals(i1.getCategoryString(), i2.getCategoryString());
    assertEquals(i1.getPubDate(), i2.getPubDate());
    assertEquals(i1.getRights(), i2.getRights());
    assertEquals(i1.getAtomId(), i2.getAtomId());
    
    assertEquals(c.getTitle(), c2.getTitle());    
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomId(), c2.getAtomId()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomLinks(), c2.getAtomLinks()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomTextAttributes(), c2.getAtomTextAttributes()));
    
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));
    
    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));
    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput(), c2.getTexInput()));
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    
  }
  
  @Test
  public void testBuild2() throws Exception{

    
    FeedReader r = new FeedReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom10/atom10b.xml").toURI(), FeedFormat.ATOM10);
    Channel c = r.readChannel();
    
    FeedParserReader pr = new FeedParserReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom10/atom10b.xml").toURI());
    Channel c2 = pr.parseChannel(new ToChannelDOMParserAtomImpl());
    
    Item i1 = c.getItems().get(0);
    Item i2 = c.getItems().get(0);
    assertEquals(c.getItems().size(), c2.getItems().size());
    assertEquals(i1.getAtomLinks(), i2.getAtomLinks());
    assertEquals(i1.getTitle(), i2.getTitle());
    assertEquals(i1.getDescription(), i2.getDescription());
    assertEquals(i1.getAuthor(), i2.getAuthor());
    assertEquals(i1.getCategoryString(), i2.getCategoryString());
    assertEquals(i1.getPubDate(), i2.getPubDate());
    assertEquals(i1.getRights(), i2.getRights());
    
    assertEquals(c.getTitle(), c2.getTitle());    
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomId(), c2.getAtomId()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomLinks(), c2.getAtomLinks()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomTextAttributes(), c2.getAtomTextAttributes()));
    
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));
    
    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));
    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    
    assertTrue("TextInput not equal!", EqualsBuilder.reflectionEquals(c.getTexInput(), c2.getTexInput()));
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    
  }
}