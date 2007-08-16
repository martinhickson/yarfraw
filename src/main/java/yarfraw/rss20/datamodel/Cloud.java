package yarfraw.rss20.datamodel;

import java.math.BigInteger;

import javax.xml.bind.JAXBElement;

import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TCloud;
import yarfraw.generated.rss20.elements.TCloudProtocol;
import yarfraw.rss20.utils.Utils;

/**
 * It specifies a web service that supports the rssCloud interface which can be implemented in HTTP-POST, XML-RPC or SOAP 1.1.
 * Its purpose is to allow processes to register with a cloud to be notified of updates to the channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
 * <cloud domain="rpc.sys.com" port="80" path="/RPC2" registerProcedure="myCloud.rssPleaseNotify" protocol="xml-rpc" />
 * In this example, to request notification on the channel it appears in, you would send an XML-RPC message to rpc.sys.com on port 80, with a path of /RPC2. The procedure to call is myCloud.rssPleaseNotify.
 * <br/>
 * A full explanation of this element and the rssCloud interface is here.
 * <br/>
 * http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface
 * <p/>
 * Example: &lt;cloud domain="rpc.sys.com" port="80" path="/RPC2" registerProcedure="pingMe" protocol="soap"/>
 * @author jliang
 *
 */
public class Cloud extends AbstractBaseObject{
  private String _domain;
  private Integer _port;
  private String _path;
  private String _registerProcedure;
  private String _protocol;
  public Cloud(){}
  public static Cloud create(){
    return new Cloud();
  }
  public Cloud(String domain, Integer port, String path,
          String registerProcedure, String protocol) {
    super();
    _domain = domain;
    _port = port;
    _path = path;
    _registerProcedure = registerProcedure;
    _protocol = protocol;
  }
  public String getDomain() {
    return _domain;
  }
  public Cloud setDomain(String domain) {
    _domain = domain;
    return this;
  }
  public int getPort() {
    return _port;
  }
  public Cloud setPort(int port) {
    _port = port;
    return this;
  }
  public String getPath() {
    return _path;
  }
  public Cloud setPath(String path) {
    _path = path;
    return this;
  }
  public String getRegisterProcedure() {
    return _registerProcedure;
  }
  public Cloud setRegisterProcedure(String registerProcedure) {
    _registerProcedure = registerProcedure;
    return this;
  }
  public String getProtocol() {
    return _protocol;
  }
  public Cloud setProtocol(String protocol) {
    _protocol = protocol;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    Utils.validateNotNull("Cloud: All fields in the cloud object should be not null", _domain, _path, _port, _protocol, _registerProcedure);
    if(!_protocol.equals("xml-rpc") && !_protocol.equals("http-post") && !_protocol.equals("soap")){
      throw new ValidationException("Cloud: Protocol should be one of the following: xml-rpc, soap, http-post");
    }
    
    try {
      if(_port != null && (_port.intValue() < 0 || _port.intValue() > 65535)){
        throw new ValidationException("Cloud: Invalid port number");
      }
    }
    catch (Exception e) {
      throw new ValidationException(e.getMessage());
    }
  }
  
  private TCloud toTCloud(){
    TCloud ret = new TCloud();
    ret.setDomain(_domain);
    ret.setPath(_path);
    ret.setPort(new BigInteger(String.valueOf(_port)));
    ret.setProtocol(TCloudProtocol.fromValue(_protocol));
    ret.setRegisterProcedure(_registerProcedure);
    return ret;
  }
  
  public JAXBElement<TCloud> toTCloudJAXB(){
    return new ObjectFactory().createTRssChannelCloud(toTCloud());
  }
}