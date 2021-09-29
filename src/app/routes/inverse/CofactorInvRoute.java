package app.routes.inverse;

import lib.router.Route;
import lib.matrix.Matrix;
import lib.utils.InputUtils;

public class CofactorInvRoute extends Route {
  public CofactorInvRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    Matrix mInvers = m.invers();
    System.out.println("Hasil Matriks Invers : ");
    System.out.println();
    System.out.print(mInvers.toString());
    System.out.println();
  }
}
