package app.routes.determinant;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.router.Route;
import lib.utils.InputUtils;
import lib.utils.Printer;

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
