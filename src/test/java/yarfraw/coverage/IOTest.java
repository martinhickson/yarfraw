package yarfraw.coverage;

import java.io.File;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.atom10.BuilderTest;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedAppender;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;

/**
 * Random tests to invoke code that was reported no covered in cobertura' coverage report
 * @author jliang
 *
 */
public class IOTest extends TestCase{
  @Test
  public void testAppender() throws Exception{
    FeedAppender a = new FeedAppender("test.xml");
    assertTrue("default format is Rss 2.0", a.getFormat()==FeedFormat.RSS20);
    a.setFormat(FeedFormat.RSS10);
    assertTrue("format should be Rss 1.0", a.getFormat()==FeedFormat.RSS10);
    a.setFormat(FeedFormat.ATOM10);
    assertTrue("format should be Atom 1.0", a.getFormat()==FeedFormat.ATOM10);
    
    a.setNumItemToKeep(10);
    
    assertTrue("num to keep not correct", a.getNumItemToKeep() == 10);
  }
  
  @Test
  public void testInputStream() throws Exception{
    Channel rss20  = FeedReader.readChannel(FeedFormat.RSS20, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/digg.xml"));
    Channel rss10  = FeedReader.readChannel(FeedFormat.RSS10, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/rss10/rdfModule.xml"));
    Channel atom10  = FeedReader.readChannel(FeedFormat.ATOM10, 
            Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/atom10/atom10b.xml"));
    assertTrue(rss20.getTitle() != null);
    assertTrue(rss10.getTitle() != null);
    assertTrue(atom10.getTitle() != null);
  }
  
  @Test
  public void testOutputStream() throws Exception{
    File f = File.createTempFile("atom10", ".xml");
    FeedWriter.writeChannel(FeedFormat.ATOM10, BuilderTest.buildChannel(), 
            new FileOutputStream(f));
    
  }
}