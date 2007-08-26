package yarfraw.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import yarfraw.mapping.backward.impl.parser.ToChannelDOMParserAtomImpl;
import yarfraw.mapping.forward.impl.ToAtom10ChannelImpl;

public class Test{

  public static void main(String[] args) throws Exception {
    Document doc = XMLUtils.parseXml(new FileInputStream("atom10b.xml"), false, false);
    new ToChannelDOMParserAtomImpl().execute(doc);
  }

}