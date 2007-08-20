package yarfraw.utils;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import yarfraw.core.datamodel.FeedFormat;

public class Test{
  public static void main(String[] args) throws Exception {

    InputStream input = null;
    try {
      input = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/atom10/atom10.xml");
      FeedFormat format = FeedFormatDetector.getFormat(input);
      System.out.println(format);
    }finally{
      IOUtils.closeQuietly(input);
    }
  }
  
  
}