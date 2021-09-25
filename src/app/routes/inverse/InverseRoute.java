package app.routes.inverse;

import java.util.Arrays;

import lib.router.Route;

public class InverseRoute extends Route {
  public InverseRoute(String key) {
    super(key, "Matriks balikan", Arrays.asList(new GaussJordanInvRoute("1"), new CofactorInvRoute("2")));
  }
}
