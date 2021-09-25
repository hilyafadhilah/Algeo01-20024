package lib.matrix;

import java.util.Scanner;
import java.util.Vector;

public class Matrix {
  protected int nRows;
  protected int nCols;
  protected Vector<Vector<Double>> matrix;

  public Matrix(int nRows, int nCols) {
    this.nRows = nRows;
    this.nCols = nCols;
    this.matrix = new Vector<Vector<Double>>(nRows);
    for (int i = 0; i < nRows; i++) {
      this.matrix.add(new Vector<Double>(nCols));
      for (int j = 0; j < nCols; j++) {
        this.matrix.get(i).add(null);
      }
    }
  }

  public double get(int i, int j) {
    return this.matrix.get(i).get(j);
  }

  public void set(int i, int j, double value) {
    this.matrix.get(i).set(j, value);
  }

  private Vector<Double> getRow(int i) {
    return this.matrix.get(i);
  }

  private void setRow(int i, Vector<Double> value) throws Exception {
    if (value.size() == this.nCols) {
      this.matrix.set(i, value);
    } else {
      throw new Exception();
    }
  }

  public static Matrix read(Scanner scanner) {
    int nRows = scanner.nextInt();
    int nCols = scanner.nextInt();
    Matrix mat = new Matrix(nRows, nCols);

    for (int i = 0; i < nRows; i++) {
      for (int j = 0; j < nCols; j++) {
        mat.set(i, j, scanner.nextFloat());
      }
    }

    return mat;
  }

  public void swapRows(int i1, int i2) throws Exception {
    Vector<Double> tmp = this.getRow(i1);
    this.setRow(i1, this.getRow(i2));
    this.setRow(i2, tmp);
  }

  private Vector<Double> getMultipliedRow(int i, double factor) throws Exception {
    if (factor == 0.0) {
      throw new Exception();
    }

    Vector<Double> row = this.getRow(i);
    for (int j = 0; j < this.nCols; j++) {
      row.set(j, row.get(j) * factor);
    }

    return row;
  }

  public void multiplyRow(int i, double factor) throws Exception {
    this.setRow(i, getMultipliedRow(i, factor));
  }

  public void elementaryRowAdd(int i1, int i2, int factor) throws Exception {
    this.setRow(i1, getMultipliedRow(i2, factor));
  }
}
