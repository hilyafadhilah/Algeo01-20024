package app.routes.determinant;

import lib.router.Route;
import lib.utils.IOUtils;
import lib.matrix.Matrix;
import lib.matrix.Determinant;

public class ReductionRoute extends Route {
  public ReductionRoute(String key) {
    super(key, "Metode Reduksi Baris");
  }

  public void run() throws Exception {
    Matrix m = IOUtils.inputMatrix(true);
    double det = Determinant.reductionMethod(m);
    System.out.println("\nDeterminan matriks = " + det);
  }
}
