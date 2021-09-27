package lib.matrix;

import java.util.Scanner;
import java.util.Vector;

import lib.math.DivisionByZeroException;

/**
 * <p>
 * Basic matrix class for linear algebra computation. This class is specifically
 * designed for this program.
 * </p>
 * 
 * <p>
 * Conventions: As with mathemathical matrix, the size of a matrix is denoted as
 * <code>m</code> ✕ <code>n</code> with <code>m</code> being the number of rows
 * and <code>n</code> being the number of columns. Each element has a "location"
 * of <code>(i,j)</code> with <code>i</code> being the row and <code>j</code>
 * being the column of the element in respect to the matrix.
 * </p>
 * 
 * <p>
 * Notable difference with mathematical matrix is the starting element of this
 * matrix is located at index <code>(0,0)</code> not <code>(1,1)</code> and the
 * endmost element is located at index <code>(m-1,n-1)</code> not
 * <code>(m,n)</code>.
 * </p>
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.3
 * @since 2021-09-24
 */
public class Matrix {
  /**
   * Number of rows in the matrix. Literal: <code>(maximum index + 1)<code>.
   */
  protected int nRows;

  /**
   * Number of columns in the matrix. Literal: <code>(maximum index + 1)</code>.
   */
  protected int nCols;

  /**
   * The matrix itself.
   */
  protected Vector<Vector<Double>> matrix;

  /**
   * Modifiable default value for each element of new matrix.
   */
  public static Double defaultValue = 0.0;

  /**
   * Constructor with defined number of rows and columns.
   * 
   * @param nRows Number of rows in this matrix
   * @param nCols Number of columns in this matrix
   */
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

  /**
   * Constructor with undefined number of rows and columns. Creates a null matrix.
   * 
   * @see #isNull()
   */
  public Matrix() {
    this.nRows = 0;
    this.nCols = 0;
    this.matrix = new Vector<>();
  }

  /**
   * Getter for row length.
   * 
   * @return Number of rows in the matrix.
   */
  public int getNRows() {
    return this.nRows;
  }

  /**
   * Getter for column length;
   * 
   * @return Number of columns in the matrix.
   */
  public int getNCols() {
    return this.nCols;
  }

  /**
   * Getter for an element.
   * 
   * @param i Row of element
   * @param j Column of element
   * @return Element
   */
  public double get(int i, int j) {
    return this.matrix.get(i).get(j);
  }

  /**
   * Setter for an element.
   * 
   * @param i     Row of element
   * @param j     Column of element
   * @param value The intended value for element
   */
  public void set(int i, int j, double value) {
    this.matrix.get(i).set(j, value);
  }

  /**
   * Set all elements from a specified position until a specified length with a
   * value.
   * 
   * @param value   The intended value for all elements
   * @param iStart  Starting row
   * @param jStart  Starting column
   * @param iLength Length of rows affected
   * @param jLength Length of columns affected
   */
  public void setAll(double value, int iStart, int jStart, int iLength, int jLength) {
    for (int i = iStart; i < iLength; i++) {
      for (int j = jStart; j < jLength; j++) {
        this.set(i, j, value);
      }
    }
  }

  /**
   * Set all elements from a specified position until the end of the matrix with a
   * value.
   * 
   * @param value  The intended value for all elements
   * @param iStart Starting row
   * @param jStart Starting column
   */
  public void setAll(double value, int iStart, int jStart) {
    setAll(value, iStart, jStart, this.nRows - iStart, this.nCols - jStart);
  }

  /**
   * Set all elements of the matrix with a value.
   * 
   * @param value The intended value for all elements
   */
  public void setAll(double value) {
    setAll(value, 0, 0, this.nRows, this.nCols);
  }

  /**
   * Increase the length of rows by adding at the end of this matrix.
   * 
   * @param length The number of rows to be added
   */
  public void addRows(int length) {
    for (int i = this.nRows; i < this.nRows + length; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = 0; j < nCols; j++) {
        this.matrix.get(i).add(Matrix.defaultValue);
      }
    }
    this.nRows += length;
  }

  /**
   * Increase the length of columns by adding at the end of this matrix.
   * 
   * @param length The number of columns to be added
   */
  public void addCols(int length) {
    for (int i = 0; i < this.nRows; i++) {
      this.matrix.add(new Vector<>(nCols));
      for (int j = this.nCols; j < nCols + length; j++) {
        this.matrix.get(i).add(Matrix.defaultValue);
      }
    }
    this.nCols += length;
  }

  /**
   * Getter for a specified row i.
   * 
   * @param i Row index
   * @return The row at position i as raw vector
   */
  private Vector<Double> getRow(int i) {
    return this.matrix.get(i);
  }

  /**
   * Setter for a specified row i.
   * 
   * @param i     Row index
   * @param value The new value of row i
   */
  private void setRow(int i, Vector<Double> value) {
    if (value.size() == this.nCols) {
      this.matrix.set(i, value);
    }
  }

  /**
   * <p>
   * Read a matrix value from a specified input scanner with defined row and
   * column length. Each line in the input stream must consists of
   * <code>nCols</code> number of valid double values separated by whitespace.
   * </p>
   * 
   * <p>
   * Example: A 2x3 matrix
   * 
   * <pre>
   * 1.0 2.0 3.0
   * 4.0 5.0 6.0
   * </pre>
   * </p>
   * 
   * @param scanner The input scanner
   * @param nRows   Number of rows to be read
   * @param nCols   Number of columns to be read
   * @return New <code>Matrix</code> with specified size
   * @throws Exception If read fails (likely invalid matrix input)
   */
  public static Matrix read(Scanner scanner, int nRows, int nCols) throws Exception {
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

  /**
   * Read a matrix value from a specified input scanner with unspecified size.
   * 
   * @param scanner The input scanner to be read from
   * @return New valid <code>Matrix</code>
   * @throws Exception If read fails (likely invalid matrix input)
   */
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

  /**
   * Outputs the matrix to a string. Each column separated with a space (no ending
   * space) and each row separated with newline (no ending newline).
   * 
   * Example:
   * 
   * <pre>
   * 1.0 1.0
   * 1.0 0.0
   * </pre>
   */
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

  /**
   * Checks if this <code>Matrix</code> is a null <code>Matrix</code>. A null
   * <code>Matrix</code> is one which has zero rows and columns.
   * 
   * @return True if this <code>Matrix</code> is null
   */
  public boolean isNull() {
    return this.nRows == 0 && this.nCols == 0;
  }

  /**
   * Checks if this <code>Matrix</code> is square in size.
   * 
   * @return True if row length equal column length
   */
  public boolean isSquare() {
    return this.nCols == this.nRows;
  }

  /**
   * Checks if a specified value exists within a column.
   * 
   * @param j   Column index
   * @param val The value to be compared
   * @return True if <code>val</code> exists in column <code>j</code>
   */
  public boolean isValueInCol(int j, double val) {
    for (int i = 0; i < this.nRows; i++) {
      if (this.get(i, j) == val) {
        return true;
      }
    }
    return false;
  }

  /**
   * Swap two rows at index <code>i1</code> and <code>i2</code>.
   * 
   * @param i1 First row index
   * @param i2 The other row index
   */
  public void swapRows(int i1, int i2) {
    Vector<Double> tmp = this.getRow(i1);
    this.setRow(i1, this.getRow(i2));
    this.setRow(i2, tmp);
  }

  /**
   * Get a raw copy of a row at index <code>i</code>.
   * 
   * @param i Row index
   * @return Raw copy of row <code>i</code>
   */
  private Vector<Double> getRowCopy(int i) {
    Vector<Double> row = new Vector<>();
    for (int j = 0; j < this.nCols; j++) {
      row.add(this.get(i, j));
    }

    return row;
  }

  /**
   * Get a raw copy of a row at index <code>i</code>, multiplied with
   * <code>factor</code>.
   * 
   * @param i      Row index
   * @param factor Multiplication factor
   * @return Raw copy of row <code>i</code> multiplied with <code>factor</code>
   */
  private Vector<Double> getMultipliedRow(int i, double factor) {
    Vector<Double> row = this.getRowCopy(i);
    for (int j = 0; j < this.nCols; j++) {
      row.set(j, row.get(j) * factor);
    }

    return row;
  }

  /**
   * Multiply row <code>i</code> n this matrix with <code>factor</code>.
   * 
   * @param i      Row index
   * @param factor Multiplication factor
   * @see #getMultipledRow(int, double)
   * @see #setRow(int, Vector)
   */
  public void multiplyRow(int i, double factor) {
    this.setRow(i, getMultipliedRow(i, factor));
  }

  /**
   * Divide row <code>i</code> in this matrix with <code>k</code>.
   * 
   * @param i Row index
   * @param k Divider
   * @throws DivisionByZeroException If <code>k</code> is equal to zero
   */
  public void divideRow(int i, double k) throws DivisionByZeroException {
    if (k == 0.0) {
      throw new DivisionByZeroException();
    }

    for (int j = 0; j < this.nCols; j++) {
      this.matrix.get(i).set(j, this.matrix.get(i).get(j) / k);
    }
  }

  /**
   * Elementary row operation: Add elements in row
   * <code>i1<code> with elements in row <code>i2</code> multiplied with
   * <code>factor</code>.
   * 
   * @param i1     Row index to be added to
   * @param i2     Row index to be the addend
   * @param factor Multiplying factor of row <code>i2</code>
   */
  public void elementaryRowAdd(int i1, int i2, double factor) {
    Vector<Double> mulRow = getMultipliedRow(i2, factor);
    for (int j = 0; j < this.nCols; j++) {
      this.matrix.get(i1).set(j, this.get(i1, j) + mulRow.get(j));
    }
  }

  /**
   * Creates a clone of this <code>Matrix</code>.
   * 
   * @return A clone of this <code>Matrix</code>.
   */
  public Matrix copy() {
    Matrix m = new Matrix(this.nRows, this.nCols);
    for (int i = 0; i < this.nRows; i++) {
      for (int j = 0; j < this.nCols; j++) {
        m.set(i, j, this.get(i, j));
      }
    }
    return m;
  }

  /**
   * Creates a submatrix of this <code>Matrix</code> from a specified position
   * with a specified length.
   * 
   * @param iStart  Starting row index
   * @param jStart  Starting column index
   * @param iLength Row length of submatrix
   * @param jLength Column length of submatrix
   * @return The submatrix
   */
  public Matrix subMatrix(int iStart, int jStart, int iLength, int jLength) {
    Matrix subMatrix = new Matrix(iLength, jLength);
    for (int i = 0; i < iLength; i++) {
      for (int j = 0; j < jLength; j++) {
        subMatrix.set(i, j, this.get(i + iStart, j + jStart));
      }
    }
    return subMatrix;
  }

  /**
   * Creates a submatrix of this <code>Matrix</code> from a specified position
   * until the endmost element.
   * 
   * @param iStart Starting row index
   * @param jStart Starting column index
   * @return The submatrix
   */
  public Matrix subMatrix(int iStart, int jStart) {
    return subMatrix(iStart, jStart, this.nRows - iStart, this.nCols - jStart);
  }

  /**
   * Creates a cofactor entry matrix of element (iC,jC) in this
   * <code>Matrix</code>.
   * 
   * @param iC Row index
   * @param jC Column index
   * @return Cofactor entry matrix
   * @throws NotSquareMatrixException If the matrix is not square in size
   */
  public Matrix cofactor(int iC, int jC) throws NotSquareMatrixException {
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

  /**
   * Find the index of row in this <code>Matrix</code> with a pivot in the
   * specified column <code>j</code>, starting from row <code>iStart</code>.
   * 
   * @param iStart Starting row index to look for
   * @param j      Column to look for
   * @return The index of row. Can return <code>iStart</code> if
   *         (<code>iStart</code>, <code>j</code>) is non-zero. Returns
   *         <code>-1</code> if not found.
   * @see #toEchelon()
   */
  private int pivotRowIndex(int iStart, int j) {
    for (int i = iStart; i < this.nRows; i++) {
      if (this.get(i, j) != 0.0) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Creates a copy of this <code>Matrix</code> in row echelon form.
   * 
   * @return Acopy of this <code>Matrix</code> in row echelon form
   * @throws DivisionByZeroException In the very impossible case that a division
   *                                 by zero occurs
   */
  public Matrix toEchelon() throws DivisionByZeroException {
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

  /**
   * Creates a copy of this <code>Matrix</code> in reduced row echelon form.
   * 
   * @return Acopy of this <code>Matrix</code> in reduced row echelon form
   * @throws DivisionByZeroException In the very impossible case that a division
   *                                 by zero occurs
   */
  public Matrix toReducedEchelon() throws DivisionByZeroException {
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
