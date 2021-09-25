package lib.router;

import java.util.List;

import lib.utils.IOUtils;
import lib.utils.CollectionUtils;

public class Router {
  protected List<Route> routes;

  public Router(List<Route> routes) {
    this.routes = routes;
  }

  public void run() {
    boolean isRunning = true;

    while (isRunning) {
      String title = "Menu";
      List<Route> routes = this.routes;
      Route route = null;

      do {
        IOUtils.printHeader(title);
        displayMenu(routes);

        try {
          String key = IOUtils.prompt(">");
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
        System.out.println(e);
      }
    }
  }

  private void displayMenu(List<Route> routes) {
    for (Route route : routes) {
      System.out.printf("  %s: %s\n", route.getKey(), route.getLabel());
    }
    System.out.print("\n");
  }
}
