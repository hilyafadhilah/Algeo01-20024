package app.routes;

import app.router.Route;
import lib.matrix.Matrix;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.system.LinearSystem;

public class RegressionRoute extends Route {
  public RegressionRoute(String key) {
    super(key, "Regresi Linier");
  }

  public void run() throws Exception {
    Matrix mInput = InputUtils.inputRegression();
    Matrix m = toRegressionMatrix(mInput).toEchelon();

    if (LinearSystem.checkSolutionType(m) == SolutionType.UNIQUE) {
      Solution[] Result = LinearSystem.gaussUnique(m);
      double xTest[] = InputUtils.inputVariables(Result.length - 1);

      Printer printer = new Printer();
      printer.printHeader("Regresi Linier Berganda");
      printer.printSubheader("Input Dataset");

      printer.print("\n");
      for (int i = 0; i < mInput.getNCols() - 1; i++) {
        printer.print("x_" + i + "  ");
      }
      printer.print("y");

      printer.print("\n" + mInput + "\n");
      printer.printSubheader("Nilai yang Ingin Ditaksir");
      printer.print("\n");

      for (int i = 0; i < xTest.length; i++) {
        printer.println("x_" + i + " = " + xTest[i]);
      }

      printer.printSubheader("Persamaan Hasil Regresi");
      printer.print("\ny = ");

      double yResult = 0;
      for (int i = 0; i < Result.length; i++) {
        double value = Result[i].constant;
        if (i == 0) {
          printer.print(value);
          yResult += value;
        } else {
          if (value > 0) {
            printer.print(" + " + value + " * " + "x_" + (i - 1));
          } else if (value < 0) {
            printer.print(" - " + -value + " * " + "x_" + (i - 1));
          }

          yResult += value * xTest[i - 1];
        }
      }

      printer.print("\n");
      printer.printSubheader("Hasil Taksiran");
      printer.println("\ny = " + yResult);
      printer.toFile();
    } else {
      throw new Exception("Tidak dapat melakukan regresi karena tidak ditemukan solusi unik.");
    }
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