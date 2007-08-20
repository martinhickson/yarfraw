package yarfraw.coverage;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedAppender;

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
}