package app.routes.system;

import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Matrix;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;

public class GaussRoute extends Route {
  public GaussRoute(String key) {
    super(key, "Metode Gauss");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Matrix mEchelon = m.toEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mEchelon);

    Printer printer = new Printer();
    printer.printHeader("Solusi SPL: Metode Gauss");
    printer.printSubheader("Input SPL");
    printer.print("\n" + LinearSystem.toString(m) + "\n");
    printer.printSubheader("Representasi Matriks");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Matriks Eselon Baris");
    printer.print("\n" + mEchelon + "\n");
    printer.printSubheader("Solusi SPL");

    if (type == SolutionType.NONE) {
      printer.println("\nSPL tidak memiliki solusi.");
    } else {
      Solution[] solutions;

      if (type == SolutionType.UNIQUE) {
        solutions = LinearSystem.gaussUnique(mEchelon);
      } else {
        solutions = LinearSystem.gaussInfinite(mEchelon);
      }

      printer.print("\n" + LinearSystem.solutionString(solutions) + "\n");
    }

    printer.toFile();
  }
}
