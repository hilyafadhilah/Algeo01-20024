package lib.matrix;

import lib.math.DivisionByZeroException;

/**
 * <code>Matrix</code> inverse calculator.
 * 
 * @author Roby Purnomo
 * @version 0.1.1
 * @since 2021-09-28
 */
public class Inverse {
  /**
   * Find the inverse using Gauss method
   * 
   * @param m A square matrix
   * @return Inverse of the matrix
   * @throws Exception If the matrix is not square
   */
  public static Matrix gaussMethod(Matrix m) throws SingularMatrixException, DivisionByZeroException {
    Matrix mProcces = new Matrix(m.getNRows(), 2 * m.getNCols());
    for (int a = 0; a < mProcces.getNRows(); a++) {
      for (int b = 0; b < mProcces.getNCols(); b++) {
        if (a == (b - m.getNRows())) {
          mProcces.set(a, b, 1);
        } else if (b > mProcces.getNRows() - 1) {
          mProcces.set(a, b, 0);
        } else {
          mProcces.set(a, b, m.get(a, b));
        }
      }
    }
    Matrix mTemp = mProcces.toReducedEchelon();
    Matrix mResult = new Matrix(m.getNRows(), m.getNCols());
    Matrix mIdentity = new Matrix(m.getNRows(), m.getNCols());
    for (int c = 0; c < mResult.getNRows(); c++) {
      for (int d = 0; d < mResult.getNCols(); d++) {
        mResult.set(c, d, mTemp.get(c, d + m.getNCols()));
        mIdentity.set(c, d, mTemp.get(c, d));
      }
    }
    if (mIdentity.isIdentity()) {
      return mResult;
    } else {
      throw new SingularMatrixException("Matriks tidak memiliki invers.");
    }
  }

  /**
   * Find the inverse using cofactor
   * 
   * @param m A square matrix
   * @return Inverse of the matrix
   * @throws Exception If the matrix is not square
   */
  public static Matrix cofactorMethod(Matrix m) throws Exception {
    if (Determinant.cofactorMethod(m) != 0) {
      if (m.getNCols() == 2) {
        Matrix mCofactor = new Matrix(2, 2);
        mCofactor.set(0, 0, m.get(1, 1));
        mCofactor.set(0, 1, -m.get(0, 1));
        mCofactor.set(1, 0, -m.get(1, 0));
        mCofactor.set(1, 1, m.get(0, 0));
        Matrix mInvers = mCofactor.divide(Determinant.cofactorMethod(m));
        return mInvers;
      } else {
        Matrix mCofactor = m.toCofactor().transpose();
        Matrix mInvers = mCofactor.divide(Determinant.cofactorMethod(m));
        return mInvers;
      }
    } else {
      throw new SingularMatrixException("Matriks tidak memiliki invers.");
    }
  }
}
