package app.routes.system;

import lib.matrix.Inverse;
import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;
import lib.utils.Printer;
import lib.system.LinearSystem;
import lib.system.Solution;

public class InverseMethodRoute extends Route {
  public InverseMethodRoute(String key) {
    super(key, "Metode Matriks Balikan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Solution[] result = LinearSystem.inverseMethod(m);
    Matrix mInv = Inverse.cofactorMethod(m.subMatrix(0, 0, m.getNRows(), m.getNCols() - 1));

    Printer printer = new Printer();
    printer.printHeader("Solusi SPL: Metode Invers");
    printer.printSubheader("Input SPL");
    printer.print("\n" + LinearSystem.toString(m) + "\n");
    printer.printSubheader("Representasi Matriks");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Invers Matriks Koefisien");
    printer.print("\n" + mInv + "\n");
    printer.printSubheader("Solusi SPL");
    printer.print("\n" + LinearSystem.solutionString(result) + "\n");
    printer.toFile();
  }
}