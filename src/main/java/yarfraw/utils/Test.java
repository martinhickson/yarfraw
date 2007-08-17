package yarfraw.rss20.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import yarfraw.generated.rss10.elements.RDF;

public class Test{
  public static void main(String[] args) throws Exception {
    Unmarshaller u;
    InputStream input = null;
    try {
      input = new FileInputStream("rdf2.xml");
      u = JAXBContext.newInstance(Utils.RSS10_JAXB_CONTEXT).createUnmarshaller();
      RDF rdf = (RDF)u.unmarshal(input);
      
      for(Object o : rdf.getChannelOrItemOrTextinput()){
        System.out.println(ToStringBuilder.reflectionToString(o, ToStringStyle.MULTI_LINE_STYLE));
      }
      
    }finally{
      IOUtils.closeQuietly(input);
    }
  }
  
  
}