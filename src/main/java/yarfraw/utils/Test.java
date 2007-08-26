package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;

import yarfraw.io.FeedReader;

public class Test{

  public static void main(String[] args) throws Exception {
    FeedReader r = new FeedReader(new HttpURL("http://www.perezhilton.com/index.xml"));
    System.out.println(r.readChannel().getItems().get(1).getContent().getContentText());
  }

}