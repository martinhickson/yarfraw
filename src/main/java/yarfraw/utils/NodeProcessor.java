package yarfraw.utils;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

/**
 * Process function of a node
 * 
 * The tree traversal in TreeUtils will call this function
 * to process a node when it visit a node
 * 
 * (think function pointer)
 * 
 * @author jliang
 *
 */
public interface NodeProcessor{
  /**
   * Process node before visiting children
   * @param node
   * @return takes on the value of {@link NodeFilter}.FILTER_* <br/>
   * FILTER_ACCEPT = process children nodes<br/>
   * FILTER_SKIP = no effect, since the node is always passed to preProcessed method<br/>
   * FILTER_REJECT = skip children nodes<br/>
   */
  public short preProcess(Node node);
  public void postProcess(Node node);
}