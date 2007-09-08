package yarfraw.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import yarfraw.core.datamodel.FeedFormat;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CommonConstants{
  private CommonConstants(){}
  public static final int MIN_PER_DAY = 60*24;
  public static final int MIN_PER_WEEK = MIN_PER_DAY*7;
  public static final int MIN_PER_MONTH = MIN_PER_DAY*30;
  public static final int MIN_PER_YEAR = MIN_PER_DAY*365;
  public static final String PREFIX_MAPPER_PROPERTY_NAME = "com.sun.xml.bind.namespacePrefixMapper";
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
    }else{
      throw new UnsupportedOperationException("Unknown format: "+format);
    }
  }
  
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
  private static Map<String, String> _extensionPrefixMap = null;
  private static Map<String, String> _rss10PrefixMap = null;
  private static Map<String, String> _rss20PrefixMap = null;
  private static Map<String, String> _atom10PrefixMap = null;
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
    if(_atom10PrefixMap == null){
      _atom10PrefixMap = new HashMap<String, String>();
      _atom10PrefixMap.put("http://www.w3.org/2005/Atom", ""); //atom 1.0 elements go to default
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
      _extensionPrefixMap.put("http://www.blogger.com/atom/ns#", null);
      _extensionPrefixMap.put("http://purl.org/atom-blog/ns#", "draft");
      _extensionPrefixMap = Collections.unmodifiableMap(_extensionPrefixMap);
    }
    return _extensionPrefixMap;
  }
}