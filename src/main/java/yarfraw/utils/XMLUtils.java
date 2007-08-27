package yarfraw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils{
  private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
  
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
  
  /**
   * Get a new {@link XPath} object.
   * @return
   */
  public static XPath newXPath() {
    return XPATH_FACTORY.newXPath();
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
   *  Returns an XPath instance configured to work with a DOM tree, using
   *  the passed context to resolve namespace references.
   */
  public static XPath newXPath(NamespaceContext nsCtx) {
    XPath xpath = XPATH_FACTORY.newXPath();
    xpath.setNamespaceContext(nsCtx);
    return xpath;
  }
  
  private XMLUtils(){}
  
  /**
   * Construct a {@link QName} object using a node's localName and NamespaceUri
   * @param n - a DOM node
   * @return - a new {@link QName} object that has input localName as it's localName and
   * input NamespaceUri as its NamespaceUri.
   */
  public static QName getQName(Node n){
    return new QName(CommonUtils.emptyIfNull(n.getNamespaceURI()), n.getLocalName());
  }
    
  
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
    DocumentBuilderFactory factory = 
      DocumentBuilderFactory.newInstance();
   factory.setValidating(validating);
   factory.setIgnoringComments(ignoringComments);
   factory.setNamespaceAware(true);
   return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml))); 
  }
  
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
  public static Document parseXml(InputStream stream, boolean validating, boolean ignoringComments) 
  throws SAXException, IOException, ParserConfigurationException{
    DocumentBuilderFactory factory = 
      DocumentBuilderFactory.newInstance();
   factory.setValidating(validating);
   factory.setIgnoringComments(ignoringComments);
   factory.setNamespaceAware(true);
   return factory.newDocumentBuilder().parse(new InputSource(stream));
   
  }
  
  /**
   *  Selects a single value using the specified XPath. 
   *  @throws XPathExpressionException 
   */
  public static String selectValue(Node context, String xpath) throws XPathExpressionException {
    return newXPath().evaluate(xpath, context);
  }
  /**
   *  Selects a single value using the specified XPath. 
   *  @throws XPathExpressionException 
   */
  public static String selectValue(Node context, String xpath, NamespaceContext nsContext) throws XPathExpressionException {
    return newXPath(nsContext).evaluate(xpath, context);
  }
  /**
   *  Selects any nodes that match the specified XPath. 
   * @throws XPathExpressionException  
   */
  public static List<Node> selectNodes(Node context, String xpath) throws XPathExpressionException {
    NodeList nodes = (NodeList)newXPath().evaluate(xpath, context, XPathConstants.NODESET);
    List<Node> result = new ArrayList<Node>();
    for (int i = 0 ; i < nodes.getLength() ; i++) {
      result.add(nodes.item(i));
    }
    return result;
  }
  /**
   *  Selects any nodes that match the specified XPath. 
   * @throws XPathExpressionException  
   */
  public static List<Node> selectNodes(Node context, String xpath, NamespaceContext nsContext) throws XPathExpressionException {
    NodeList nodes = (NodeList)newXPath(nsContext).evaluate(xpath, context, XPathConstants.NODESET);
    List<Node> result = new ArrayList<Node>();
    for (int i = 0 ; i < nodes.getLength() ; i++) {
      result.add(nodes.item(i));
    }
    return result;
  }
  
  
  /**
   * Depth first traversal
   * 
   * preProcess() function in NodeProcessor is called before visiting children
   * postProcess() function in NodeProcessor is called before visiting children
   * 
   * @param node - the root of the tree for traversal
   * @param processor - process function to called when visit the node
   */
    public static void traverseTreeDepthFirst(Node node, NodeProcessor processor){
      short filter = processor.preProcess(node);
      if(filter != NodeFilter.FILTER_REJECT){
        Node child = node.getFirstChild();
        while(child != null){
          traverseTreeDepthFirst(child, processor);
          child = child.getNextSibling();
        }
      }
      processor.postProcess(node);
    }
}