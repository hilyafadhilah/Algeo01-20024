package app;

import app.routes.Routes;
import lib.router.Router;
import lib.utils.InputUtils;

public class App {
  public static void main(String[] args) throws Exception {
    InputUtils.init();
    Router router = new Router(Routes.getRoutes());
    router.run();
    InputUtils.close();
  }
}
