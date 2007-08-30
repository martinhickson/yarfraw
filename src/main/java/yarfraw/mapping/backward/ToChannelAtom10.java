package yarfraw.mapping.backward;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.mapping.Functor;

public interface  ToChannelAtom10 extends Functor<ChannelFeed, FeedType, YarfrawException>{}