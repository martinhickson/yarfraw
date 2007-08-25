package yarfraw.mapping;

import org.w3c.dom.Document;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;

public interface ToChannelDOMMapper extends Functor<Channel, Document, YarfrawException>{}