package app.routes.determinant;

import lib.matrix.Matrix;
import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Determinant;

public class ReductionRoute extends Route {
  public ReductionRoute(String key) {
    super(key, "Metode Reduksi Baris");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    double det = Determinant.reductionMethod(m);

    Printer printer = new Printer();
    printer.printHeader("Determinan Matriks: Metode Reduksi");
    printer.printSubheader("Matriks Input");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Hasil Perhitungan");
    printer.print("\nDeterminan matriks = " + det + "\n");
    printer.toFile();
  }
}
