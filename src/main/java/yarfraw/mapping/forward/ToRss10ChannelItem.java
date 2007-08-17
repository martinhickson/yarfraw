package yarfraw.mapping.forward;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.TRss10Item;
import yarfraw.mapping.Functor;

public interface ToRss10ChannelItem  extends Functor<JAXBElement<TRss10Item>, Item, YarfrawException>{}