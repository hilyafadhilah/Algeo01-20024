package app.router;

import java.util.ArrayList;
import java.util.List;

/**
 * Route "abstract" stub class that doesn't do anything when run. Extend the
 * <code>run()</code> method specifically to achieve true routing when used with
 * the <code>Router</code>.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @see Router
 * @since 2021-09-26
 */
public class Route {
  /**
   * <code>Route</code> unique key.
   */
  protected String key;

  /**
   * <code>Route</code> label for display purpose.
   */
  protected String label;

  /**
   * Subroutes of this <code>Route</code>.
   */
  protected List<Route> subroutes;

  /**
   * Constructor with subroutes.
   * 
   * @param key       <code>Route</code> unique key.
   * @param label     <code>Route</code> label for display purpose.
   * @param subroutes Subroutes of this <code>Route</code>.
   */
  public Route(String key, String label, List<Route> subroutes) {
    this.key = key;
    this.label = label;
    this.subroutes = subroutes;
  }

  /**
   * Constructor without subroutes.
   * 
   * @param key   <code>Route</code> unique key.
   * @param label <code>Route</code> label for display purpose.
   */
  public Route(String key, String label) {
    this(key, label, new ArrayList<Route>());
  }

  /**
   * Getter for <code>key</code>.
   * 
   * @return <code>key</code>
   * @see #key
   */
  public String getKey() {
    return this.key;
  }

  /**
   * Getter for <code>label</code>.
   * 
   * @return <code>label</code>
   * @see #label
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * Getter for <code>subroutes</code>.
   * 
   * @return <code>subroutes</code>
   * @see #subroutes
   */
  public List<Route> getSubroutes() {
    return this.subroutes;
  }

  /**
   * Check if this <code>Route</code> has <code>subroutes</code>.
   * 
   * @return <code>true</code> if it has
   */
  public boolean hasSubroutes() {
    return !this.subroutes.isEmpty();
  }

  /**
   * The main function to be run by the <code>Router</code>.
   * 
   * @throws Exception     If it fails
   * @throws ExitException If it signals exit peacefully
   * @see Router#run()
   */
  public void run() throws Exception {
    /* STUB */
  }
}
