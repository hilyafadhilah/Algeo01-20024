package app.routes.system;

import lib.router.Route;

import java.util.ArrayList;

import lib.matrix.Matrix;
import lib.utils.InputUtils;
import lib.system.LinearSystem;

public class CramerRoute extends Route {
  public CramerRoute(String key) {
    super(key, "Kaidah Cramer");
  }

  public void run() throws Exception {
    Matrix m = InputUtils.inputMatrix();
    ArrayList<Double> result = LinearSystem.cramerMethod(m);
    System.out.println("Hasil SPL dengan Metode Cramer : ");
    System.out.println();
    for (int i = 0; i < result.size(); i++) {
      System.out.println("x_" + i + " = " + result.get(i));
    }
  }
}