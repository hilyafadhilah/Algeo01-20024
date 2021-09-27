package lib.matrix;

import java.util.Scanner;
import java.util.Vector;

import lib.math.DivisionByZeroException;

public class Matrix {
  protected int nRows;
  protected int nCols;
  protected Vector<Vector<Double>> matrix;

  public static Double defaultValue = 0.0;

  public Matrix(int nRows, int nCols) {
    this.nRows = nRows;
    this.nCols = nCols;
    this.matrix = new Vector<>(nRows);
    for (int i = 0; i < nRows; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = 0; j < nCols; j++) {
        this.matrix.get(i).add(Matrix.defaultValue);
      }
    }
  }

  public Matrix() {
    this.nRows = 0;
    this.nCols = 0;
    this.matrix = new Vector<>();
  }

  public int getNRows() {
    return this.nRows;
  }

  public int getNCols() {
    return this.nCols;
  }

  public double get(int i, int j) {
    return this.matrix.get(i).get(j);
  }

  public void set(int i, int j, double value) {
    this.matrix.get(i).set(j, value);
  }

  public void setAll(double value, int iStart, int jStart, int iLength, int jLength) {
    for (int i = iStart; i < iLength; i++) {
      for (int j = jStart; j < jLength; j++) {
        this.set(i, j, value);
      }
    }
  }

  public void setAll(double value, int iStart, int jStart) {
    setAll(value, iStart, jStart, this.nRows - iStart, this.nCols - jStart);
  }

  public void setAll(double value) {
    setAll(value, 0, 0, this.nRows, this.nCols);
  }

  public void addRows(int length) {
    for (int i = this.nRows; i < this.nRows + length; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = 0; j < nCols; j++) {
        this.matrix.get(i).add(Matrix.defaultValue);
      }
    }
    this.nRows += length;
  }

  public void addCols(int length) {
    for (int i = 0; i < this.nRows; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = this.nCols; j < nCols + length; j++) {
        this.matrix.get(i).add(Matrix.defaultValue);
      }
    }
    this.nCols += length;
  }

  private Vector<Double> getRow(int i) {
    return this.matrix.get(i);
  }

  private void setRow(int i, Vector<Double> value) {
    if (value.size() == this.nCols) {
      this.matrix.set(i, value);
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
        throw new InvalidMatrixException();
      }

      strScanner.close();
      i++;
    }

    mat.nRows = i;
    mat.nCols = nCols;
    return mat;
  }

  public String toString() {
    String output = "";

    for (int i = 0; i < this.nRows; i++) {
      for (int j = 0; j < this.nCols; j++) {
        output += this.get(i, j);

        if (j + 1 != this.nCols) {
          output += " ";
        }
      }

      if (i + 1 != this.nRows) {
        output += "\n";
      }
    }

    return output;
  }

  public boolean isNull() {
    return this.nRows == 0 && this.nCols == 0;
  }

  public boolean isSquare() {
    return this.nCols == this.nRows;
  }

  public void swapRows(int i1, int i2) {
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

  private Vector<Double> getMultipliedRow(int i, double factor) {
    Vector<Double> row = this.getRowCopy(i);
    for (int j = 0; j < this.nCols; j++) {
      row.set(j, row.get(j) * factor);
    }

    return row;
  }

  public void multiplyRow(int i, double factor) {
    this.setRow(i, getMultipliedRow(i, factor));
  }

  public void divideRow(int i, double k) throws Exception {
    if (k == 0.0) {
      throw new DivisionByZeroException();
    }

    for (int j = 0; j < this.nCols; j++) {
      this.matrix.get(i).set(j, this.matrix.get(i).get(j) / k);
    }
  }

  public void elementaryRowAdd(int i1, int i2, double factor) {
    Vector<Double> mulRow = getMultipliedRow(i2, factor);
    for (int j = 0; j < this.nCols; j++) {
      this.matrix.get(i1).set(j, this.get(i1, j) + mulRow.get(j));
    }
  }

  public Matrix copy() {
    Matrix m = new Matrix(this.nRows, this.nCols);
    for (int i = 0; i < this.nRows; i++) {
      for (int j = 0; j < this.nCols; j++) {
        m.set(i, j, this.get(i, j));
      }
    }
    return m;
  }

  public Matrix subMatrix(int iStart, int jStart, int iLength, int jLength) {
    Matrix subMatrix = new Matrix(iLength, jLength);
    for (int i = 0; i < iLength; i++) {
      for (int j = 0; j < jLength; j++) {
        subMatrix.set(i, j, this.get(i + iStart, j + jStart));
      }
    }
    return subMatrix;
  }

  public Matrix subMatrix(int iStart, int jStart) {
    return subMatrix(iStart, jStart, this.nRows - iStart, this.nCols - jStart);
  }

  public Matrix cofactor(int iC, int jC) throws Exception {
    if (!this.isSquare()) {
      throw new NotSquareMatrixException();
    }

    int size = this.getNRows();
    Matrix mCof = new Matrix(size - 1, size - 1);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i != iC && j != jC) {
          mCof.set((i < iC ? i : i - 1), (j < jC ? j : j - 1), this.get(i, j));
        }
      }
    }

    return mCof;
  }

  private int pivotRowIndex(int iStart, int j) {
    for (int i = iStart; i < this.nRows; i++) {
      if (this.get(i, j) != 0.0) {
        return i;
      }
    }

    return -1;
  }

  public boolean isValueInCol(int j, double val) {
    for (int i = 0; i < this.nRows; i++) {
      if (this.get(i, j) == val) {
        return true;
      }
    }
    return false;
  }

  public Matrix toEchelon() throws Exception {
    Matrix result = this.copy();

    int i = 0;
    for (int j = 0; j < this.nCols && i < this.nRows; j++) {
      int pivotRow = result.pivotRowIndex(i, j);

      if (pivotRow != -1) {
        result.swapRows(i, pivotRow);
        result.divideRow(i, result.get(i, j));

        for (int k = i + 1; k < this.nRows; k++) {
          result.elementaryRowAdd(k, i, -result.get(k, j));
        }

        i++;
      }

    }

    return result;
  }

  public Matrix toReducedEchelon() throws Exception {
    Matrix result = this.copy().toEchelon();
    Vector<int[]> pivots = new Vector<>();

    for (int i = result.nRows - 1; i >= 0; i--) {
      for (int j = 0; j < result.nCols; j++) {
        if (result.get(i, j) == 1) {
          pivots.add(new int[] { i, j });
          break;
        }
      }
    }

    for (int i = 0; i < pivots.size() - 1; i++) {
      for (int j = i + 1; j < pivots.size(); j++) {
        result.elementaryRowAdd(pivots.get(j)[0], pivots.get(i)[0], -result.get(pivots.get(j)[0], pivots.get(i)[1]));
      }
    }

    return result;
  }
}
