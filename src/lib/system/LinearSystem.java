package lib.system;

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
 * @version 0.1.4
 * @see lib.matrix.Matrix
 * @see lib.system.Solution
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
   * List of letters for parameter naming
   */
  private static final char alfabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
      'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

  /**
   * Convert an augmented matrix of a system to equations string for display
   * purpose
   * 
   * @param mAug The system in augmented matrix form
   * @return String representing the equations
   */
  public static String toString(Matrix mAug) {
    String str = "";
    int nEqs = mAug.getNRows();
    int nVars = mAug.getNCols() - 1;

    for (int i = 0; i < nEqs; i++) {
      String eqString = "";
      double constant = mAug.get(i, mAug.getNCols() - 1);

      for (int j = 0; j < nVars; j++) {
        double coeff = mAug.get(i, j);

        if (coeff != 0.0) {
          if (coeff > 0.0 && !eqString.isEmpty()) {
            eqString += " + ";
          } else if (coeff < 0.0) {
            eqString += eqString.isEmpty() ? "-" : " - ";
          }

          if (Math.abs(coeff) != 1.0) {
            eqString += Math.abs(coeff) + " * ";
          }

          eqString += "x_" + j;
        }
      }

      if (!eqString.isEmpty()) {
        str += eqString + " = " + constant;
        if (i + 1 < nEqs) {
          str += "\n";
        }
      }
    }

    return str;
  }

  /**
   * Convert array of <code>Solution</code> into string
   * 
   * @param solutions The <code>Solution</code> array
   * @return The solution as equations string
   */
  public static String solutionString(Solution[] solutions) {
    String str = "";

    for (int i = 0; i < solutions.length; i++) {
      str += "x_" + i + " = " + solutions[i];

      if (i + 1 < solutions.length) {
        str += "\n";
      }
    }

    return str;
  }

  /**
   * Solve linear system using Gauss method with the assumption of unique
   * solutions.
   * 
   * @param mEchelon The system as augmented row echelon matrix
   * @return Array of <code>Solution</code>, unique (no parameters)
   */
  public static Solution[] gaussUnique(Matrix mEchelon) {
    int nVars = mEchelon.getNCols() - 1;
    Solution[] solutions = new Solution[nVars];

    for (int i = nVars - 1; i >= 0; i--) {
      solutions[i] = new Solution(mEchelon.get(i, nVars));
      for (int j = i + 1; j < nVars; j++) {
        solutions[i].constant -= mEchelon.get(i, j) * solutions[j].constant;
      }
    }

    return solutions;
  }

  /**
   * Solve linear system using gauss method, assumption: infinite solution.
   * 
   * @param mEchelon The system as augmented row echelon matrix
   * @return Array of <code>Solution</code>, infinite (contains parameters)
   */
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
          if (pivotMap[i] != -1) {
            // This variable is not a parameter, subtitute the real value
            for (int k = 0; k < nCols; k++) {
              double tempVal = row.get(k) - mEchelon.get(pivotMap[j], i) * mEchelon.get(pivotMap[i], k);
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
   * Solve linear system using Gauss Jordan Method, assumes unique solution.
   * 
   * @param mRed The system in reduced row echelon augmented matrix form
   * @return Array of <code>Solution</code>, unique (no parameters)
   */
  public static Solution[] gaussJordanUnique(Matrix mRed) {
    int nVars = mRed.getNCols() - 1;
    Solution[] solutions = new Solution[nVars];

    for (int i = 0; i < nVars; i++) {
      solutions[i] = new Solution(mRed.get(i, nVars));
    }

    return solutions;
  }

  /**
   * Solve linear system using Gauss Jordan Method, assumes unique solution.
   * 
   * @param mRed The system in reduced row echelon augmented matrix form
   * @return Array of <code>Solution</code>, infinite (contains parameters)
   */
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
        int row = -1;
        for (int a = mRed.getNRows() - 1; a >= 0; a--) {
          if (mRed.get(a, j) == 1) {
            row = a;
          }
        }
        solutions[j] = new Solution(mRed.get(row, nCols - 1));
        int paramIdx = 0;
        for (int k = j + 1; k < nCols - 1; k++) {
          double coeff = -mRed.get(row, k);

          String param = Character.toString(alfabet[paramIdx % alfabet.length]);
          int paramSeq = paramIdx / alfabet.length;

          if (paramSeq > 0) {
            param += "_" + paramSeq;
          }

          solutions[j].add(param, coeff);

          if (coeff == 0.0 && !columnHasPivot[k]) {
            paramIdx++;
          }
        }
      }
    }

    return solutions;
  }

  /**
   * Solve linear system using Cramer's rule
   * 
   * @param m The system in augmented matrix form, n x (n+1) <code>Matrix</code>
   * @return Array of <code>Solution</code>, unique (no parameters)
   * @throws Exception If system cannot be solved using this method
   */
  public static Solution[] cramerMethod(Matrix m) throws Exception {
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
    Solution[] solutions = new Solution[mCoeff.getNCols()];

    for (int j = 0; j < mCoeff.getNCols(); j++) {
      Matrix mTmp = mCoeff.copy();
      for (int i = 0; i < mCoeff.getNRows(); i++) {
        mTmp.set(i, j, mConst.get(i, 0));
      }

      solutions[j] = new Solution(Determinant.cofactorMethod(mTmp) / det);
    }

    return solutions;
  }

  /**
   * Solve linear system using inverse method
   * 
   * @param m The system in augmented matrix form
   * @return Array of <code>Solution</code>, unique (no parameters)
   * @throws Exception If system cannot be solved using this method
   */
  public static Solution[] inverseMethod(Matrix m) throws Exception {
    Matrix mA = m.subMatrix(0, 0, m.getNRows(), m.getNCols() - 1);
    Matrix mB = m.subMatrix(0, m.getNCols() - 1);

    if (mA.getNRows() > mA.getNCols()) {
      throw new CannotSolveException(Method.INVERSE, Cause.TOO_MANY_EQ);
    } else if (mA.getNRows() < mA.getNCols()) {
      throw new CannotSolveException(Method.INVERSE, Cause.NON_UNIQUE_SOLUTION);
    }

    if (Determinant.cofactorMethod(mA) == 0.0) {
      throw new CannotSolveException(Method.INVERSE, Cause.NON_UNIQUE_SOLUTION);
    }

    Matrix mAInvers = Inverse.cofactorMethod(mA);
    Matrix mResult = mAInvers.multiplyMatrix(mB);

    Solution[] solutions = new Solution[mResult.getNRows()];

    for (int i = 0; i < solutions.length; i++) {
      solutions[i] = new Solution(mResult.get(i, 0));
    }

    return solutions;
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

    if (nVars < 1) {
      return SolutionType.NONE;
    }

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
