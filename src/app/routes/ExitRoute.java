package app.routes;

import lib.router.ExitException;
import lib.router.Route;

public class ExitRoute extends Route {
  public ExitRoute(String key) {
    super(key, "Keluar");
  }

  public void run() throws Exception {
    throw new ExitException();
  }
}
