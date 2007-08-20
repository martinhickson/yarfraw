package yarfraw.mapping.forward;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.mapping.Functor;

public interface ToAtom10Channel extends Functor<JAXBElement<TRss10Channel>, Channel, YarfrawException>{}