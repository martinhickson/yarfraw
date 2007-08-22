package yarfraw.utils;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

public class Test{

  public static void main(String[] args) throws YarfrawException {
    FeedReader r = new FeedReader("atom10.xml");
    r.setFormat(FeedFormat.ATOM10);
    Channel c = r.readChannel();
    System.out.println(c);
  }

}