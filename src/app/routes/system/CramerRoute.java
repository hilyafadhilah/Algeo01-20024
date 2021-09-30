package app.routes.system;

import lib.router.Route;

import lib.matrix.Matrix;
import lib.utils.InputUtils;
import lib.system.LinearSystem;
import lib.system.Solution;

public class CramerRoute extends Route {
  public CramerRoute(String key) {
    super(key, "Kaidah Cramer");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    Solution[] result = LinearSystem.cramerMethod(m);
    System.out.println("Hasil SPL dengan Metode Cramer : ");
    System.out.println();
    for (int i = 0; i < result.length; i++) {
      System.out.println("x_" + i + " = " + result[i]);
    }
  }
}