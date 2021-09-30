package app.routes;

import lib.matrix.Matrix;

import java.lang.Math;

import app.interpolation.Interpolation;
import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;

public class InterpolationRoute extends Route {
  public InterpolationRoute(String key) {
    super(key, "Interpolasi Polinom");
  }

  public void run() throws Exception {
    Interpolation Input = InputUtils.inputInterpolation();
    Matrix m = ConvertMatrix(Input.getPoints());
    double x = Input.getSearchValue();

    Printer printer = new Printer();
    printer.printHeader("Interpolasi Polinom");

    if (LinearSystem.checkSolutionType(m) == SolutionType.UNIQUE) {
      Solution[] result = LinearSystem.gaussJordanUnique(m.toReducedEchelon());

      printer.printSubheader("Input Dataset");
      printer.print("\n" + Input + "\n");
      printer.print("\nNilai yang ingin ditaksir = " + x + "\n");
      printer.printSubheader("Representasi Matriks");
      printer.print("\n" + m + "\n");

      printer.printSubheader("Persamaan Interpolasi Polinom");
      printer.print("\ny(x) = ");
      for (int i = 0; i < result.length; i++) {
        double value = result[i].constant;

        if (i == 0) {
          printer.print(value);
        } else {
          printer.print(" ");

          if (value > 0.0) {
            printer.print("+ " + value);
          } else if (value < 0.0) {
            printer.print("- " + -value);
          }

          printer.print(" * x");

          if (i > 1) {
            printer.print("^(" + i + ")");
          }
        }
      }
      printer.print("\n");

      double sum = 0.0;
      for (int i = 0; i < result.length; i++) {
        sum += result[i].constant * Math.pow(x, i);
      }

      printer.printSubheader("Hasil Taksiran");
      printer.print("\ny(" + x + ") = " + sum + "\n");
      printer.toFile();
    }
  }

  public Matrix ConvertMatrix(Matrix m) {
    Matrix mNew = new Matrix(m.getNRows(), m.getNRows() + 1);
    for (int a = 0; a < mNew.getNRows(); a++) {
      for (int b = 0; b < mNew.getNCols(); b++) {
        if (b == mNew.getNCols() - 1) {
          mNew.set(a, b, m.get(a, 1));
        } else {
          mNew.set(a, b, Math.pow(m.get(a, 0), b));
        }
      }
    }
    return mNew;
  }
}