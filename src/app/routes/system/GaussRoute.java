package app.routes.system;

import lib.matrix.Matrix;
import java.util.Vector;
import lib.router.Route;
import lib.system.LinearSystem;
import lib.system.LinearSystem.SolutionType;
import lib.utils.IOUtils;

public class GaussRoute extends Route {
  public GaussRoute(String key) {
    super(key, "Metode Gauss");
  }

  public void run() throws Exception {
    char alfabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z' };

    Matrix m = IOUtils.inputMatrix();
    Matrix mEchelon = m.toEchelon();
    SolutionType type = LinearSystem.checkSolutionType(mEchelon);
    System.out.println();
    System.out.println("Matriks Eselon :");
    System.out.println(mEchelon);
    System.out.println();

    if (type == SolutionType.UNIQUE) {
      double solution[];
      solution = new double[mEchelon.getNCols() - 1];
      solution[solution.length - 1] = mEchelon.get(mEchelon.getNRows() - 1, mEchelon.getNCols() - 1);

      for (int i = mEchelon.getNRows() - 2; i >= 0; i--) {
        solution[i] = mEchelon.get(i, mEchelon.getNCols() - 1);
        for (int j = i + 1; j < mEchelon.getNRows(); j++) {
          solution[i] -= mEchelon.get(i, j) * solution[j];
        }
      }

      System.out.println("Solusi :");

      for (int i = 0; i < solution.length; i++) {
        System.out.println("x_" + i + " = " + solution[i]);
      }
    } else if (type == SolutionType.INFINITE) {
      Vector<int[]> pivots = new Vector<>();

      for (int i = mEchelon.getNRows() - 1; i >= 0; i--) {
        for (int j = 0; j < mEchelon.getNCols() - 1; j++) {
          if (mEchelon.get(i, j) == 1) {
            pivots.add(new int[] { i, j });
            break;
          }
        }
      }

      System.out.println("Solusi :");

      int idxalfabet = 0;
      int k;

      for (int j = 0; j < mEchelon.getNCols() - 1; j++) {
        boolean isValInCol = false;

        for (int a = 0; a < pivots.size() && !isValInCol; a++) {
          if (pivots.get(a)[1] == j) {
            isValInCol = true;
          }
        }

        if (isValInCol) {
          Vector<Double> Temp;
          Temp = new Vector<>(mEchelon.getNCols());

          for (int i = 0; i < mEchelon.getNCols(); i++) {
            Temp.add(mEchelon.get(j, i));
          }

          for (int i = j + 1; i < mEchelon.getNCols(); i++) {
            isValInCol = false;
            for (int a = 0; a < pivots.size() && !isValInCol; a++) {
              if (pivots.get(a)[1] == i) {
                isValInCol = true;
              }
            }

            if (isValInCol) {
              for (k = 0; k < Temp.size(); k++) {
                double tempVal = Temp.get(k);
                tempVal -= mEchelon.get(j, i) * mEchelon.get(i, k);
                Temp.set(k, tempVal);
              }
            }
          }

          System.out.print("x_" + j + " = " + Temp.get(mEchelon.getNCols() - 1) + " ");
          int l = 0;
          for (int o = j + 1; o < Temp.size() - 1; o++) {
            isValInCol = false;
            for (int a = 0; a < pivots.size() && !isValInCol; a++) {
              if (pivots.get(a)[1] == o) {
                isValInCol = true;
              }
            }

            if (!isValInCol) {
              if (Temp.get(o) > 0) {
                if (l / 27 == 0) {
                  System.out.print("- " + Temp.get(o) + " * " + alfabet[l % 27]);
                } else {
                  System.out.print("- " + Temp.get(o) + " * " + alfabet[l % 27] + "_" + l / 27);
                }
              } else {
                if (l / 27 == 0) {
                  System.out.print("+ " + -Temp.get(o) + " * " + alfabet[l % 27]);
                } else {
                  System.out.print("+ " + -Temp.get(o) + " * " + alfabet[l % 27] + "_" + l / 27);
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
}
