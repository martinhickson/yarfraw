package yarfraw.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class Test{
  public static void main(String[] args) throws Exception {

    InputStream input = null;
    Set<String> set = null;
    try {
      input = new FileInputStream("prod_deatest_4.tab");
       set = new HashSet<String>(IOUtils.readLines(input));
    }finally{
      IOUtils.closeQuietly(input);
    }
    
    try {
      input = new FileInputStream("prod_deatest_42.tab");
      set.removeAll(IOUtils.readLines(input));
    }finally{
      IOUtils.closeQuietly(input);
    }
    for(String s : set){
      System.out.println(s);
    }
  }
  
  
}