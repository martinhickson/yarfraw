package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;

import yarfraw.io.FeedReader;


public class Test{

  public static void main(String[] args) throws Exception {
    FeedReader r = new FeedReader(new HttpURL("http://www.fool.com/About/headlines/rss_headlines.asp"));
    r.readChannel();
  }

}