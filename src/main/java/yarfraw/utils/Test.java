package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.io.FeedReader;


public class Test{

  public static void main(String[] args) throws Exception {
    FeedReader r = new FeedReader(new HttpURL("http://news.google.com/?output=atom"));
    ChannelFeed c =  r.readChannel();
    System.out.println(c);
    System.out.println(System.getProperty("java.io.tmpdir"));
  }

}