package yarfraw.io.parser;

import org.w3c.dom.Document;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.mapping.Functor;

public interface ToChannelDOMParser extends Functor<Channel, Document, YarfrawException>{}