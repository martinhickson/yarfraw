package yarfraw.mapping.backward;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom03.elements.FeedType;
import yarfraw.mapping.Functor;

public interface  ToChannelAtom03 extends Functor<ChannelFeed, FeedType, YarfrawException>{}