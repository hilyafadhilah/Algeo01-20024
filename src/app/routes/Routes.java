package app.routes;

import java.util.ArrayList;

import app.routes.determinant.DeterminantRoute;
import app.routes.inverse.InverseRoute;
import app.routes.system.SystemRoute;
import lib.router.Route;

public class Routes {
  public static ArrayList<Route> getRoutes() {
    ArrayList<Route> routes = new ArrayList<>();
    routes.add(new SystemRoute("1"));
    routes.add(new DeterminantRoute("2"));
    routes.add(new InverseRoute("3"));
    routes.add(new InterpolationRoute("4"));
    routes.add(new RegressionRoute("5"));
    routes.add(new ExitRoute("6"));
    return routes;
  }
}
