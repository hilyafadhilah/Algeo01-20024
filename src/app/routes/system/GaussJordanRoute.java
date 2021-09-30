package app.routes.system;

import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Matrix;
import lib.system.LinearSystem;
import lib.system.Solution;
import lib.system.LinearSystem.SolutionType;

public class GaussJordanRoute extends Route {
  public GaussJordanRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Matrix mReducedEchelon = m.toReducedEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mReducedEchelon);

    Printer printer = new Printer();
    printer.printHeader("Solusi SPL: Metode Gauss-Jordan");
    printer.printSubheader("Input SPL");
    printer.print("\n" + LinearSystem.toString(m) + "\n");
    printer.printSubheader("Representasi Matriks");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Matriks Eselon Baris");
    printer.print("\n" + m.toEchelon() + "\n");
    printer.printSubheader("Matriks Eselon Baris Tereduksi");
    printer.print("\n" + mReducedEchelon + "\n");
    printer.printSubheader("Solusi SPL");

    if (type == SolutionType.NONE) {
      printer.println("\nSPL tidak memiliki solusi.");
    } else {
      Solution[] solutions;

      if (type == SolutionType.UNIQUE) {
        solutions = LinearSystem.gaussJordanUnique(mReducedEchelon);
      } else {
        solutions = LinearSystem.gaussJordanInfinite(mReducedEchelon);
      }

      printer.print("\n" + LinearSystem.solutionString(solutions) + "\n");
    }

    printer.toFile();
  }
}
