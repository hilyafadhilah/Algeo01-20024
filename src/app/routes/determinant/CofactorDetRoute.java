package app.routes.determinant;

import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Determinant;
import lib.matrix.Matrix;

public class CofactorDetRoute extends Route {
  public CofactorDetRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    double det = Determinant.cofactorMethod(m);

    Printer printer = new Printer();
    printer.printHeader("Determinan Matriks: Metode Kofaktor");
    printer.printSubheader("Matriks Input");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Hasil Perhitungan");
    printer.print("\nDeterminan matriks = " + det + "\n");
    printer.toFile();
  }
}
