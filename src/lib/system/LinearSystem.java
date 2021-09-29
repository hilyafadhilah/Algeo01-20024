package lib.system;

import java.util.ArrayList;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
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
}
