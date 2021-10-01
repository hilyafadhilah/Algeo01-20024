package lib.matrix;

/**
 * <code>Matrix</code> determinant calculator.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @author Roby Purnomo
 * @version 0.1.2
 * @see Matrix
 * @since 2021-09-24
 */
public class Determinant {
  /**
   * Compute determinant of <code>Matrix</code> mat using the cofactor method
   * (recursive).
   * 
   * @param mat The <code>Matrix</code>, must be square
   * @return The determinant of <code>mat</code>
   * @throws NotSquareMatrixException If the matrix is not square in size
   * @see Matrix#cofactor(int, int)
   */
  public static double cofactorMethod(Matrix mat) throws NotSquareMatrixException {
    if (!mat.isSquare()) {
      throw new NotSquareMatrixException();
    }

    if (mat.getNRows() == 2) {
      // Basis: 2 x 2 matrix
      return mat.get(0, 0) * mat.get(1, 1) - mat.get(0, 1) * mat.get(1, 0);
    } else {
      // Recurrence: N x N matrix where N > 2
      int size = mat.getNRows();
      double det = 0.0;

      for (int col = 0; col < size; col++) {
        // Use first row as the pivot
        // Compute cofactor
        Matrix cofactor = mat.cofactor(0, col);

        // Compute addend (with sign), notice the recursive call
        double addend = mat.get(0, col) * cofactorMethod(cofactor);
        if (col % 2 != 0) {
          addend = -addend;
        }

        // Add it to the solution
        det += addend;
      }
      return det;
    }
  }

  /**
   * Compute determinant of a <code>Matrix</code> using the matrix reduction
   * method.
   * 
   * @param mat The <code>Matrix</code>, must be square
   * @return The determinant of <code>mat</code>
   * @throws NotSquareMatrixException If the matrix is not square in size
   */
  public static double reductionMethod(Matrix mat) throws Exception {
    Matrix mTemp = mat.copy();
    double det = 1;

    int i = 0;
    for (int j = 0; j < mTemp.getNCols() && i < mTemp.getNRows(); j++) {
      System.out.println(mTemp + "\n");
      if (mTemp.pivotRowIndex(i, j) < mTemp.getNRows()) {
        int pivot = mTemp.pivotRowIndex(i, j);

        if (pivot != -1 && pivot != i) {
          mTemp.swapRows(i, pivot);
          det *= -1;
        }

        double val = mTemp.get(i, j);
        det *= val;

        if (val != 0.0) {
          mTemp.divideRow(i, mTemp.get(i, j));
        }

        for (int k = i + 1; k < mTemp.getNRows(); k++) {
          mTemp.elementaryRowAdd(k, i, -mTemp.get(k, j));
        }

        i++;
      }
    }

    return det;
  }
}
