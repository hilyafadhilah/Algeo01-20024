package app.router;

import java.util.List;

import app.utils.CollectionUtils;
import app.utils.InputUtils;
import app.utils.OutputUtils;

/**
 * Routing class specific to this application.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.3
 * @see Route
 * @since 2021-09-26
 */
public class Router {
  /**
   * List of routes.
   * 
   * @see Route
   */
  protected List<Route> routes;

  /**
   * Constructor
   * 
   * @param routes List of routes
   */
  public Router(List<Route> routes) {
    this.routes = routes;
  }

  /**
   * Run this <code>Router</code> with the assigned <code>routes</code>. It will
   * do an infinite loop until the user signals EOF or force exit, OR if the user
   * chooses a route that throws an <code>ExitException</code>.
   * 
   * @see ExitException
   */
  public void run() {
    boolean isRunning = true;

    while (isRunning) {
      String title = "Menu";
      List<Route> routes = this.routes;
      Route route = null;

      do {
        OutputUtils.printHeader(title);
        displayMenu(routes);

        try {
          String key = InputUtils.prompt("menu>");
          route = CollectionUtils.find(routes, r -> r.getKey().equals(key));

          if (route != null) {
            routes = route.getSubroutes();
            title = route.getLabel();
          } else {
            System.out.println("Pilihan tidak valid.");
          }
        } catch (Exception e) {
          isRunning = false;
        }
      } while (route != null && route.hasSubroutes());

      try {
        if (route != null) {
          route.run();
        }
      } catch (ExitException e) {
        isRunning = false;
      } catch (Exception e) {
        System.out.println();
        OutputUtils.printHeader("Error");
        System.out.println(e.getMessage());
        System.out.println();
      }
    }
  }

  /**
   * Displays list of <code>routes</code> with a predefined format.
   * 
   * @param routes The routes to be displayed
   */
  private void displayMenu(List<Route> routes) {
    for (Route route : routes) {
      System.out.printf("  %s: %s\n", route.getKey(), route.getLabel());
    }
    System.out.print("\n");
  }
}
