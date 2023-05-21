package Final;

// import javafx packages
import javafx.scene.Node;

/**
 * Interface for elements that contribute to the UI. 
 */
public interface UIElement {
  /**
   * Renders some object to return a container with all UI elements. 
   * 
   * @return Node containing the UI elements
   * @throws Exception if there is an error initializing the UI element
   */
  public Node render() throws Exception;
}
