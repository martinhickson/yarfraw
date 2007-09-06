package yarfraw.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import yarfraw.core.datamodel.FeedFormat;

public class JAXBUtils {

  private static JAXBContext RSS20_CONTEXT = null;
  private static JAXBContext RSS10_CONTEXT = null;
  private static JAXBContext ATOM10_CONTEXT = null;
  
  /**
   * Gets the {@link JAXBContext} based on the input {@link FeedFormat}
   * @param format
   * @return
   * @throws JAXBException
   */
  public static synchronized JAXBContext getContext(FeedFormat format) throws JAXBException{
    
    if(format == FeedFormat.RSS10){
      if(RSS10_CONTEXT == null){
        RSS10_CONTEXT = JAXBContext.newInstance(CommonUtils.RSS10_JAXB_CONTEXT);
      }
      return RSS10_CONTEXT;
    }
    if(format == FeedFormat.RSS20){
      if(RSS20_CONTEXT == null){
        RSS20_CONTEXT = JAXBContext.newInstance(CommonUtils.RSS20_JAXB_CONTEXT);
      }
      return RSS20_CONTEXT;
    }
    
    if(format == FeedFormat.ATOM10){
      if(ATOM10_CONTEXT == null){
        ATOM10_CONTEXT = JAXBContext.newInstance(CommonUtils.ATOM10_JAXB_CONTEXT);
      }
      return ATOM10_CONTEXT;
    }
    
    throw new UnsupportedOperationException("Unsupported format: "+ format);
    
  }
}