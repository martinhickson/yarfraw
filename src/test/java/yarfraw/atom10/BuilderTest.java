package yarfraw.atom10;

import java.io.File;
import java.util.Locale;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.io.FeedWriter;
import yarfraw.utils.XMLUtils;

public class BuilderTest extends TestCase{
  
  public static ChannelFeed buildChannel() throws Exception{
    return  new ChannelFeed()
    .setLang(Locale.ENGLISH)
    .setTitle("dive into mark")
    .setDescriptionOrSubtitle("A <em>lot</em> of effort went into making this effortless")
    .setPubDate("2005-07-10T12:29:29Z")
    .setUid("tag:example.org,2003:3")
    .addLink(new Link("http://example.org/")
                      .setHreflang(Locale.ENGLISH)
                      .setRel("alternate")
                      .setType("text/html"))
    .addLink(new Link("http://example.org/feed.atom")
                      .setRel("self")
                      .setType("application/atom+xml"))
    .setRights("Copyright (c) 2003, Mark Pilgrim")
    .setGenerator(new Generator("Example Toolkit")
                              .setVersion("1.0")
                              .setUri("http://www.example.com/"))
    
    .addItem(new ItemEntry()
          .setTitle("Atom draft-07 snapshot")
          .addLink(new Link("http://example.org/2005/04/02/atom")
                      .setRel("alternate")
                      .setType("text/html"))
          .addLink(new Link("http://example.org/audio/ph34r_my_podcast.mp3")
                          .setRel("enclosure")
                          .setType("audio/mpeg")
                          .setLength(1337))                      
          
          .setUid("tag:example.org,2003:3.2397")
          .setUpdatedDate("2003-12-13T08:29:29-04:00")
          .setPubDate("2003-12-13T08:29:29-04:00")
          .addAuthorOrCreator(new Person("f8dy@example.com")
                                  .setName("Mark Pilgrim")
                                  .setUri("http://example.org/"))
          .addContributor(new Person().setName("Sam Ruby"))
          .addContributor(new Person().setName("Joe Gregorio"))
          .setContent(new Content().setType("xhtml")
                                   .setBase("http://diveintomark.org/")
                                   .setLang(Locale.US)
                                   .addOtherElement("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
                                           "<p><i>[Update: The Atom draft is finished.]</i></p>"+
                                         "</div>")));
  }
  
  @Test
  public void testBuild() throws Exception{

    ChannelFeed ch = buildChannel();
    FeedWriter writer = new FeedWriter(File.createTempFile("atom10",".xml"));
    writer.setFormat(FeedFormat.ATOM10);
    writer.writeChannel(ch);
    
    ch.validate(FeedFormat.ATOM10);
                                                              
  }
  
  @Test
  public void testAtomContent() throws Exception{
    Content content = new Content()
                         .addContentText("text content")
                         .addOtherAttributes(new QName("http://ns", "myattr"), "value")
                         .addOtherElement(XMLUtils.parseXml("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
                                         "<p><i>[Update: The Atom draft is finished.]</i></p>"+
                                       "</div>", false, false).getDocumentElement());
    assertTrue("content not correctly built", content.getContentText().size()==1);
    assertTrue("content not correctly built", content.getOtherAttributes().containsValue("value"));
    assertTrue("content not correctly built", content.getOtherElements().size() ==1);
  }
  
  @Test
  public void testAtomId() throws Exception{
    Id id = new Id()
               .addOtherAttributes(new QName("http://ns", "myattr"), "value")
               .setBase("base")
               .setLang(Locale.ENGLISH);
    assertTrue("content not correctly built", id.getLang().equals(Locale.ENGLISH.getLanguage()));
    assertTrue("content not correctly built", id.getOtherAttributes().containsValue("value"));
    assertTrue("content not correctly built", id.getBase().equals("base"));
  }
}