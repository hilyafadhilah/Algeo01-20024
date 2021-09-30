package app.routes.system;

import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Matrix;
import lib.system.LinearSystem;
import lib.system.Solution;

public class CramerRoute extends Route {
  public CramerRoute(String key) {
    super(key, "Kaidah Cramer");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Solution[] result = LinearSystem.cramerMethod(m);

    Printer printer = new Printer();
    printer.printHeader("Solusi SPL: Kaidah Cramer");
    printer.printSubheader("Input SPL");
    printer.print("\n" + LinearSystem.toString(m) + "\n");
    printer.printSubheader("Representasi Matriks");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Solusi SPL");
    printer.print("\n" + LinearSystem.solutionString(result) + "\n");
    printer.toFile();
  }
}
