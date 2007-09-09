package yarfraw.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import yarfraw.core.datamodel.YarfrawException;

public class XMLUtils{
  private XMLUtils(){}
  /**
   * Parses an xml string to a Document object.
   * 
   * @param xml - xml to be parsed
   * @param validating - see {@link DocumentBuilderFactory}
   * @param ignoringComments - see {@link DocumentBuilderFactory}
   * @return a W3 DOM object representation of the input string
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public static Document parseXml(String xml, boolean validating, boolean ignoringComments) 
  throws SAXException, IOException, ParserConfigurationException{
   return parseXml(new InputSource(new StringReader(xml)), validating, ignoringComments); 
  }
  
  /**
   * Parses an xml stream to a Document object.
   * 
   * @param stream - xml to be parsed
   * @param validating - see {@link DocumentBuilderFactory}
   * @param ignoringComments - see {@link DocumentBuilderFactory}
   * @return a W3 DOM object representation of the input string
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public static Document parseXml(InputStream stream, boolean validating, boolean ignoringComments) 
  throws SAXException, IOException, ParserConfigurationException{
   return parseXml(new InputSource(stream), validating, ignoringComments);
  }
  /**
   * Parses xml to a Document object.
   * 
   * @param source - xml source to be parsed
   * @param validating - see {@link DocumentBuilderFactory}
   * @param ignoringComments - see {@link DocumentBuilderFactory}
   * @return a W3 DOM object representation of the input string
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public static Document parseXml(InputSource source, boolean validating, boolean ignoringComments)
  throws SAXException, IOException, ParserConfigurationException{
    DocumentBuilderFactory factory = 
      DocumentBuilderFactory.newInstance();
   factory.setValidating(validating);
   factory.setIgnoringComments(ignoringComments);
   factory.setNamespaceAware(true);
   return factory.newDocumentBuilder().parse(source);
  }
  
  /**
   * Search through the input element list and return the first element that matches
   * both input the namespaceURI and the localName.
   * 
   * @param namespaceURI - namespaceURI of the element to be search for
   * @param localName - localName of the element
   * @return - null if no matching element is found,
   * the matching element otherwise.
   */
  public static Element getElementByNS(List<Element> elements, String namespaceURI, String localName){
    if(CollectionUtils.isEmpty(elements)){
      return null;
    }
    for(Element e : elements){
      if(ObjectUtils.equals(localName, e.getLocalName()) && 
              ObjectUtils.equals(namespaceURI, emptyIfNull(e.getNamespaceURI()))){
        return e;
      }
    }    
    return null;
  }

  /**
   * Search through the input element list and return the FIRST element that matches
   * the localName.
   * 
   * @param localName - localName of the element
   * @return - null if no matching element is found,
   * the matching element otherwise.
   */
  public static Element getElementByLocalName(List<Element> elements, String localName){
    if(CollectionUtils.isEmpty(elements)){
      return null;
    }
    for(Element e : elements){
      if(ObjectUtils.equals(localName, e.getLocalName())){
        return e;
      }
    }    
    return null;
  }
  
  public static boolean same(QName qn1, QName qn2){
    return ObjectUtils.equals(qn1, qn2);
  }

  
  /**
   * Return empty string if input is null.
   * @param str
   * @return
   */
  public static String emptyIfNull(String str){
    return str == null?StringUtils.EMPTY:str;
  }

  
  /**
   * Get the attribute value of the attribute that matches the specified {@link QName}.
   * @param element - the element to be searched on.
   * @param name - 
   * @return - the value of the specified attribute, null if the attribute is not found.
   */
  public static String getAttributeValue(Node element, QName name){
    return getAttributeValue(element, name.getLocalPart(), name.getNamespaceURI());
  }
  
  /**
   * Get the attribute value of the attribute that matches the specified localName.
   * @param element - the element to be searched on.
   * @param name - 
   * @return - the value of the specified attribute, null if the attribute is not found.
   */
  public static String getAttributeValue(Node element, String attributeLocalName){
    return getAttributeValue(element, attributeLocalName, null);
  }
  /**
   * Get the attribute value of the attribute that matches the specified localName and NamespaceUri
   * @param element - the element to be searched on.
   * @param name - 
   * @return - the value of the specified attribute, null if the attribute is not found.
   */
  public static String getAttributeValue(Node element, String attributeLocalName, String namespaceUri){
    NamedNodeMap attr = element.getAttributes();
    if(attr == null){
      return null;
    }
    for(int i=0; i<attr.getLength(); i++){
      Node a = attr.item(i);
      boolean sameUri = namespaceUri == null? true: namespaceUri.equals(a.getNamespaceURI());
      if(sameUri && attributeLocalName.equals(a.getLocalName())){
        return a.getNodeValue();
      }
    }
    return null;
  }
     
  /**
   * Construct a {@link QName} object using a node's localName and NamespaceUri
   * @param n - a DOM node
   * @return - a new {@link QName} object that has input localName as it's localName and
   * input NamespaceUri as its NamespaceUri.
   */
  public static QName getQName(Node n){
    return new QName(emptyIfNull(n.getNamespaceURI()), n.getLocalName());
  }
  
  
  /**
   * Get a list of Children nodes of the input parent using a localName
   * @param parent - the parent node
   * @param localName - local name of the children to be searched for
   * @return - all children nodes that matches the input localName. 
   */
  public static List<Node> getChildrenNodesByName(Node parent, String localName){
    return getChildrenByName(parent, localName, false);
  }
  
  /**
   * Get a single Children node of the input parent using a localName
   * @param parent - the parent node
   * @param localName - local name of the children to be searched for
   * @return - the FIRST children node that matches the input localName. 
   */
  public static Node getChildrenNodeByName(Node parent, String localName){
    List<Node> result = getChildrenByName(parent, localName, true);
    return result.size() > 0? result.get(0) : null;
  }
  

  public static String transformWithXsl(String xslt, String xml) throws YarfrawException{
    Source source = new StreamSource(xml);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Result res = new StreamResult(out);  
    TransformerFactory transFact = TransformerFactory.newInstance();
    Transformer trans;
    
    try {
      trans = transFact.newTransformer(new StreamSource(xslt));
      trans.transform(source, res);
    } catch (TransformerConfigurationException e) {
      throw new YarfrawException("Transformer config exception", e);
    } catch (TransformerException e) {
      throw new YarfrawException("Transform exception", e);
    }
    
    return out.toString();
  }
  
  private static List<Node> getChildrenByName(Node parent, String localName, boolean single){
    List<Node> nodes = new ArrayList<Node>();
    NodeList list = parent.getChildNodes();
    for(int i =0; i< list.getLength(); i++){
      if(localName.equals(list.item(i).getLocalName())){
        nodes.add(list.item(i));
        if(single){
          return nodes;
        }
      }
    }
    return nodes;
  }

}