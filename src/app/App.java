package app;

import app.router.Router;
import app.routes.Routes;
import app.utils.InputUtils;

public class App {
  public static void main(String[] args) throws Exception {
    InputUtils.init();
    Router router = new Router(Routes.getRoutes());
    router.run();
    InputUtils.close();
  }
}
