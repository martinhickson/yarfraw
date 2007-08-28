package yarfraw.rss20;

import java.io.File;
import java.util.EnumSet;

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
import yarfraw.io.parser.ToChannelDOMParserFactory;
import static yarfraw.io.parser.CoreRssElementEnum.*;
public class ParserTest extends TestCase{
  private static final ToChannelDOMParserFactory ParserFactory = ToChannelDOMParserFactory.getInstance();
  @Test
  public void testBuild() throws Exception{

    Channel ch = BuilderTest.buildChannel();
    ch.addCategory("somecategory");
    ch.getItems().get(0).setDescription("some description");
    ch.getItems().get(0).addCategory("some item cat");
    ch.getItems().get(0).setAtomId(new AtomId("some uri"));
    File f = File.createTempFile("rss20", ".xml");
    FeedWriter writer = new FeedWriter(f, FeedFormat.RSS20);
    writer.writeChannel(ch);
                                          
    FeedReader r = new FeedReader(f, FeedFormat.RSS20);
    Channel c = r.readChannel();
    FeedParserReader pr = new FeedParserReader(f);
    Channel c2 = pr.parseChannel(ParserFactory.createParser(FeedFormat.RSS20));
    
    Item i1 = c.getItems().get(0);
    Item i2 = c2.getItems().get(0);
    assertEquals(c.getItems().size(), c2.getItems().size());
    assertEquals(i1.getAtomLinks(), i2.getAtomLinks());
    assertEquals(i1.getTitle(), i2.getTitle());
    assertEquals(i1.getDescription(), i2.getDescription());
    assertEquals(i1.getAuthor(), i2.getAuthor());
    assertTrue("category not equal", i1.getCategoryString().containsAll(i2.getCategoryString()));
    assertEquals(i1.getPubDate(), i2.getPubDate());
    assertEquals(i1.getRights(), i2.getRights());
    assertEquals(i1.getAtomId(), i2.getAtomId());
    
    assertEquals(c.getTitle(), c2.getTitle());    
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomId(), c2.getAtomId()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomLinks(), c2.getAtomLinks()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomTextAttributes(), c2.getAtomTextAttributes()));
    
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    
    
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));
    
    assertEquals(c.getLanguage(), c2.getLanguage());
    assertTrue("Link not equal!", EqualsBuilder.reflectionEquals(c.getLink(), c2.getLink()));
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    
    assertEquals(c.getTexInput(), c2.getTexInput());
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    assertEquals(c.getTtl(), c2.getTtl());
  }
  
  @Test
  public void testBuild2() throws Exception{

    FeedReader r = new FeedReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI(), FeedFormat.RSS20);
    Channel c = r.readChannel();
    
    FeedParserReader pr = new FeedParserReader(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    ToChannelDOMParserFactory parserFactory = ToChannelDOMParserFactory.getInstance();
    Channel c2 = pr.parseChannel(parserFactory.createParser(FeedFormat.RSS20));
    
    Item i1 = c.getItems().get(0);
    Item i2 = c2.getItems().get(0);
    assertEquals(c.getItems().size(), c2.getItems().size());
    assertEquals(i1.getAtomLinks(), i2.getAtomLinks());
    assertEquals(i1.getTitle(), i2.getTitle());
    assertEquals(i1.getDescription(), i2.getDescription());
    
    assertEquals(i1.getAuthor(), i2.getAuthor());
    assertEquals(i1.getCategoryString(), i2.getCategoryString());
    assertEquals(i1.getPubDate(), i2.getPubDate());
//    assertEquals(i1.getRights(), i2.getRights());
    
    assertEquals(c.getTitle(), c2.getTitle());    
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomId(), c2.getAtomId()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomLinks(), c2.getAtomLinks()));
    assertTrue("Channel not equal!", EqualsBuilder.reflectionEquals(c.getAtomTextAttributes(), c2.getAtomTextAttributes()));
    
    assertTrue("Category not equal!", EqualsBuilder.reflectionEquals(c.getCategoryString(), c2.getCategoryString()));
    assertTrue("Description not equal!", EqualsBuilder.reflectionEquals(c.getDescription(), c2.getDescription()));

    assertTrue("Language not equal!", EqualsBuilder.reflectionEquals(c.getLanguage(), c2.getLanguage()));
    
    assertTrue("PubDate not equal!", EqualsBuilder.reflectionEquals(c.getPubDate(), c2.getPubDate()));
    
    assertEquals(c.getTexInput(), c2.getTexInput());
    assertTrue("Title not equal!", EqualsBuilder.reflectionEquals(c.getTitle(), c2.getTitle()));
    
  }
  
  @Test
  public void testError() throws Exception{
    try {
      ToChannelDOMParserFactory parserFactory = ToChannelDOMParserFactory.getInstance();
      parserFactory.createParser(FeedFormat.RSS20, EnumSet.of(Channel_category));
      fail("This is expected to failed");
    } catch (Exception e) {
      //success
    }
    
    try {
      ToChannelDOMParserFactory parserFactory = ToChannelDOMParserFactory.getInstance();
      parserFactory.createParser(FeedFormat.RSS20, EnumSet.of(Item_category));
      fail("This is expected to failed");
    } catch (Exception e) {
      //success
    }
  }
  @Test
  public void testBuild3() throws Exception{
    Channel ch = BuilderTest.buildChannel();
    ch.addCategory("somecategory");
    ch.getItems().get(0).setDescription("some description");
    ch.getItems().get(0).addCategory("some item cat");
    ch.getItems().get(0).setAtomId(new AtomId("some uri"));
    ch.setImage("http://url", "title", "http://link");
    File f = File.createTempFile("rss10", ".xml");
    FeedWriter writer = new FeedWriter(f);
    writer.setFormat(FeedFormat.RSS20);
    writer.writeChannel(ch);
                                          
    FeedReader r = new FeedReader(f, FeedFormat.RSS20);
    Channel c = r.readChannel();
    FeedParserReader pr = new FeedParserReader(f);
    ToChannelDOMParserFactory parserFactory = ToChannelDOMParserFactory.getInstance();
    Channel c2 = pr.parseChannel(parserFactory.createParser(FeedFormat.RSS20,
        EnumSet.complementOf(EnumSet.of(Channel_image))));

    assertEquals(null, c2.getImage());
    assertNotNull(c.getImage());
    
    c2 = pr.parseChannel(parserFactory.createParser(FeedFormat.RSS20,
        EnumSet.complementOf(EnumSet.of(Item_author, Item_link))));
    Item i1 = c.getItems().get(0);
    Item i2 = c2.getItems().get(0);
    assertEquals(null, i2.getLink());
    assertNotNull(i1.getLink());
    
    assertEquals(null, i2.getAuthor());
    assertNotNull(i1.getAuthor());
  }
}