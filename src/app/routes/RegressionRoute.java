package app.routes;

import app.router.Route;
import lib.matrix.Matrix;
import lib.system.Solution;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.system.LinearSystem;

public class RegressionRoute extends Route {
  public RegressionRoute(String key) {
    super(key, "Regresi Linier");
  }

  public void run() throws Exception {
    Matrix m = toRegressionMatrix(InputUtils.inputRegression()).toEchelon();
    Solution[] Result = LinearSystem.gaussUnique(m);
    double xTest[] = { 1, 2, 3 };

    Printer printer = new Printer();

    double yResult = 0;
    System.out.print("y = ");
    for (int i = 0; i < Result.length; i++) {
      if (i == 0) {
        System.out.print(Result[i].constant + " ");
        yResult += Result[i].constant;
      } else {
        if (Result[i].constant > 0) {
          System.out.print("+ " + Result[i].constant + " * " + "x_" + i + " ");
        } else if (Result[i].constant < 0) {
          System.out.print("- " + -Result[i].constant + " * " + "x_" + i + " ");
        }
        yResult += Result[i].constant * xTest[i - 1];
      }
    }
    System.out.println();
    System.out.println("y = " + yResult);
  }

  public Matrix toRegressionMatrix(Matrix m) throws Exception {
    Matrix mResult = new Matrix(m.getNCols(), m.getNCols() + 1);
    for (int a = 0; a < mResult.getNRows(); a++) {
      for (int b = 0; b < mResult.getNCols(); b++) {
        double sum;
        if (a == 0 && b == 0) {
          mResult.set(a, b, m.getNRows());
        } else if (a == 0) {
          sum = 0;
          for (int c = 0; c < m.getNRows(); c++) {
            sum += m.get(c, b - 1);
          }
          mResult.set(a, b, sum);
        } else if (b == 0) {
          sum = 0;
          for (int c = 0; c < m.getNRows(); c++) {
            sum += m.get(c, a - 1);
          }
          mResult.set(a, b, sum);
        } else {
          sum = 0;
          for (int c = 0; c < m.getNRows(); c++) {
            sum += m.get(c, b - 1) * m.get(c, a - 1);
          }
          mResult.set(a, b, sum);
        }
      }
    }
    return mResult;
  }
}