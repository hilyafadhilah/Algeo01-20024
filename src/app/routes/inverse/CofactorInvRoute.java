package app.routes.inverse;

import lib.matrix.Matrix;
import lib.matrix.SingularMatrixException;
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
    Printer printer = new Printer();

    try {
      printer.printHeader("Invers Matriks: Metode Kofaktor");
      printer.printSubheader("Matriks Input");
      printer.print("\n" + m + "\n");
      printer.printSubheader("Hasil Matriks Invers");

      Matrix mInvers = Inverse.cofactorMethod(m);
      printer.print("\n" + mInvers + "\n");
    } catch (SingularMatrixException e) {
      printer.print("\nMatriks tidak memiliki Invers.");
    }

    printer.toFile();
  }
}
