package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;

public class InverseMethodRoute extends Route {
  public InverseMethodRoute(String key) {
    super(key, "Metode Matriks Balikan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    // Matrix m dipecah dalam bentuk AX = B
    // Yakni mA = A, mB = B, X = solusi
    Matrix mA = new Matrix(m.getNRows(), m.getNCols() - 1);
    Matrix mB = new Matrix(m.getNRows(), 1);

    for (int a = 0; a < m.getNRows(); a++) {
      mB.set(a, 0, m.get(a, m.getNCols() - 1));
      for (int b = 0; b < mA.getNCols(); b++) {
        mA.set(a, b, m.get(a, b));
      }
    }
    Matrix mAInvers = mA.invers();
    Matrix mResult = mAInvers.multiplyMatrix(mB);
    System.out.println("Hasil SPL dengan Metode Invers : ");
    System.out.println();
    for (int i = 0; i < mResult.getNRows(); i++) {
      System.out.println("x_" + i + " = " + mResult.get(i, 0));
    }
    System.out.println();
  }
}