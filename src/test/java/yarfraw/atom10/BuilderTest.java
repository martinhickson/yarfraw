package yarfraw.atom10;

import static yarfraw.core.datamodel.AtomTextAttributes.TextType.html;
import static yarfraw.core.datamodel.AtomTextAttributes.TextType.text;
import static yarfraw.core.datamodel.AtomTextAttributes.TextType.xhtml;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.AtomContent;
import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.io.FeedWriter;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.XMLUtils;

public class BuilderTest extends TestCase{
  
  public static Channel buildChannel() throws Exception{
    return  Channel.create()
    .setTitle("dive into mark")
    .setDescription("A <em>lot</em> of effort went into making this effortless")
    .setPubDate("2005-07-10T12:29:29Z", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"))
    .setAtomId(new AtomId("tag:example.org,2003:3"))
    .setLink("http://example.org/")
    .addAtomLink(new AtomLink().setHref("http://example.org/")
                               .setRel("alternate")
                               .setType("text/html")
                               .setHreflang("en"))
    .addAtomLink(new AtomLink().setHref("http://example.org/feed.atom")
                              .setRel("self")
                              .setType("application/atom+xml"))
    .setCopyright("Copyright (c) 2003, Mark Pilgrim")
    .setGenerator("Example Toolkit")
    .putAtomTextAttribute(AtomTextElementEnum.title, new AtomTextAttributes(text))
    .putAtomTextAttribute(AtomTextElementEnum.subtitle, new AtomTextAttributes(html))
    .additem(Item.create()
          .setTitle("Atom draft-07 snapshot")
          .addAtomLink(new AtomLink().setHref("http://example.org/2005/04/02/atom")
                               .setRel("alternate")
                               .setType("text/html"))
          .addAtomLink(new AtomLink().setHref("http://example.org/audio/ph34r_my_podcast.mp3")
                              .setRel("enclosure")
                              .setType("audio/mpeg")
                              .setLength(1337))
          .setAtomId(new AtomId("tag:example.org,2003:3.2397"))
          .setPubDate(CommonUtils.tryParseISODate("2003-12-13T08:29:29-04:00"))
          //person's name, uri elements are not supported
          .setAuthor("f8dy@example.com")
          //contributor element are not supported
          .setAtomContent(new AtomContent().setType(xhtml)
                                           .setBase("http://diveintomark.org/")
                                           .setLang(Locale.US)
                                           .addOtherElement("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
                                                   "<p><i>[Update: The Atom draft is finished.]</i></p>"+
                                                 "</div>")));
  }
  
  @Test
  public void testBuild() throws Exception{

    Channel ch = buildChannel();
    FeedWriter writer = new FeedWriter("atom10.xml");
    writer.setFormat(FeedFormat.ATOM10);
    writer.writeChannel(ch);
                                                              
  }
  
  @Test
  public void testAtomContent() throws Exception{
    AtomContent content = AtomContent.create()
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
    AtomId id = AtomId.create()
                                     .addOtherAttributes(new QName("http://ns", "myattr"), "value")
                                     .setBase("base")
                                     .setLang(Locale.ENGLISH);
    assertTrue("content not correctly built", id.getLang()==Locale.ENGLISH);
    assertTrue("content not correctly built", id.getOtherAttributes().containsValue("value"));
    assertTrue("content not correctly built", id.getBase().equals("base"));
  }
}