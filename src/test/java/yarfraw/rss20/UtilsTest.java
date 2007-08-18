package yarfraw.rss20;

import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.utils.FeedFormatDetector;

/**
 * Util class tests.
 * 
 * @author jliang
 *
 */
public class UtilsTest extends TestCase{
  @Test
  public void testFormatDetection() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/digg.xml");
      assertEquals(FeedFormat.RSS20, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  @Test
  public void testFormatDetection2() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/rss10/rdf.xml");
      assertEquals(FeedFormat.RSS10, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  
  
  
}