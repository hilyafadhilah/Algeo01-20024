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
    this.matrix = new Vector<>(nRows);
    for (int i = 0; i < nRows; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = 0; j < nCols; j++) {
        this.matrix.get(i).add(null);
      }
    }
  }

  public Matrix() {
    this.nRows = 0;
    this.nCols = 0;
    this.matrix = new Vector<>();
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

  public static Matrix read(Scanner scanner, int nRows, int nCols) {
    Matrix mat = new Matrix(nRows, nCols);
    for (int i = 0; i < nRows; i++) {
      String line = scanner.nextLine();
      Scanner lineScanner = new Scanner(line);

      for (int j = 0; j < nCols; j++) {
        mat.set(i, j, lineScanner.nextDouble());
      }

      lineScanner.close();
    }

    return mat;
  }

  public static Matrix read(Scanner scanner) throws Exception {
    Matrix mat = new Matrix();
    int nRows = 0;
    int nCols = 0;

    int i = 0;
    while (scanner.hasNextLine()) {
      mat.matrix.add(new Vector<>());

      String line = scanner.nextLine();
      Scanner strScanner = new Scanner(line);

      int j = 0;
      while (strScanner.hasNextDouble()) {
        Double value = strScanner.nextDouble();
        mat.matrix.get(i).add(value);
        j++;
      }

      if (nCols == 0 && j > 0) {
        nCols = j;
      } else if (nCols != j) {
        strScanner.close();
        throw new Exception();
      }

      strScanner.close();
      i++;
    }

    mat.nRows = nRows;
    mat.nCols = nCols;
    return mat;
  }

  public boolean isNull() {
    return this.nRows == 0 && this.nCols == 0;
  }

  public void swapRows(int i1, int i2) throws Exception {
    Vector<Double> tmp = this.getRow(i1);
    this.setRow(i1, this.getRow(i2));
    this.setRow(i2, tmp);
  }

  private Vector<Double> getRowCopy(int i) {
    Vector<Double> row = new Vector<>();
    for (int j = 0; j < this.nCols; j++) {
      row.add(this.get(i, j));
    }

    return row;
  }

  private Vector<Double> getMultipliedRow(int i, double factor) throws Exception {
    Vector<Double> row = this.getRowCopy(i);
    for (int j = 0; j < this.nCols; j++) {
      row.set(j, row.get(j) * factor);
    }

    return row;
  }

  public void multiplyRow(int i, double factor) throws Exception {
    this.setRow(i, getMultipliedRow(i, factor));
  }

  public void divideRow(int i, double k) throws Exception {
    if (k == 0.0) {
      throw new Exception();
    }

    Vector<Double> row = this.getRow(i);
    for (int j = 0; j < this.nCols; j++) {
      row.set(j, row.get(j) / k);
    }
  }

  public void elementaryRowAdd(int i1, int i2, int factor) throws Exception {
    this.setRow(i1, getMultipliedRow(i2, factor));
  }
}
