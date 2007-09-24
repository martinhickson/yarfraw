package yarfraw.extension;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.generated.atom03.ext.elements.Atom03Extension;
import yarfraw.io.FeedReader;
import yarfraw.utils.extension.ExtensionUtils;

public class Atom03Test extends TestCase{
  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom03/atom03.xml").toURI());
    r.setFormat(FeedFormat.ATOM03);
    ChannelFeed c = r.readChannel();
    assertEquals(26, c.getItems().size());
    
    assertEquals("<b>Iranian president spars with academics in NY - Reuters Canada</b>", c.getItems().get(0).getTitleText());
    Atom03Extension ext = ExtensionUtils.extractAtom03Extension(c.getItems().get(0).getOtherElements());
    assertEquals("2007-09-24T19:39:29+00:00", ext.getIssued());
    
    assertEquals("Top Stories", ext.getSummary().getContent().get(0));
    assertEquals("text/html", ext.getContent().get(0).getType());
    assertEquals("escaped", ext.getContent().get(0).getMode());
//    System.out.println(ToStringBuilder.reflectionToString(ext.getContent().get(0)));
  }
}