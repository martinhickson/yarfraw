package yarfraw.atom10;

import java.text.SimpleDateFormat;
import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.AtomContent;
import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Item;
import yarfraw.io.FeedWriter;
import yarfraw.utils.Utils;

import static yarfraw.core.datamodel.AtomTextAttributes.TextType.*;

public class BuilderTest extends TestCase{
  
  @Test
  public void testBuild() throws Exception{
    Channel ch = Channel.create()
      .setTitle("dive into mark")
      .setDescription("A <em>lot</em> of effort went into making this effortless")
      .setPubDate("2005-07-10T12:29:29Z", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"))
      .setAtomId(new AtomId("tag:example.org,2003:3"))
      .setLink("http://example.org/")
      // . multiple link is not supported, i dont see any benefit of having multiple link
      .setCopyright("Copyright (c) 2003, Mark Pilgrim")
      .setGenerator("Example Toolkit")
      .putAtomTextAttribute(AtomTextElementEnum.title, new AtomTextAttributes(text))
      .putAtomTextAttribute(AtomTextElementEnum.subtitle, new AtomTextAttributes(html))
      .additem(Item.create()
            .setTitle("Atom draft-07 snapshot")
            .setLink("http://example.org/2005/04/02/atom")
            .setAtomId(new AtomId("tag:example.org,2003:3.2397"))
            .setPubDate(Utils.tryParseISODate("2003-12-13T08:29:29-04:00"))
            //person's name, uri elements are not supported
            .setAuthor("f8dy@example.com")
            //contributor element are not supported
            .setAtomContent(new AtomContent().setType(xhtml)
                                             .setBase("http://diveintomark.org/")
                                             .setLang(Locale.US)
                                             .addOtherElement("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
                                                     "<p><i>[Update: The Atom draft is finished.]</i></p>"+
                                                   "</div>")));
    
    FeedWriter writer = new FeedWriter("atom10.xml");
    writer.setFormat(FeedFormat.ATOM10);
    writer.writeChannel(ch);
                                                              
  }
}