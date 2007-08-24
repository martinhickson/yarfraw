package yarfraw.io.parser;

import org.xml.sax.ext.DefaultHandler2;

import yarfraw.core.datamodel.Channel;

public abstract class FeedSAXParserHandler extends DefaultHandler2{
  abstract public Channel getChannel();
}