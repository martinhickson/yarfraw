package yarfraw.rss10;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
/**
 * Some unit tests for Reader/Writer/Appender
 * 
 * @author jliang
 *
 */
public class IOTest extends TestCase{

  @Test
  public void testRead() throws Exception{
    FeedReader r = new FeedReader( Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    Channel c = r.readChannel();
    
    File f = File.createTempFile("rss10test", ".xml");
    FeedWriter w = new FeedWriter(f);
    w.setFormat(FeedFormat.RSS10);
    w.writeChannel(c);
    
    r.setFormat(FeedFormat.RSS10);
    r.setFile(f);
    Channel c2 = r.readChannel();
    assertEquals("digg", c2.getTitle());
    //TODO: write test
  }
  
  
}


