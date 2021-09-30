package app.routes;

import lib.matrix.Matrix;
import lib.router.Route;
import java.lang.Math;
import lib.utils.InputUtils;
import lib.system.LinearSystem;
import lib.system.Interpolation;

public class InterpolationRoute extends Route {
  public InterpolationRoute(String key) {
    super(key, "Interpolasi Polinom");
  }

  public void run() throws Exception {
    Interpolation Input = InputUtils.inputInterpolation();
    Matrix m = Input.getPoints();
    double x = Input.getSearchValue();
    Matrix mResult = LinearSystem.InverseMethod(ConvertMatrix(m));
    System.out.println(mResult.toString());
    System.out.println("Hasil Polinom : ");
    System.out.println();
    System.out.print("y(x) = ");
    for (int i = 0; i < mResult.getNRows(); i++) {
      if (i == 0) {
        System.out.print(mResult.get(i, 0) + " ");
      } else if (i == 1) {
        if (mResult.get(i, 0) > 0) {
          System.out.print("+ " + mResult.get(i, 0) + "x ");  
        } else if (mResult.get(i, 0)==0){
          System.out.print("+ " + mResult.get(i, 0));  
        } else {
          System.out.print("- " + -mResult.get(i, 0) + "x ");
        }
      } else {
        if (mResult.get(i, 0) > 0) {
          System.out.print("+ " + mResult.get(i, 0) + "x^(" + i + ") ");  
        } else if (mResult.get(i, 0)==0){
          System.out.print("+ " + mResult.get(i, 0));  
        } else {
          System.out.print("- " + -mResult.get(i, 0) + "x^(" + i + ") ");
        }
      }
    }
    System.out.println();
    double sum = 0.0;
    for (int i = 0; i < mResult.getNRows(); i++) {
      sum += mResult.get(i, 0) * Math.pow(x, i);
    }
    System.out.println("y(" + x + ") = " + sum);
  }

  public Matrix ConvertMatrix(Matrix m) {
    Matrix mNew = new Matrix(m.getNRows(), m.getNRows()+1);
    for (int a = 0; a < mNew.getNRows(); a++) {
      for (int b = 0; b < mNew.getNCols(); b++) {
        if (b == mNew.getNCols()-1) {
          mNew.set(a, b, m.get(a, 1));
        } else {
          mNew.set(a, b, Math.pow(m.get(a, 0), b));
        }
      }
    }
    return mNew;
  }
}