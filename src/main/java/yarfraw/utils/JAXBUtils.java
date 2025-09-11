package yarfraw.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

import yarfraw.core.datamodel.FeedFormat;

public class JAXBUtils {
  public static final String PREFIX_MAPPER_PROPERTY_NAME = "org.glassfish.jaxb.namespacePrefixMapper";
  private static JAXBContext RSS20_CONTEXT = null;
  private static JAXBContext RSS10_CONTEXT = null;
  private static JAXBContext ATOM10_CONTEXT = null;
  private static JAXBContext ATOM03_CONTEXT = null;
  
  
  private static Map<String, String> _extensionPrefixMap = null;
  private static Map<String, String> _rss10PrefixMap = null;
  private static Map<String, String> _rss20PrefixMap = null;
  private static Map<String, String> _atom10PrefixMap = null;
  private static Map<String, String> _atom03PrefixMap = null;
  
  /**
   * Gets the namespace prefix map for the marshaller.
   * @param format a {@link FeedFormat}
   * @return a {@link NamespacePrefixMapper} object for the {@link Marshaller}
   */
  public static NamespacePrefixMapper  getNamespacePrefixMapper(FeedFormat format) {
    if(format == FeedFormat.ATOM10){
      return ATOM10_PREFIX_MAPPER;
    }else if(format == FeedFormat.RSS10){
      return RSS10_PREFIX_MAPPER;
    }else if(format == FeedFormat.RSS20){
      return RSS20_PREFIX_MAPPER;
    }else if(format == FeedFormat.ATOM03){
      return ATOM03_PREFIX_MAPPER;
    }else{
      throw new UnsupportedOperationException("Unknown format: "+format);
    }
  }

  private static NamespacePrefixMapper ATOM03_PREFIX_MAPPER = new NamespacePrefixMapper(){

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
      Map<String, String> prefixMap = getAtom03PrefixMap();
      return prefixMap.get(namespaceUri);
    }
    
  };
  
  private static NamespacePrefixMapper ATOM10_PREFIX_MAPPER = new NamespacePrefixMapper(){

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
      Map<String, String> prefixMap = getAtom10PrefixMap();
      return prefixMap.get(namespaceUri);
    }
    
  };

  private static NamespacePrefixMapper RSS10_PREFIX_MAPPER = new NamespacePrefixMapper(){

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
      Map<String, String> prefixMap = getRss10PrefixMap();
      return prefixMap.get(namespaceUri);
    }
    
  };

  private static NamespacePrefixMapper RSS20_PREFIX_MAPPER = new NamespacePrefixMapper(){

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
      Map<String, String> prefixMap = getRss20PrefixMap();
      return prefixMap.get(namespaceUri);
    }
    
  };

  private synchronized static Map<String, String> getRss10PrefixMap(){
    if(_rss10PrefixMap == null){
      _rss10PrefixMap = new HashMap<String, String>();
      _rss10PrefixMap.put("http://purl.org/rss/1.0/", ""); //rss 1.0 elements go to default
      _rss10PrefixMap.putAll(getExtensionPrefixMap());
      _rss10PrefixMap = Collections.unmodifiableMap(_rss10PrefixMap);
    }
    return _rss10PrefixMap;
  }
  
  private synchronized static Map<String, String> getRss20PrefixMap(){
    if(_rss20PrefixMap == null){
      _rss20PrefixMap = new HashMap<String, String>();
      _rss20PrefixMap.putAll(getExtensionPrefixMap());
      _rss20PrefixMap = Collections.unmodifiableMap(_rss20PrefixMap);
    }
    return _rss20PrefixMap;
  }

  private synchronized static Map<String, String> getAtom10PrefixMap(){
    if(_atom03PrefixMap == null){
      _atom03PrefixMap = new HashMap<String, String>();
      _atom03PrefixMap.put("http://www.w3.org/2005/Atom", ""); //atom 1.0 elements go to default
      _atom03PrefixMap.putAll(getExtensionPrefixMap());
      _atom03PrefixMap = Collections.unmodifiableMap(_atom03PrefixMap);
    }
    return _atom03PrefixMap;
  }
  
  private synchronized static Map<String, String> getAtom03PrefixMap(){
    if(_atom10PrefixMap == null){
      _atom10PrefixMap = new HashMap<String, String>();
      _atom10PrefixMap.put("http://purl.org/atom/ns#", ""); //atom 0.3 elements go to default
      _atom10PrefixMap.putAll(getExtensionPrefixMap());
      _atom10PrefixMap = Collections.unmodifiableMap(_atom10PrefixMap);
    }
    return _atom10PrefixMap;
  }
  
  private synchronized static Map<String, String> getExtensionPrefixMap(){
    if(_extensionPrefixMap == null){
      _extensionPrefixMap = new HashMap<String, String>();
      _extensionPrefixMap.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
      _extensionPrefixMap.put("http://www.w3.org/XML/1998/namespace", "xml");
      _extensionPrefixMap.put("http://base.google.com/ns/1.0", "g" );//google base
      _extensionPrefixMap.put("http://www.itunes.com/dtds/podcast-1.0.dtd", "itunes");
      _extensionPrefixMap.put("http://tools.search.yahoo.com/mrss/", "mrss");
      _extensionPrefixMap.put("http://wellformedweb.org/CommentAPI/", "wfw");
      _extensionPrefixMap.put("http://www.georss.org/georss/10", "georss");
      _extensionPrefixMap.put("http://purl.org/dc/elements/1.1/", "dc");
      _extensionPrefixMap.put("http://purl.org/rss/1.0/modules/syndication/", "sy");
      _extensionPrefixMap.put("http://webns.net/mvcb/", "admin");
      _extensionPrefixMap.put("http://rssnamespace.org/feedburner/ext/1.0", "feedburner");
      _extensionPrefixMap.put("http://purl.org/rss/1.0/modules/slash/", "slash");
      _extensionPrefixMap.put("http://www.blogger.com/atom/ns#", "blogger");
      _extensionPrefixMap.put("http://purl.org/atom-blog/ns#", "draft");
      _extensionPrefixMap.put("http://www.w3.org/1999/xhtml", "xhtml");
      _extensionPrefixMap = Collections.unmodifiableMap(_extensionPrefixMap);
    }
    return _extensionPrefixMap;
  }
  
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
    
    if(format == FeedFormat.ATOM03){
      if(ATOM03_CONTEXT == null){
        ATOM03_CONTEXT = JAXBContext.newInstance(CommonUtils.ATOM03_JAXB_CONTEXT);
      }
      return ATOM03_CONTEXT;
    }
    throw new UnsupportedOperationException("Unsupported format: "+ format);
    
  }
}