package lib.router;

import java.util.ArrayList;
import java.util.List;

public class Route {
  protected String key;
  protected String label;
  protected List<Route> subroutes;

  public Route(String key, String label, List<Route> subroutes) {
    this.key = key;
    this.label = label;
    this.subroutes = subroutes;
  }

  public Route(String key, String label) {
    this(key, label, new ArrayList<Route>());
  }

  public String getKey() {
    return this.key;
  }

  public String getLabel() {
    return this.label;
  }

  public List<Route> getSubroutes() {
    return this.subroutes;
  }

  public boolean hasSubroutes() {
    return !this.subroutes.isEmpty();
  }

  public void run() throws Exception {
    /* STUB */
  }
}
