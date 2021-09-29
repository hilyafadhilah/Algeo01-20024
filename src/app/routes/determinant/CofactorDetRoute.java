package app.routes.determinant;

import lib.router.Route;
import lib.utils.IOUtils;
import lib.matrix.Matrix;
import lib.matrix.Determinant;

public class CofactorDetRoute extends Route {
  public CofactorDetRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = IOUtils.inputMatrix();
    double det = Determinant.cofactorMethod(m);
    System.out.println("Determinan matriks = " + det);
  }
}
