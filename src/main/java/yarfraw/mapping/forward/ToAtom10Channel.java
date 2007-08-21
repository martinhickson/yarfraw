package yarfraw.mapping.forward;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.mapping.Functor;

public interface ToAtom10Channel extends Functor<JAXBElement<FeedType>, Channel, YarfrawException>{}