package app.routes.system;

import lib.matrix.Matrix;
import lib.router.Route;
import java.util.Vector;
import lib.utils.IOUtils;

public class GaussJordanRoute extends Route {
  public GaussJordanRoute(String key) {
    super(key, "Metode Gauss-Jordan");
  }

  public void run () throws Exception {
    char alfabet[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    Matrix m = IOUtils.inputMatrix();
    Matrix mReducedEchelon = m.toReducedEchelon();
    System.out.println();
    System.out.println("Matriks Eselon Tereduksi :");
    System.out.println(mReducedEchelon);
    System.out.println();
    if (this.checksolusi(mReducedEchelon) == 1) {
      System.out.println("Solusi :");
      for (int i = 0; i < mReducedEchelon.getNCols()-1; i++) {
        System.out.println("x_" + i + " = " + mReducedEchelon.get(i, mReducedEchelon.getNCols()-1));
      }
    } else if (this.checksolusi(mReducedEchelon) == 2) {
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
      for (int j = 0; j < mReducedEchelon.getNCols()-1; j++) {
        boolean isValInCol = false;
        for (int a = 0; a < pivots.size() && !isValInCol; a++) {
          if (pivots.get(a)[1] == j) {
            isValInCol = true;
          }
        }
        if (isValInCol) {
          int k;
          System.out.print("x_" + j + " = " + mReducedEchelon.get(j, mReducedEchelon.getNCols()-1) + " ");
          int l = 0;
          for (k = j + 1; k < mReducedEchelon.getNCols()-1; k++) {
            if (mReducedEchelon.get(j, k) != 0) {
              if (mReducedEchelon.get(j, k) > 0) {
                if (l/27 == 0) {
                  System.out.print("- " + mReducedEchelon.get(j,k) + " * " + alfabet[l%27]);
                } else {
                  System.out.print("- " + mReducedEchelon.get(j,k) + " * " + alfabet[l%27] + "_" + l/27);
                }
              } else {
                if (l/27 == 0) {
                  System.out.print("+ " + -mReducedEchelon.get(j,k) + " * " + alfabet[l%27]);
                } else {
                  System.out.print("+ " + -mReducedEchelon.get(j,k) + " * " + alfabet[l%27] + "_" + l/27);
                }
                
              }
              l++;
            }
          }
          System.out.println();
        } else {
          if (idxalfabet/27 == 0) {
            System.out.print("x_" + j + " = " + alfabet[idxalfabet%27]);
          } else {
            System.out.println("x_" + j + " = " + alfabet[idxalfabet%27] + "_" + idxalfabet/27);
          }
          idxalfabet++;
        }
      }
    } else {
      System.out.println("Tidak ada solusi");
    }
    System.out.println();
  }

  public int checksolusi (Matrix ReducedEchelon) {
    boolean isAllZero = true;
    int i;
    for (i = ReducedEchelon.getNRows()-1; i >= 0 && isAllZero; i--) {
      isAllZero = true;
      for (int j = 0; j <= ReducedEchelon.getNCols()-1 && isAllZero; j++) {
        if (ReducedEchelon.get(i,j) != 0) {
          isAllZero = false;
        }
      }
    }
    i++;
    isAllZero = true;
    for (int j = 0; j < ReducedEchelon.getNCols()-1 && isAllZero; j++) {
      if (ReducedEchelon.get(i, j) != 0) {
        isAllZero = false;
      }
    }
    if (isAllZero && ReducedEchelon.get(i, ReducedEchelon.getNCols()-1) != 0) {
      return 3;
    } else if (i+1 == ReducedEchelon.getNCols()-1) {
      return 1;
    } else {
      return 2;
    }
  }
}
