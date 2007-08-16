package yarfraw.rss20.io;

import java.net.URI;

public class Rss20Aggregator{
  public static void main(String[] args) throws Exception{
    Rss20Reader r = new Rss20Reader(new URI("http://digg.com/rss/index.xml"));
    r.readChannel();
  }
}