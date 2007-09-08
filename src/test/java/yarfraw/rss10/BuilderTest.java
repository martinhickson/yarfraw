package yarfraw.rss10;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.TextInput;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
/**
 * Some unit tests.
 * TODO: this really needs some cleanup
 * @author jliang
 *
 */
public class BuilderTest{

  public static ChannelFeed buildChannel() throws Exception{
      ChannelFeed channel = new ChannelFeed().setAbout("http://meerkat.oreillynet.com/?_fl=rss1.0")
              .setTitle("Meerkat")
              .addLink("http://meerkat.oreillynet.com<")
              .setDescriptionOrSubtitle("Meerkat: An Open Wire Service")
              .addManagingEditorOrAuthorOrPublisher("The O'Reilly Network")
              .addWebMasterOrCreator("Rael Dornfest (mailto:rael@oreilly.com)")
              .setRights("Copyright &#169; 2000 O'Reilly &amp; Associates, Inc.")
              .setPubDate("2000-01-01T12:00+00:00")
              //30 min ttl will be converted to {udpatePeriod:hourly, updateFrequency:2}
              //updateBase is not supported directly, use addOtherElement to add it manually
              //if you really want to add it
              .setTtl(30)
              //the resource attribute is automatically set to be the same as the 'about' attribute
              .setImageOrIcon(new Image().setAbout("http://meerkat.oreillynet.com/icons/meerkat-powered.jpg")
                                   .setTitle("Meerkat Powered!")
                                   .setLink("http://meerkat.oreillynet.com")
                                   .setUrl("http://meerkat.oreillynet.com/icons/meerkat-powered.jpg"))
              .setTexInput(new TextInput().setAbout("http://meerkat.oreillynet.com")
                                          .setTitle("Search Meerkat")
                                          .setDescription("Search Meerkat's RSS Database...")
                                          .setName("s")
                                          .setLink("http://meerkat.oreillynet.com/")
                                          //<ti:function> and <ti:inputType> are not supported directly
                                          //no problems, just add them manually
                                          .addOtherElement("<ti:function xmlns:ti=\"http://purl.org/rss/1.0/modules/textinput/\">search</ti:function>")
                                          .addOtherElement("<ti:inputType xmlns:ti=\"http://purl.org/rss/1.0/modules/textinput/\">regex</ti:inputType>")
                                          )
              .addItem(new ItemEntry().setTitle("XML: A Disruptive Technology")
                                 .addLink("http://c.moreover.com/click/here.pl?r123")
                                 .setDescriptionOrSummary("XML is placing increasingly heavy loads on the existing technical infrastructure of the Internet.")
                                 .addAuthorOrCreator("Simon St.Laurent (mailto:simonstl@simonstl.com)")
                                 //Doh! publisher not supported, add it manually again
                                 .addOtherElement("<dc:publisher xmlns:dc=\"http://purl.org/dc/elements/1.1/\">The O'Reilly Network</dc:publisher>")
                                 .setRights("Copyright &#169; 2000 O'Reilly &amp; Associates, Inc.</dc:")
                                 .addCategorySubject("XML")
                                 //no supports for <co:*> elements
                                 .addOtherElement("<co:name xmlns:co=\"http://purl.org/rss/1.0/modules/company/\">XML.com</co:name>")
                                 .addOtherElement("<co:market xmlns:co=\"http://purl.org/rss/1.0/modules/company/\">NASDAQ</co:market>")
                                 .addOtherElement("<co:symbol xmlns:co=\"http://purl.org/rss/1.0/modules/company/\">XML</co:symbol>")
                                 );
      return channel;
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
    File file = new File("testTmpOutput/rss10/testOtherElements.xml");;
    FeedWriter w = new FeedWriter(file);
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
    
    //make sure we can read it back
    FeedReader reader = new FeedReader(file);
    reader.setFormat(FeedFormat.RSS10);
    ChannelFeed ch = reader.readChannel();

    assertEquals(2, ch.getItems().size());
    item = ch.getItems().get(0);
    
  }
  
  @Test
  public void testBuild2() throws Exception{
    ChannelFeed c = buildChannel();
    FeedWriter w = new FeedWriter("testTmpOutput/rss10/testBuild.xml");
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
  }
  

}


