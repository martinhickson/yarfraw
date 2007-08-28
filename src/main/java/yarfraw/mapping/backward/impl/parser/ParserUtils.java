package yarfraw.mapping.backward.impl.parser;

import static yarfraw.io.parser.AttributesQName.ATOM10_ENTRY_SRC;
import static yarfraw.io.parser.AttributesQName.ATOM10_ENTRY_TYPE;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.AtomTextAttributes.TextType;
import yarfraw.utils.DOMSerializer;
import yarfraw.utils.XMLUtils;

class ParserUtils{
  private ParserUtils(){}
  private static final DOMSerializer DOM_SERIALIZER = new DOMSerializer();
  private static final Log LOG = LogFactory.getLog(ParserUtils.class);
  public static void setItemContent(Node encoded, Item item){
    if(encoded != null){
      Content content = new Content();
      String type = XMLUtils.getAttributeValue(encoded, ATOM10_ENTRY_TYPE.getLocalPart());
      content.setSrc(XMLUtils.getAttributeValue(encoded, ATOM10_ENTRY_SRC.getLocalPart()));
      content.setType(type == null ? TextType.text: TextType.valueOf(type));
      if(content.getType() == TextType.xhtml){
        StringWriter writer = new StringWriter();
        try {
          DOM_SERIALIZER.serializeNode(encoded.getFirstChild(), writer, StringUtils.EMPTY);
        } catch (IOException e) {
          LOG.warn("The content of the <content> element should be xhtml, but unable");
        }
        content.addContentText(writer.toString());
      }else{
        content.addContentText(encoded.getTextContent());
      }
      item.setContent(content);
    }
  }
  
  
  public static String getStringContent(Node parent, String childLocalName){
    Node n = XMLUtils.getChildrenNodeByName(parent, childLocalName);
    if(n != null){
      return n.getTextContent();
    }
    return null;
  }
}