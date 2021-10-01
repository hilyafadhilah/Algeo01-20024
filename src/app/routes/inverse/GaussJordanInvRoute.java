package app.routes.inverse;

import app.router.Route;
import app.utils.InputUtils;
import app.utils.Printer;
import lib.matrix.Inverse;
import lib.matrix.Matrix;
import lib.matrix.SingularMatrixException;

public class GaussJordanInvRoute extends Route {
  public GaussJordanInvRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix(true);
    Printer printer = new Printer();

    try {
      printer.printHeader("Invers Matriks: Metode Gauss-Jordan");
      printer.printSubheader("Matriks Input");
      printer.print("\n" + m + "\n");
      printer.printSubheader("Hasil Matriks Invers");
      Matrix mInvers = Inverse.gaussMethod(m);
      printer.print("\n" + mInvers + "\n");
    } catch (SingularMatrixException e) {
      printer.print("\nMatriks tidak memiliki invers.");
    }

    printer.toFile();
  }
}