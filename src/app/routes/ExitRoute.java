package app.routes;

import app.router.ExitException;
import app.router.Route;

public class ExitRoute extends Route {
  public ExitRoute(String key) {
    super(key, "Keluar");
  }

  public void run() throws Exception {
    throw new ExitException();
  }
}
