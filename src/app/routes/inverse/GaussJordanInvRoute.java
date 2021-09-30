package app.routes.inverse;

import lib.router.Route;
import lib.matrix.Inverse;
import lib.matrix.Matrix;
import lib.utils.InputUtils;
import lib.utils.Printer;

public class GaussJordanInvRoute extends Route {
  public GaussJordanInvRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    Matrix mInvers = Inverse.gaussMethod(m);

    Printer printer = new Printer();
    printer.printHeader("Invers Matriks: Metode Gauss-Jordan");
    printer.printSubheader("Matriks Input");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Hasil Matriks Invers");
    printer.print("\n" + mInvers + "\n");
    printer.toFile();
  }
}