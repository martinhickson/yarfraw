package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.lang.StringEscapeUtils;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.io.FeedReader;


public class Test{

  public static void main(String[] args) throws Exception {
    FeedReader r = new FeedReader(new HttpURL("http://www.geonames.org/recent-changes.xml"));
    ChannelFeed c =  r.readChannel();
    System.out.println(c);
    System.out.println(System.getProperty("java.io.tmpdir"));

    System.out.println(StringEscapeUtils.unescapeXml("&lt;div xmlns=&quot;http://www.w3.org/1999/xhtml&quot;&gt;&lt;p&gt;&lt;i&gt;"+
  "[Update: The Atom draft is finished.]&lt;/i&gt;&lt;/p&gt;&lt;/div&gt;"));
  }

}