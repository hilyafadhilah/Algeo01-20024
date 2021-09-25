package app;

import app.routes.Routes;
import lib.router.Router;
import lib.utils.IOUtils;

public class App {
  public static void main(String[] args) throws Exception {
    IOUtils.init();
    Router router = new Router(Routes.getRoutes());
    router.run();
    IOUtils.close();
  }
}
