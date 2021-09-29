package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import lib.system.LinearSystem;
import lib.system.LinearSystem.SolutionType;

import java.util.Vector;
import lib.utils.InputUtils;

public class GaussJordanRoute extends Route {
  public GaussJordanRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run() throws Exception {
    char alfabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z' };

    Matrix m = InputUtils.inputMatrix();
    Matrix mReducedEchelon = m.toReducedEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mReducedEchelon);

    System.out.println();
    System.out.println("Matriks Eselon Tereduksi :");
    System.out.println(mReducedEchelon);
    System.out.println();

    if (type == SolutionType.UNIQUE) {
      System.out.println("Solusi :");
      for (int i = 0; i < mReducedEchelon.getNCols() - 1; i++) {
        System.out.println("x_" + i + " = " + mReducedEchelon.get(i, mReducedEchelon.getNCols() - 1));
      }
    } else if (type == SolutionType.INFINITE) {
      Vector<int[]> pivots = new Vector<>();

      for (int i = mReducedEchelon.getNRows() - 1; i >= 0; i--) {
        for (int j = 0; j < mReducedEchelon.getNCols() - 1; j++) {
          if (mReducedEchelon.get(i, j) == 1) {
            pivots.add(new int[] { i, j });
            break;
          }
        }
      }

      System.out.println("Solusi :");

      int idxalfabet = 0;
      for (int j = 0; j < mReducedEchelon.getNCols() - 1; j++) {
        boolean isValInCol = false;
        for (int a = 0; a < pivots.size() && !isValInCol; a++) {
          if (pivots.get(a)[1] == j) {
            isValInCol = true;
          }
        }

        if (isValInCol) {
          int k;
          System.out.print("x_" + j + " = " + mReducedEchelon.get(j, mReducedEchelon.getNCols() - 1) + " ");
          int l = 0;
          for (k = j + 1; k < mReducedEchelon.getNCols() - 1; k++) {
            if (mReducedEchelon.get(j, k) != 0) {
              if (mReducedEchelon.get(j, k) > 0) {
                if (l / 27 == 0) {
                  System.out.print("- " + mReducedEchelon.get(j, k) + " * " + alfabet[l % 27]);
                } else {
                  System.out.print("- " + mReducedEchelon.get(j, k) + " * " + alfabet[l % 27] + "_" + l / 27);
                }
              } else {
                if (l / 27 == 0) {
                  System.out.print("+ " + -mReducedEchelon.get(j, k) + " * " + alfabet[l % 27]);
                } else {
                  System.out.print("+ " + -mReducedEchelon.get(j, k) + " * " + alfabet[l % 27] + "_" + l / 27);
                }

              }
              l++;
            }
          }

          System.out.println();
        } else {
          if (idxalfabet / 27 == 0) {
            System.out.print("x_" + j + " = " + alfabet[idxalfabet % 27]);
          } else {
            System.out.println("x_" + j + " = " + alfabet[idxalfabet % 27] + "_" + idxalfabet / 27);
          }
          idxalfabet++;
        }
      }
    } else {
      System.out.println("Tidak ada solusi");
    }
    System.out.println();
  }

  private String[] solveInfinite(Matrix mRed) {
    char alfabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z' };

    int nCols = mRed.getNCols();
    String[] solutions = new String[nCols - 1];
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

        solutions[j] = param;
        idxAlfabet++;
      } else {
        // This variable is not a parameter
        solutions[j] += mRed.get(j, nCols - 1);
        int paramIdx = 0;
        for (int k = j + 1; k < nCols - 1; k++) {
          double coeff = mRed.get(j, k);

          if (coeff != 0.0) {
            if (coeff > 0) {
              solutions[j] += " - " + (coeff == 1.0 ? "" : (coeff + " * "));
            } else {
              solutions[j] += " + " + (coeff == -1.0 ? "" : (-coeff + " * "));
            }

            String param = Character.toString(alfabet[paramIdx % alfabet.length]);
            int paramSeq = paramIdx / alfabet.length;

            if (paramSeq > 0) {
              param += "_" + paramSeq;
            }

            solutions[j] += param;
          }

          paramIdx++;
        }
      }
    }

    return solutions;
  }
}
