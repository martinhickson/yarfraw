package yarfraw.mapping.backward.impl;

import java.util.EnumSet;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.mapping.CoreRssElementEnum;
import yarfraw.mapping.backward.ToChannelDOM;

public class ToChannelDOMSimplifiedImpl implements ToChannelDOM, NodeFilter{
  protected EnumSet<CoreRssElementEnum> _elementsOfInterest;
  protected FeedFormat _format;
  public ToChannelDOMSimplifiedImpl() {
    super();
    _elementsOfInterest = EnumSet.allOf(CoreRssElementEnum.class);
  }
  public ToChannelDOMSimplifiedImpl(
      EnumSet<CoreRssElementEnum> elementsOfInterest) {
    super();
    _elementsOfInterest = elementsOfInterest;
  }

  public EnumSet<CoreRssElementEnum> getElementsOfInterest() {
    return _elementsOfInterest;
  }

  public void setElementsOfInterest(EnumSet<CoreRssElementEnum> elementsOfInterest) {
    _elementsOfInterest = elementsOfInterest;
  }
  
  public Channel execute(Document doc) throws YarfrawException {
    DOMImplementation domimpl = doc.getImplementation();
    if (domimpl.hasFeature("Traversal", "2.0")) {

      Node root = doc.getDocumentElement();
      int whattoshow = NodeFilter.SHOW_ELEMENT;

      boolean expandreferences = false;

      DocumentTraversal traversal = (DocumentTraversal)doc;
      TreeWalker walker = traversal.createTreeWalker(root, 
                                                     whattoshow, 
                                                     this, //node filter 
                                                     expandreferences);
      Node n = walker.nextNode();
      while(n != null){
//        System.out.println(current);
//        System.out.println(current.getTextContent());
        System.out.println(n.getPrefix()+"  "+n.getNodeName()+"  "+n.getBaseURI()+" "+n.getLocalName()+"  "+n.getNamespaceURI());
        
        n = walker.nextNode();
        
      }

   } else {
      throw new YarfrawException("The input DOM implementation does not support TreeWalker traversal");
   }
    return null;
  }
  
  public short acceptNode(Node n) {
    for(CoreRssElementEnum e : _elementsOfInterest){
      QName expected = e.getName(_format);
      if(StringUtils.equals(emptyIfNull(n.getNamespaceURI()), expected.getNamespaceURI()) 
          && StringUtils.equals(n.getLocalName(), expected.getLocalPart())){
        return NodeFilter.FILTER_ACCEPT;
      }
    }
    return NodeFilter.FILTER_REJECT;
  }
  
  private static String emptyIfNull(String str){
    return str == null?StringUtils.EMPTY:str;
  }
  public void setFeedFormat(FeedFormat format) {
    _format = format; 
  }
  
}

