package yarfraw.io.parser;

import org.xml.sax.ext.DefaultHandler2;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;

public abstract class FeedSAXParser extends DefaultHandler2{
  protected FeedFormat _format = FeedFormat.RSS20;
  abstract public Channel getChannel();

  public void setFormat(FeedFormat format) {
    _format = format;
  } 
}