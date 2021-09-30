package app.routes;

import lib.matrix.Matrix;
import lib.router.Route;
import java.lang.Math;
import lib.utils.InputUtils;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;
import lib.system.Interpolation;

public class InterpolationRoute extends Route {
  public InterpolationRoute(String key) {
    super(key, "Interpolasi Polinom");
  }

  public void run() throws Exception {
    Interpolation Input = InputUtils.inputInterpolation();
    Matrix m = ConvertMatrix(Input.getPoints()).toReducedEchelon();
    double x = Input.getSearchValue();

    if (LinearSystem.checkSolutionType(m) == SolutionType.UNIQUE) {
      Solution[] result = LinearSystem.gaussJordanUnique(m);
      System.out.println("Hasil Polinom : ");
      System.out.println();
      System.out.print("y(x) = ");
      for (int i = 0; i < result.length; i++) {
        double value = result[i].constant;
        if (i == 0) {
          System.out.print(value + " ");
        } else if (i == 1) {
          if (value > 0) {
            System.out.print("+ " + value + "x ");
          } else if (value == 0) {
            System.out.print("+ " + value);
          } else {
            System.out.print("- " + -value + "x ");
          }
        } else {
          if (value > 0) {
            System.out.print("+ " + value + "x^(" + i + ") ");
          } else if (value == 0) {
            System.out.print("+ " + value);
          } else {
            System.out.print("- " + -value + "x^(" + i + ") ");
          }
        }
      }
      System.out.println();
      double sum = 0.0;
      for (int i = 0; i < result.length; i++) {
        sum += result[i].constant * Math.pow(x, i);
      }
      System.out.println("y(" + x + ") = " + sum);
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