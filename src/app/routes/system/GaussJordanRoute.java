package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;
import lib.utils.InputUtils;

public class GaussJordanRoute extends Route {
  public GaussJordanRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Matrix mReducedEchelon = m.toReducedEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mReducedEchelon);

    System.out.println();
    System.out.println("Matriks Eselon Tereduksi :");
    System.out.println(mReducedEchelon);
    System.out.println();

    if (type == SolutionType.NONE) {
      System.out.println("Tidak ada solusi");
    } else {
      Solution[] solutions;

      if (type == SolutionType.UNIQUE) {
        solutions = LinearSystem.gaussJordanUnique(mReducedEchelon);
      } else {
        solutions = LinearSystem.gaussJordanInfinite(mReducedEchelon);
      }

      System.out.println("Solusi :");
      for (int i = 0; i < solutions.length; i++) {
        System.out.println("x_" + i + " = " + solutions[i]);
      }
    }

    System.out.println();
  }
}
