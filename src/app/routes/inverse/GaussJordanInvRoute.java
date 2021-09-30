package app.routes.inverse;

import lib.router.Route;
import lib.matrix.Inverse;
import lib.matrix.Matrix;
import lib.utils.InputUtils;

public class GaussJordanInvRoute extends Route {
  public GaussJordanInvRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    Matrix mInvers = Inverse.gaussMethod(m);
    System.out.println("Hasil Matriks Invers : ");
    System.out.println();
    System.out.print(mInvers.toString());
    System.out.println();
  }
}