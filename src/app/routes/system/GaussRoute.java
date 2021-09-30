package app.routes.system;

import lib.matrix.Matrix;

import lib.router.Route;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;
import lib.utils.InputUtils;

public class GaussRoute extends Route {
  public GaussRoute(String key) {
    super(key, "Metode Gauss");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Matrix mEchelon = m.toEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mEchelon);
    System.out.println();
    System.out.println("Matriks Eselon :");
    System.out.println(mEchelon);
    System.out.println();

    if (type == SolutionType.NONE) {
      System.out.println("Tidak ada solusi");
    } else {
      Solution[] solutions;

      if (type == SolutionType.UNIQUE) {
        solutions = LinearSystem.gaussUnique(mEchelon);
      } else {
        solutions = LinearSystem.gaussInfinite(mEchelon);
      }

      System.out.println("Solusi :");
      for (int i = 0; i < solutions.length; i++) {
        System.out.println("x_" + i + " = " + solutions[i]);
      }
    }

    System.out.println();
  }
}
