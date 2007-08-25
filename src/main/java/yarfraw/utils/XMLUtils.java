package yarfraw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
  public static XPath newXPath() {
    return XPATH_FACTORY.newXPath();
  }
  public static String getAttributeValue(Node element, QName name){
    return getAttributeValue(element, name.getLocalPart(), name.getNamespaceURI());
  }
  public static String getAttributeValue(Node element, String attributeLocalName){
    return getAttributeValue(element, attributeLocalName, null);
  }
  public static String getAttributeValue(Node element, String attributeLocalName, String namespaceUri){
    NamedNodeMap attr = element.getAttributes();
    if(attr == null){
      return null;
    }
    
    Node n = namespaceUri == null? attr.getNamedItem(attributeLocalName) :
        attr.getNamedItemNS(namespaceUri, attributeLocalName);
    if(n == null){
      return null;
    }else{
      return n.getNodeValue();
    }
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
   *  Returns a <code>Namespace</code> context, based on the passed association
   *  of prefixes and namespaces.
   *
   *  @param  namespaces  Namespace associations: prefixes are stored as keys,
   *                      namespace URIs as values. Note that more than one
   *                      prefix may be used for the same namespace. <em>The
   *                      returned context retains a reference to the passed
   *                      map<em>; change the map at your own risk.
   */
  public static NamespaceContext createNamespaceContext(
          final Map<String,String> namespaces) {
    return new NamespaceContext() {
      private Map<String,String> _namespaces = namespaces;

      public String getNamespaceURI(String prefix) {
        // FIXME - this method must handle prefixes defined by spec in addition
        //         to those defined by map
        return _namespaces.get(prefix);
      }

      public String getPrefix(String namespaceURI) {
        List<String> prefixes = getPrefixList(namespaceURI);
        return (prefixes.size() == 0) ? null : prefixes.get(0);
      }

      public Iterator<String> getPrefixes(String namespaceURI) {
        return getPrefixList(namespaceURI).iterator();
      }

      private List<String> getPrefixList(String namespaceURI) {
        List<String> prefixes = new ArrayList<String>();
        for (Map.Entry<String,String> entry : _namespaces.entrySet()) {
          if (entry.getValue().equals(namespaceURI)) {
            prefixes.add(entry.getKey());
          }
        }
        // FIXME - check spec'd prefixes if list is empty
        return prefixes;
      }
    };
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