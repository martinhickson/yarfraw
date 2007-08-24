package yarfraw.utils;

import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedParserReader;
import yarfraw.io.parser.FeedSAXParserSimpleImpl;

public class Test{

  public static void main(String[] args) throws YarfrawException {
    FeedParserReader p = new FeedParserReader("rdfModule.xml");
    p.parseChannel(new FeedSAXParserSimpleImpl());
    System.out.println("blah".endsWith(""));
  }

}