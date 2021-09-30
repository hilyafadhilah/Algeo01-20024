package app.routes.inverse;

import lib.matrix.Matrix;
import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Inverse;

public class CofactorInvRoute extends Route {
  public CofactorInvRoute(String key) {
    super(key, "Metode Kofaktor");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    Matrix mInvers = Inverse.cofactorMethod(m);

    Printer printer = new Printer();
    printer.printHeader("Invers Matriks: Metode Kofaktor");
    printer.printSubheader("Matriks Input");
    printer.print("\n" + m + "\n");
    printer.printSubheader("Hasil Matriks Invers");
    printer.print("\n" + mInvers + "\n");
    printer.toFile();
  }
}
