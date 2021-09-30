package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;
import lib.system.LinearSystem;

public class InverseMethodRoute extends Route {
  public InverseMethodRoute(String key) {
    super(key, "Metode Matriks Balikan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    // Matrix m dipecah dalam bentuk AX = B
    // Yakni mA = A, mB = B, X = solusi
    Matrix mResult = LinearSystem.InverseMethod(m);
    System.out.println("Hasil SPL dengan Metode Invers : ");
    System.out.println();
    for (int i = 0; i < mResult.getNRows(); i++) {
      System.out.println("x_" + i + " = " + mResult.get(i, 0));
    }
    System.out.println();
  }
}