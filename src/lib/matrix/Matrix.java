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

  public Matrix copy() {
    Matrix m = new Matrix(this.nRows, this.nCols);
    for (int i = 0; i < nRows; i++) {
      for (int j = 0; j < nCols; j++) {
        mat.set(i, j, this.get(i, j));
      }
    }
    return m;
  }

  private int nonZeroRow(int i, int j) {
    boolean isZero = true;
    while (i < this.nRows && isZero) {
      if (this.get(i,j) != 0) {
        isZero = false;
      } else {
        i++;
      }
    }
    return i;
  }

  public boolean checkValCol(int j, double val) {
    boolean ada = false;
    for (int i = 0; i < this.nRows && !ada; i++) {
      if (this.get(i,j) == val) {
        ada = true;
      }
    }
    return ada;
  }

  public Matrix toEchelon() {
    Matrix result = this.copy();
    int i = 0;
    for (int j = 0; j < this.nCols && i < this.nRows; j++) {
      int row = this.nonZeroRow(i, j);
      if (row < this.nRows) {
        result.swapRows(i, row);
        result.divideRow(i, result.get(i,j));
        for (int k = i + 1; k < this.nRows; k++) {
          result.elementaryRowAdd(k, i, -result.get(k,j));
        }
        i++;
      }
    }
    return result;
  }

  public Matrix toReducedEchelon() {
    Matrix result = this.copy().toEchelon();
    Vector<int[]> index1utama;
    for (int i = result.nRows-1; i >= 0; i--) {
      for (j = 0; j < result.nCols; j++) {
        if (result.get(i,j) == 1) {
          index1utama.add({i,j});
          break;
        }
      }
    }
    for (int i = 0; i < index1utama.size()-1; i++) {
      for (int j = i + 1; j < index1utama.size(); j++) {
        result.elementaryRowAdd(index1utama.get(j)[0], index1utama.get(i)[0], result.get(index1utama.get(j)[0], index1utama.get(j)[1]));
      }
    }
    return result;
  }
}
