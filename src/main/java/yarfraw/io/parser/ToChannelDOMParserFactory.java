package yarfraw.io.parser;

import java.util.EnumSet;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.parser.CoreRssElementEnum;
import yarfraw.io.parser.ToChannelDOMParser;
import yarfraw.mapping.backward.impl.parser.ToChannelDOMParserAtomImpl;
import yarfraw.mapping.backward.impl.parser.ToChannelDOMParserRss10Impl;

public class ToChannelDOMParserFactory{
  private static final ToChannelDOMParserFactory _instance = new ToChannelDOMParserFactory();
  private ToChannelDOMParserFactory(){};
  public static ToChannelDOMParserFactory getInstance(){
    return _instance;
  }
  
  public ToChannelDOMParser createParser(FeedFormat format, EnumSet<CoreRssElementEnum> elementOfInterests){
    if(format == FeedFormat.ATOM10){
      return new ToChannelDOMParserAtomImpl(elementOfInterests);
    }else if(format == FeedFormat.RSS20){
      throw new IllegalArgumentException("nothing yet");
    }else if(format == FeedFormat.RSS10){
      return new ToChannelDOMParserRss10Impl(elementOfInterests);
    }else{
      throw new IllegalArgumentException("Unknow feed format: "+format);
    }
  }
  
  public ToChannelDOMParser createParser(FeedFormat format){
    return createParser(format, EnumSet.allOf(CoreRssElementEnum.class));
  }
}