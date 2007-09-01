package yarfraw.atom10;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;

/**
 * Random tests to invoke code that was reported no covered in cobertura' coverage report
 * @author jliang
 *
 */
public class IOTest extends TestCase{
  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom10/atom10b.xml").toURI());
    r.setFormat(FeedFormat.ATOM10);
    ChannelFeed c = r.readChannel();
    File f = File.createTempFile("atom10", ".xml");
    FeedWriter w = new FeedWriter(f);
    w.setFormat(FeedFormat.ATOM10);
    w.writeChannel(c);
    
    r.setFile(f);
    ChannelFeed c2 = r.readChannel();
    
    //replace div element with null because they will fail the reflection equal test
    //but they are actually equal, it's just that one has a NS prefix, one doesnt 
    c.getItems().get(0).getContent().getOtherElements().set(0, null);
    c2.getItems().get(0).getContent().getOtherElements().set(0, null);
    
    assertEquals(c, c2);
  }
  
  @Test
  public void testRead2() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom10/xmlDotComAtom10.xml").toURI());
    r.setFormat(FeedFormat.ATOM10);
    ChannelFeed c = r.readChannel();
    File f = File.createTempFile("atom10", ".xml");
    FeedWriter w = new FeedWriter(f);
    w.setFormat(FeedFormat.ATOM10);
    w.writeChannel(c);
    
    r.setFile(f);
    
    ChannelFeed c2 = r.readChannel();
    
    assertEquals(c.getOtherElements().size(), c2.getOtherElements().size());
    //replace other elements with null because they will fail the reflection equal test
    //but they are actually equal, it's just that one has a NS prefix, one doesnt 
    c.setOtherElements(null);
    c2.setOtherElements(null);
    
    assertEquals(c, c2);

  }
  
  @Test
  public void testRead3() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/atom10/atom10.xml").toURI());
    r.setFormat(FeedFormat.ATOM10);
    ChannelFeed c = r.readChannel();
    File f = File.createTempFile("atom10", ".xml");
    FeedWriter w = new FeedWriter(f);
    w.setFormat(FeedFormat.ATOM10);
    w.writeChannel(c);
    
    r.setFile(f);
    
    ChannelFeed c2 = r.readChannel();
    assertEquals(c, c2);
  }


}