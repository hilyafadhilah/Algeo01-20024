package lib.system;

import java.util.ArrayList;
import java.util.Vector;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.matrix.Inverse;
import lib.system.CannotSolveException.Cause;
import lib.system.CannotSolveException.Method;

/**
 * Linear equation system solver
 * 
 * @author Roby Purnomo
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

  private static final char alfabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
      'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

  protected Matrix m;

  public static Solution[] gaussUnique(Matrix mEchelon) {
    int nVars = mEchelon.getNCols() - 1;
    Solution[] solutions = new Solution[nVars];

    for (int i = nVars - 1; i >= 0; i--) {
      solutions[i] = new Solution(mEchelon.get(i, nVars));
      for (int j = i + 1; j < mEchelon.getNRows(); j++) {
        solutions[i].constant -= mEchelon.get(i, j) * solutions[j].constant;
      }
    }

    return solutions;
  }

  public static Solution[] gaussInfinite(Matrix mEchelon) {

    int nCols = mEchelon.getNCols();
    Solution[] solutions = new Solution[nCols - 1];
    Vector<int[]> pivots = mEchelon.getPivots();
    int[] pivotMap = new int[nCols - 1];

    // Check if each columns contains a pivot
    // Keep in mind, column with no pivot is a parameter
    for (int j = 0; j < nCols - 1; j++) {
      pivotMap[j] = -1;
      for (int[] pivot : pivots) {
        if (pivot[1] == j) {
          pivotMap[j] = pivot[0];
          break;
        }
      }
    }

    // Loop through solutions, number of solutions = columns - 1
    int idxAlfabet = 0;
    for (int j = 0; j < nCols - 1; j++) {
      if (pivotMap[j] == -1) {
        // This variable is a parameter
        String param = Character.toString(alfabet[idxAlfabet % alfabet.length]);
        int paramSeq = idxAlfabet / alfabet.length;

        if (paramSeq > 0) {
          param += "_" + paramSeq;
        }

        solutions[j] = new Solution(param);
        idxAlfabet++;
      } else {
        // This variable is not a parameter
        Vector<Double> row = mEchelon.getRowCopy(pivotMap[j]);

        // Do backwards substitution
        for (int i = j + 1; i < nCols - 1; i++) {
          if (pivotMap[i] == -1) {
            // This variable is not a parameter, subtitute the real value
            for (int k = 0; k < nCols; k++) {
              double tempVal = row.get(k) - mEchelon.get(j, i) * mEchelon.get(i, k);
              row.set(k, tempVal);
            }
          }
        }

        // Rightmost value is now the final constant
        solutions[j] = new Solution(row.get(nCols - 1));

        // Insert params
        int paramIdx = 0;
        for (int i = j + 1; i < nCols - 1; i++) {
          if (pivotMap[i] == -1) {
            // This variable is a parameter
            double coeff = -row.get(i);

            String param = Character.toString(alfabet[paramIdx % alfabet.length]);
            int paramSeq = paramIdx / alfabet.length;

            if (paramSeq > 0) {
              param += "_" + paramSeq;
            }

            solutions[j].add(param, coeff);
            paramIdx++;
          }
        }
      }
    }

    return solutions;
  }

  /**
   * Solve linear system using Gauss Jordan Method only if the solution is unique.
   * 
   * @param m The system in augmented matrix form
   * @return List of solutions
   * @throws Exception If solution is not unique
   */
  public static Solution[] gaussJordanUnique(Matrix mRed) {
    int nVars = mRed.getNCols() - 1;
    Solution[] solutions = new Solution[nVars];

    for (int i = 0; i < nVars; i++) {
      solutions[i] = new Solution(mRed.get(i, nVars));
    }

    return solutions;
  }

  public static Solution[] gaussJordanInfinite(Matrix mRed) {
    int nCols = mRed.getNCols();
    Solution[] solutions = new Solution[nCols - 1];
    Vector<int[]> pivots = mRed.getPivots();

    // Check if each columns contains a pivot
    // Keep in mind, column with no pivot is a parameter
    boolean[] columnHasPivot = new boolean[nCols - 1];
    for (int j = 0; j < nCols - 1; j++) {
      columnHasPivot[j] = false;
      for (int[] pivot : pivots) {
        if (pivot[1] == j) {
          columnHasPivot[j] = true;
          break;
        }
      }
    }

    // Loop through solutions, number of solutions = columns - 1
    int idxAlfabet = 0;
    for (int j = 0; j < mRed.getNCols() - 1; j++) {
      if (!columnHasPivot[j]) {
        // This variable is a parameter
        String param = Character.toString(alfabet[idxAlfabet % alfabet.length]);
        int paramSeq = idxAlfabet / alfabet.length;

        if (paramSeq > 0) {
          param += "_" + paramSeq;
        }

        solutions[j] = new Solution(param);
        idxAlfabet++;
      } else {
        // This variable is not a parameter
        solutions[j] = new Solution(mRed.get(j, nCols - 1));
        int paramIdx = 0;
        for (int k = j + 1; k < nCols - 1; k++) {
          double coeff = -mRed.get(j, k);

          String param = Character.toString(alfabet[paramIdx % alfabet.length]);
          int paramSeq = paramIdx / alfabet.length;

          if (paramSeq > 0) {
            param += "_" + paramSeq;
          }

          solutions[j].add(param, coeff);
          paramIdx++;
        }
      }
    }

    return solutions;
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

  public static Matrix InverseMethod(Matrix m) throws Exception {
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
    int nRows = mEchelon.getNRows();
    int nCols = mEchelon.getNCols();
    int nVars = nCols - 1;

    if (nRows < nVars) {
      return SolutionType.INFINITE;
    }

    double constant = mEchelon.get(nVars - 1, nCols - 1);
    double coeff = mEchelon.get(nVars - 1, nCols - 2);

    if (coeff == 0.0) {
      if (constant != 0.0) {
        return SolutionType.NONE;
      } else {
        return SolutionType.INFINITE;
      }
    } else if (nRows > nVars) {
      for (int i = nVars; i < nRows; i++) {
        if (mEchelon.get(i, nCols - 1) != 0.0) {
          return SolutionType.NONE;
        }
      }
    }

    return SolutionType.UNIQUE;
  }
}
