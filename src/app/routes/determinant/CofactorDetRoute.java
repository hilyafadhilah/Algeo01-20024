package app.routes.determinant;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;

public class CofactorDetRoute extends Route {
  public CofactorDetRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    double det = Determinant.cofactorMethod(m);
    System.out.println("\nDeterminan matriks = " + det);
  }
}
