package lib.matrix;

/**
 * <code>Matrix</code> determinant calculator.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @author [TODO]
 * @version 0.1.1
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
   * [TODO]
   * 
   * @param mat The <code>Matrix</code>, must be square
   * @return The determinant of <code>mat</code>
   * @throws NotSquareMatrixException If the matrix is not square in size
   */
  public static double reductionMethod(Matrix mat) throws Exception {
    Matrix mTemp;
    mTemp = mat.copy();
    int i, j, k;
    i = 0;
    double det = 1;
    for (j = 0; j < mTemp.getNCols() && i < mTemp.getNRows(); j++) {
      if (mTemp.pivotRowIndex(i, j) < mTemp.getNRows()) {
        mTemp.swapRows(i, mTemp.pivotRowIndex(i, j));
        if (mTemp.pivotRowIndex(i, j) != i) {
          det *= -1;
        }
        det *= mTemp.get(i, j);
        mTemp.divideRow(i, mTemp.get(i, j));
        for (k = i + 1; k < mTemp.getNRows(); k++) {
          mTemp.elementaryRowAdd(k, i, -mTemp.get(k, j));
        }
        i++;
      }
    }
    return det;
  }
}
