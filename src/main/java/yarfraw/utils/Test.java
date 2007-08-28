package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedParserReader;
import yarfraw.io.parser.ToChannelDOMParserFactory;

public class Test{

  public static void main(String[] args) throws Exception {
//    FeedReader r = new FeedReader(new HttpURL("http://www.perezhilton.com/index.xml"));
//    System.out.println(r.readChannel().getItems().get(1).getContent().getContentText());
    FeedParserReader pr = new FeedParserReader(new HttpURL("http://www.perezhilton.com/index.xml"));
    System.out.println(pr.parseChannel(ToChannelDOMParserFactory.getInstance().createParser(FeedFormat.RSS20)).getItems().get(1).getContent().getContentText());
  }

}