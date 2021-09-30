package app.routes.inverse;

import java.util.Arrays;

import app.router.Route;

public class InverseRoute extends Route {
  public InverseRoute(String key) {
    super(key, "Matriks Balikan", Arrays.asList(new GaussJordanInvRoute("1"), new CofactorInvRoute("2")));
  }
}
