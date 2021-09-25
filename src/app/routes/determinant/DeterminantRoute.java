package app.routes.determinant;

import java.util.Arrays;

import lib.router.Route;

public class DeterminantRoute extends Route {
  public DeterminantRoute(String key) {
    super(key, "Determinan Matriks", Arrays.asList(new ReductionRoute("1"), new CofactorDetRoute("2")));
  }
}
