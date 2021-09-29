package app.routes.inverse;

import lib.router.Route;
import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.IOUtils;

public class CofactorInvRoute extends Route {
  public CofactorInvRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = IOUtils.inputMatrix(true);
    Matrix mInvers = m.invers();
    System.out.println("Hasil Matriks Invers : ");
    System.out.print(mInvers.toString());
  }
}
