package yarfraw.rss20;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.ValidationException;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
/**
 * Some unit tests.
 * 
 * @author jliang
 *
 */
public class BuilderTest{
    


  public static ChannelFeed buildChannel() throws MalformedURLException, URISyntaxException{
      ChannelFeed channel = new ChannelFeed().setTitle("Liftoff News")
                                             .addLink("http://liftoff.msfc.nasa.gov/")
                                             .setDescriptionOrSubtitle("Liftoff to Space Exploration.")
                                             .setLang("en-us")
                                             .setPubDate("Tue, 10 Jun 2003 04:00:00 GMT")
                                             .setLastBuildOrUpdatedDate("Tue, 10 Jun 2003 09:41:01 GMT")
                                             .setDocs("http://blogs.law.harvard.edu/tech/rss")
                                             .setGenerator("Weblog Editor 2.0")
                                             .addManagingEditorOrAuthorOrPublisher("editor@example.com")
                                             .addWebMasterOrCreator("webmaster@example.com")
                             .addItem(new ItemEntry().setTitle("Star City")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp")
                                                     .setDescriptionOrSummary("How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\"&gt;Star City&lt;/a&gt;.")
                                                     .setPubDate("Tue, 03 Jun 2003 09:39:21 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"))
                             .addItem(new ItemEntry().setDescriptionOrSummary("Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a &lt;a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\"&gt;partial eclipse of the Sun&lt;/a&gt; on Saturday, May 31st.")
                                                     .setPubDate("Fri, 30 May 2003 11:06:42 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/30.html#item572"))
                             .addItem(new ItemEntry().setTitle("The Engine That Does More")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp")
                                                     .setDescriptionOrSummary("Before man travels to Mars, NASA hopes to design new engines that will let us fly through the Solar System more quickly.  The proposed VASIMR engine would do that.")
                                                     .setPubDate("Tue, 27 May 2003 08:37:32 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/27.html#item571"))
                             .addItem(new ItemEntry().setTitle("Astronauts' Dirty Laundry")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp</")
                                                     .setDescriptionOrSummary("Compared to earlier spacecraft, the International Space Station has many luxuries, but laundry facilities are not one of them.  Instead, astronauts have other options.")
                                                     .setPubDate("Tue, 20 May 2003 08:56:02 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/20.html#item570"));                                                     
                                                     
      return channel;
  }


  @Test
  public void testValidation() throws MalformedURLException, URISyntaxException{
    ChannelFeed c = buildChannel();
    c.setItems(null);
    try {
      c.validate(FeedFormat.RSS20);
      fail("Expecting validation error");
    } catch (ValidationException e) {
      //success
    }
  }
  
  @Test
  public void testValidation2() throws MalformedURLException, URISyntaxException{
    ChannelFeed c = buildChannel();
    c.addLink((String)null);
    try {
      c.validate(FeedFormat.RSS20);
      fail("Expecting validation error");
    } catch (ValidationException e) {
      //success
    }
  }
 
  @Test
  public void testOtherElements() throws Exception {
    ChannelFeed c = buildChannel();
    
    c.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    c.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    c.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");
    
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document doc = factory.newDocumentBuilder().newDocument();
    c.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    ItemEntry item = new ItemEntry().setTitle("title")
                          .setDescriptionOrSummary("desc")
                          .addLink("http://my.company.com");
    item.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    item.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    item.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");    
    
    item.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    c.addItem(item);
    File file = File.createTempFile("YarfrawTestOtherElements", ".xml");
    FeedWriter w = new FeedWriter(file);
    w.writeChannel(c);
    
    //make sure we can read it back
    FeedReader reader = new FeedReader(file);
    reader.readChannel();
  }
}


