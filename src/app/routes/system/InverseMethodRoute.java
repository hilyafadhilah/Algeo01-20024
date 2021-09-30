package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;
import lib.system.LinearSystem;
import lib.system.Solution;

public class InverseMethodRoute extends Route {
  public InverseMethodRoute(String key) {
    super(key, "Metode Matriks Balikan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    // Matrix m dipecah dalam bentuk AX = B
    // Yakni mA = A, mB = B, X = solusi
    Solution[] result = LinearSystem.inverseMethod(m);
    System.out.println("Hasil SPL dengan Metode Invers : ");
    System.out.println();
    for (int i = 0; i < result.length; i++) {
      System.out.println("x_" + i + " = " + result[i]);
    }
    System.out.println();
  }
}