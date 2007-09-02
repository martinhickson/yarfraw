package yarfraw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils{
  
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

}