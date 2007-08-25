package yarfraw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
}