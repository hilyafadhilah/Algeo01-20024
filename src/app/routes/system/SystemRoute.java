package app.routes.system;

import java.util.Arrays;

import lib.router.Route;

public class SystemRoute extends Route {
  public SystemRoute(String key) {
    super(key, "Sistem Persamaan Linier", Arrays.asList(new GaussRoute("1"), new GaussJordanRoute("2"),
        new InverseMethodRoute("3"), new CramerRoute("4")));
  }
}
