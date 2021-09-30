package lib.system;

import java.util.ArrayList;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.matrix.Inverse;
import lib.system.CannotSolveException.Cause;
import lib.system.CannotSolveException.Method;

/**
 * Linear equation system solver
 * 
 * @author Jundan Haris
 * @version 0.1.0
 * @see lib.matrix.Matrix
 * @since 2021-09-28
 */
public class LinearSystem {
  /**
   * SolutionType types
   */
  public enum SolutionType {
    UNIQUE, INFINITE, NONE
  }

  /**
   * Solve linear system using Cramer's rule
   * 
   * @param m The system in augmented matrix form, n x (n+1) <code>Matrix</code>
   * @return List of n solutions
   * @throws Exception If system cannot be solved using this method
   */
  public static ArrayList<Double> cramerMethod(Matrix m) throws Exception {
    if (m.getNRows() < (m.getNCols() - 1)) {
      throw new CannotSolveException(Method.CRAMER, Cause.NON_UNIQUE_SOLUTION);
    } else if (m.getNRows() > (m.getNCols() - 1)) {
      throw new CannotSolveException(Method.CRAMER, Cause.TOO_MANY_EQ);
    }

    Matrix mCoeff = m.subMatrix(0, 0, m.getNRows(), m.getNCols() - 1);
    double det = Determinant.cofactorMethod(mCoeff);

    if (det == 0.0) {
      throw new CannotSolveException(Method.CRAMER, Cause.NON_UNIQUE_SOLUTION);
    }

    Matrix mConst = m.subMatrix(0, m.getNCols() - 1);
    ArrayList<Double> solution = new ArrayList<>();

    for (int j = 0; j < mCoeff.getNCols(); j++) {
      Matrix mTmp = mCoeff.copy();
      for (int i = 0; i < mCoeff.getNRows(); i++) {
        mTmp.set(i, j, mConst.get(i, 0));
      }

      solution.add(Determinant.cofactorMethod(mTmp) / det);
    }

    return solution;
  }

  /**
   * Returns a complete augmented matrix version of a linear system. The number of
   * rows should be at least equal to the number of columns minus 1.
   * 
   * @param m The system in augmented matrix form
   * @return The completed augmented matrix of the system
   */
  public static Matrix completeAugmentedMatrix(Matrix m) {
    Matrix result = m.copy();
    int currentRows = m.getNRows();
    int idealRows = m.getNCols() - 1; // Supposed row length

    if (currentRows < idealRows) {
      result.addRows(idealRows - currentRows);
      result.setAll(0.0, currentRows, 0);
    }

    return result;
  }

  /**
   * Check solution type of a system in augmented row echelon matrix form
   * 
   * @param mEchelon Linear system in augmented row echelon matrix form
   * @return SolutionType type
   */
  public static SolutionType checkSolutionType(Matrix mEchelon) {
    boolean isAllZero = true;
    int i;
    for (i = mEchelon.getNRows() - 1; i >= 0 && isAllZero; i--) {
      isAllZero = true;
      for (int j = 0; j <= mEchelon.getNCols() - 1 && isAllZero; j++) {
        if (mEchelon.get(i, j) != 0) {
          isAllZero = false;
        }
      }
    }
    i++;
    isAllZero = true;
    for (int j = 0; j < mEchelon.getNCols() - 1 && isAllZero; j++) {
      if (mEchelon.get(i, j) != 0) {
        isAllZero = false;
      }
    }

    if (isAllZero && mEchelon.get(i, mEchelon.getNCols() - 1) != 0) {
      return SolutionType.NONE;
    } else if (i + 1 == mEchelon.getNCols() - 1) {
      return SolutionType.UNIQUE;
    } else {
      return SolutionType.INFINITE;
    }
  }

  public static Matrix InverseMethod (Matrix m) throws Exception {
    Matrix mA = new Matrix(m.getNRows(), m.getNCols() - 1);
    Matrix mB = new Matrix(m.getNRows(), 1);

    for (int a = 0; a < m.getNRows(); a++) {
      mB.set(a, 0, m.get(a, m.getNCols() - 1));
      for (int b = 0; b < mA.getNCols(); b++) {
        mA.set(a, b, m.get(a, b));
      }
    }
    Matrix mAInvers = Inverse.CofactorMethod(mA);
    Matrix mResult = mAInvers.multiplyMatrix(mB);
    return mResult;
  }
}
