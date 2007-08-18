package yarfraw.rss10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.Day;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Guid;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
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
    
  private static final int _60 = 60;
  private static final String DESCRIPTION = "Descritpion ......... ";
  private static final String ITEM2 = "Item2";
  private static final String GUID = "GUID";
  private static final String HTTP_WWW_MYBLOG_ORG_CGI_LOCAL_MT_MT_COMMENTS_CGI_ENTRY_ID_290 = "http://www.myblog.org/cgi-local/mt/mt-comments.cgi?entry_id=290";
  private static final String HTTP_SOMEDOMAIN = "http://somedomain";
  private static final String CAT3 = "cat3";
  private static final String OPRAH_OXYGEN_NET = "oprah@oxygen.net";
  private static final String DESC = "desc";
  private static final String HTTP_SOMELINK_COM = "http://somelink.com/";
  private static final String ITEM_1 = "Item 1";
  private static final String HTTP_LINK_COM_LINK = "http://link.com/link";
  private static final String NAME = "name";
  private static final String DESCRITPION = "Descritpion";
  private static final String TITLE = "Title";
  private static final String TEST_IMAGE = "Test Image";
  private static final String HTTP_MY_IMAGE_COM_IMAGE_JPG = "http://my.image.com/image.jpg";
  private static final String SOAP = "soap";
  private static final String PING_ME = "pingMe";
  private static final String RPC2 = "/RPC2";
  private static final String RPC_SYS_COM = "rpc.sys.com";
  private static final Cloud CLOUD = new Cloud(RPC_SYS_COM, 80, RPC2, PING_ME,SOAP);
  private static final String HTTP_BLOGS_LAW_HARVARD_EDU_TECH_RSS = "http://blogs.law.harvard.edu/tech/rss";
  private static final String MIGHTY_IN_HOUSE_CONTENT_SYSTEM_V2_3 = "MightyInHouse Content System v2.3";
  private static final String CAT2 = "cat2";
  private static final String CAT1 = "cat1";
  private static final String BETTY_HERALD_COM_BETTY_GUERNSEY = "betty@herald.com (Betty Guernsey)";
  private static final String GEO_HERALD_COM = "geo@herald.com";
  private static final String COPYRIGHT_2002_SPARTANBURG_HERALD_JOURNAL = "Copyright 2002, Spartanburg Herald-Journal";
  private static final String HTTP_WWW_TEST_COM = "http://www.test.com";
  private static final String TEST_TITLE = "Test Title";


  public static Channel buildChannel() throws MalformedURLException, URISyntaxException{
      Channel channel = new Channel().setTitle(TEST_TITLE)
      .setLink("    "+HTTP_WWW_TEST_COM)
      .setDescription(DESCRIPTION)
      .setCopyright(COPYRIGHT_2002_SPARTANBURG_HERALD_JOURNAL)
      .setManagingEditor(GEO_HERALD_COM)
      .setWebMaster(BETTY_HERALD_COM_BETTY_GUERNSEY)
      .setLanguage(Locale.ENGLISH)
      .setPubDate(new Date(System.currentTimeMillis()))
      .setLastBuildDate(new Date(System.currentTimeMillis()))
      .addCategory(CAT1)
      .addCategory(CAT2)
      .setGenerator(MIGHTY_IN_HOUSE_CONTENT_SYSTEM_V2_3)
      .setDocs("    "+HTTP_BLOGS_LAW_HARVARD_EDU_TECH_RSS+"\t\t")
      .setCloud(CLOUD)
      .setTtl(_60)
      .setImage("   "+HTTP_MY_IMAGE_COM_IMAGE_JPG, TEST_IMAGE, "   "+HTTP_MY_IMAGE_COM_IMAGE_JPG)
      .setTexInput(new TextInput(TITLE, DESCRITPION, NAME, HTTP_LINK_COM_LINK))
      .addSkipDay(Day.Saturday, Day.Sunday)
      .addSkipHour(12, 0, 1, 2, 3, 4, 5)
      
      .additem(new Item().setTitle(ITEM_1)
                         .setLink("    "+HTTP_SOMELINK_COM)
                         .setDescription(DESC)
                         .setAuthor(OPRAH_OXYGEN_NET)
                         .addCategory(CAT1, CAT2)
                         .addCategory(new Category(CAT3).setDomain(HTTP_SOMEDOMAIN))
                         .setComments("    "+HTTP_WWW_MYBLOG_ORG_CGI_LOCAL_MT_MT_COMMENTS_CGI_ENTRY_ID_290)
                         .setGuid(new Guid(GUID)),
               new Item().setTitle(ITEM2)
                         .setLink(HTTP_SOMELINK_COM)
                         .setDescription(DESC)
                         .setAuthor(OPRAH_OXYGEN_NET)
                         .addCategory(CAT1, CAT2)
                         .addCategory(new Category(CAT3).setDomain(HTTP_SOMEDOMAIN))
                         .setComments(HTTP_WWW_MYBLOG_ORG_CGI_LOCAL_MT_MT_COMMENTS_CGI_ENTRY_ID_290+"\t\t")
                         .setGuid(new Guid(GUID)));
      return channel;
  }

  @Test
  public void testBuild() throws Exception{
    Channel c = buildChannel();
    assertEquals(c.getTitle(), TEST_TITLE);
    assertTrue("Category did not build correctly", 
            c.getCategory().containsAll(Arrays.asList(new Category(CAT1), new Category(CAT2))));
    
    //FIXME: switch expected with actual 
    assertEquals(c.getCloud().getDomain(), RPC_SYS_COM);
    assertEquals(c.getCloud().getPort(), 80);
    assertEquals(c.getCloud().getProtocol(), SOAP);
    assertEquals(c.getCloud().getRegisterProcedure(), PING_ME);
    assertEquals(c.getCloud().getPath(), RPC2);
    
    assertEquals(c.getCopyright(), COPYRIGHT_2002_SPARTANBURG_HERALD_JOURNAL);
    assertEquals(c.getDescription(), DESCRIPTION);
    assertEquals(c.getDocs(), new URI(HTTP_BLOGS_LAW_HARVARD_EDU_TECH_RSS));
    assertEquals(c.getGenerator(), MIGHTY_IN_HOUSE_CONTENT_SYSTEM_V2_3);
    assertEquals(c.getImage(), new Image(HTTP_MY_IMAGE_COM_IMAGE_JPG, TEST_IMAGE, HTTP_MY_IMAGE_COM_IMAGE_JPG));
    assertEquals(c.getLanguage(), Locale.ENGLISH);
    assertEquals(c.getLink(), new URI(HTTP_WWW_TEST_COM));
    assertEquals(c.getManagingEditor(), GEO_HERALD_COM);
    assertTrue("Skipdays did not build correctly", 
            c.getSkipDays().containsAll(Arrays.asList(Day.Saturday, Day.Sunday)));
    assertTrue("Skiphours did not build correctly", 
            c.getSkipHours().containsAll(Arrays.asList(12, 0, 1, 2, 3, 4, 5)));
    assertEquals(c.getTtl(), new Integer(_60));
    assertEquals(c.getWebMaster(), BETTY_HERALD_COM_BETTY_GUERNSEY);
    assertEquals(2, c.getItems().size());
    Item item = c.getItems().get(0);
    assertEquals(ITEM_1, item.getTitle());
    assertEquals(DESC, item.getDescription());
    assertEquals(OPRAH_OXYGEN_NET, item.getAuthor());
    assertTrue("Category did not build correctly", 
        item.getCategory().containsAll(Arrays.asList(new Category(CAT1), new Category(CAT2), new Category(CAT3, HTTP_SOMEDOMAIN))));
    assertEquals(new URI(HTTP_WWW_MYBLOG_ORG_CGI_LOCAL_MT_MT_COMMENTS_CGI_ENTRY_ID_290), item.getComments());
    assertEquals(new Guid(GUID), item.getGuid());

  }  

  @Test
  public void testOtherElements() throws Exception {
    Channel c = buildChannel();
    
    c.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    c.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    c.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");
    
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document doc = factory.newDocumentBuilder().newDocument();
    c.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    Item item = new Item().setTitle("title")
                          .setDescription("desc")
                          .setLink("http://my.company.com");
    item.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    item.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    item.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");    
    
    item.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    c.additem(item);
    File file = File.createTempFile("YarfrawTestOtherElements",".xml");
    FeedWriter w = new FeedWriter(file);
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
    
    //make sure we can read it back
    FeedReader reader = new FeedReader(file);
    reader.setFormat(FeedFormat.RSS10);
    Channel ch = reader.readChannel();
    
    
    assertEquals(ch.getCopyright(), COPYRIGHT_2002_SPARTANBURG_HERALD_JOURNAL);
    assertEquals(ch.getDescription(), DESCRIPTION);
    
    assertEquals(ch.getImage(), new Image(HTTP_MY_IMAGE_COM_IMAGE_JPG, TEST_IMAGE, HTTP_MY_IMAGE_COM_IMAGE_JPG));
    assertEquals(ch.getLanguage(), Locale.ENGLISH);
    assertEquals(ch.getLink(), new URI(HTTP_WWW_TEST_COM));
    assertEquals(ch.getManagingEditor(), GEO_HERALD_COM);
    
    assertEquals(ch.getTtl(), new Integer(_60));
    assertEquals(ch.getWebMaster(), BETTY_HERALD_COM_BETTY_GUERNSEY);

    assertEquals(3, ch.getItems().size());
    item = ch.getItems().get(0);
    assertEquals(ITEM_1, item.getTitle());
    assertEquals(DESC, item.getDescription());
    assertEquals(OPRAH_OXYGEN_NET, item.getAuthor());
    assertTrue("Category did not build correctly", 
        item.getCategory().containsAll(Arrays.asList(new Category(CAT1), new Category(CAT2), new Category(CAT3))));
    
  }
}


