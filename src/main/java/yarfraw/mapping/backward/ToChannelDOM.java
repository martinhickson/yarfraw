package yarfraw.mapping.backward;

import org.w3c.dom.Document;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.mapping.Functor;

public interface ToChannelDOM extends Functor<Channel, Document, YarfrawException>{
  public void setFeedFormat(FeedFormat format);
}