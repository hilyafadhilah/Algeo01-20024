package app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import app.interpolation.Interpolation;
import lib.matrix.Matrix;

/**
 * Utilities for input, specific to this application.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.4
 * @since 2021-09-26
 */
public class InputUtils {
  /**
   * <code>Scanner</code> of <code>stdin</code>.
   * 
   * @see java.util.Scanner
   */
  protected static Scanner stdinScanner;

  /**
   * Initializes I/O. Opens <code>stdin</code> scanner.
   * 
   * @see #stdinScanner
   */
  public static void init() {
    stdinScanner = new Scanner(System.in);
  }

  /**
   * Cleans up I/O. Closes <code>stdin</code> scanner.
   * 
   * @see #init()
   */
  public static void close() {
    stdinScanner.close();
  }

  /**
   * Getter for <code>stdinScanner</code>.
   * 
   * @return <code>stdin</code> scanner instance
   * @see #stdinScanner
   */
  public static Scanner getStdinScanner() {
    return stdinScanner;
  }

  /**
   * Inputs from stdin with a message.
   * 
   * @param query Message to be shown. No space at the end required.
   * @return The user input
   */
  public static String prompt(String query) {
    System.out.print(query + " ");
    return stdinScanner.nextLine();
  }

  /**
   * Inputs from stdin without a message
   * 
   * @return The user input
   */
  public static String prompt() {
    return stdinScanner.nextLine();
  }

  public static double promptDouble() {
    double value = stdinScanner.nextDouble();
    stdinScanner.nextLine();
    return value;
  }

  /**
   * <p>
   * Asks the user to input a <code>Matrix</code>.
   * </p>
   * 
   * <p>
   * First, this function assumes that the user inputs two integers separated by
   * whitespace in a line, which denotes the row and column length of the matrix.
   * If it successfully does that, then it tries to input matrix from stdin using
   * the specified size.
   * </p>
   * 
   * <p>
   * If it fails to read two integers, it assumes that the whole line is a
   * filename. Then it tries to read the file.
   * </p>
   * 
   * <p>
   * If it fails to read the file, an exception will be thrown automatically.
   * </p>
   * 
   * @param boolean Whether the matrix must be square
   * @return The matrix from user input
   * @throws Exception If input is invalid
   * @see lib.matrix.Matrix#read(Scanner, int, int)
   * @see lib.matrix.Matrix#read(Scanner)
   */
  public static Matrix inputMatrix(boolean isSquare) throws InvalidInputException {
    File file = null;
    Matrix mat = null;

    OutputUtils.printSubheader("Input Matriks");
    System.out.print(
        "\nMasukkan ukuran matriks dalam bilangan bulat,\n" + "dengan format \"[jumlah baris]<spasi>[jumlah kolom]\"\n"
            + "Jika matriks dalam file, masukkan path file tersebut\n\n");

    try {
      String line = prompt("matriks>");
      Scanner lineScanner = new Scanner(line);

      try {
        if (line.strip().isEmpty()) {
          throw new InvalidInputException("Input kosong.");
        }

        if (!lineScanner.hasNextInt()) {
          throw new ScanFileException();
        }

        int nRows = lineScanner.nextInt();

        if (!lineScanner.hasNextInt()) {
          throw new ScanFileException();
        }

        int nCols = lineScanner.nextInt();

        if (isSquare && nRows != nCols) {
          throw new InvalidInputException("Matriks harus berukuran nxn.");
        }

        System.out.print("\nMasukkan elemen matriks, setiap baris dipisah dengan newline\n"
            + "dan setiap elemen dalam satu baris dipisah dengan spasi.\n\n");

        mat = Matrix.read(stdinScanner, nRows, nCols);
      } catch (ScanFileException e) {
        try {
          System.out.println("test");
          file = new File(line);
          Scanner scanner = new Scanner(file);
          mat = Matrix.read(scanner);
          scanner.close();
        } catch (FileNotFoundException e2) {
          throw new InvalidInputException("File `" + line + "` tidak dapat ditemukan.");
        }

        if (isSquare && !mat.isSquare()) {
          throw new InvalidInputException("Matriks harus berukuran nxn.");
        }
      } finally {
        lineScanner.close();
      }
    } catch (Exception e) {
      throw new InvalidInputException(e.getMessage());
    }

    return mat;
  }

  /**
   * Input a <code>Matrix</code> that doesn't have to be square. Syntatic sugar
   * for inputMatrix(false).
   * 
   * @return The matrix from user input
   * @throws Exception If input is invalid
   * @see {@link #inputMatrix(boolean)}
   */
  public static Matrix inputMatrix() throws Exception {
    return inputMatrix(false);
  }

  /**
   * <p>
   * Asks the user to input an <code>Interpolation</code>.
   * </p>
   * 
   * <p>
   * First, this function assumes that the user inputs an integer which represents
   * the number of data points. If it successfully does that, then it tries to
   * input an Nx2 matrix from stdin using the specified size.
   * </p>
   * 
   * <p>
   * If it fails to read two integers, it assumes that the whole line is a
   * filename. Then it tries to read the file.
   * </p>
   * 
   * <p>
   * If it fails to read the file, an exception will be thrown automatically.
   * </p>
   * 
   * @return
   * @throws Exception
   */
  public static Interpolation inputInterpolation() throws InvalidInputException {
    Interpolation intpl = new Interpolation();

    OutputUtils.printSubheader("Input Interpolasi");
    System.out.print("\nMasukkan jumlah titik dalam bilangan bulat.\n"
        + "Jika titik dalam file, langsung masukkan path file tersebut.\n\n");

    try {
      String line = "";
      Scanner lineScanner = null;

      try {
        line = prompt("interpolasi>");
        lineScanner = new Scanner(line);

        if (line.strip().isEmpty()) {
          throw new InvalidInputException("Input kosong.");
        }

        if (!lineScanner.hasNextInt()) {
          throw new ScanFileException();
        }

        int nPoints = lineScanner.nextInt();

        System.out.print("\nMasukkan titik yang diketahui, setiap titik dipisah dengan newline\n"
            + "dan dalam format \"[x]<spasi>[y]\".\n\n");

        intpl.setPoints(Matrix.read(stdinScanner, nPoints, 2));
      } catch (ScanFileException e) {
        try {
          File file = new File(line);
          Scanner scanner = new Scanner(file);
          intpl.setPoints(Matrix.read(scanner));
          scanner.close();
        } catch (FileNotFoundException e2) {
          throw new InvalidInputException("File `" + line + "` tidak dapat ditemukan");
        }
      } finally {
        lineScanner.close();
      }

      double min = intpl.getMinAbscissa();
      double max = intpl.getMaxAbscissa();

      System.out.println("\nMasukkan absis dari nilai yang ingin ditaksir dengan interpolasi,\n"
          + "dengan nilai absis antara " + min + " dan " + max + "\n");
      intpl.setSearchValue(promptDouble());
    } catch (Exception e) {
      throw new InvalidInputException(e.getMessage());
    }

    return intpl;
  }

  /**
   * <p>
   * Asks the user to input regression data and return it as <code>Matrix</code>.
   * </p>
   * 
   * <p>
   * First, this function assumes that the user inputs two integers separated by
   * whitespace in a line, which denotes the number of data samples and variables.
   * If it successfully does that, then it tries to input matrix from stdin using
   * the specified size.
   * </p>
   * 
   * <p>
   * If it fails to read two integers, it assumes that the whole line is a
   * filename. Then it tries to read the file.
   * </p>
   * 
   * <p>
   * If it fails to read the file, an exception will be thrown automatically.
   * </p>
   * 
   * @return The matrix of data regression from user input
   * @throws Exception If input is invalid
   * @see #inputMatrix(boolean)
   */
  public static Matrix inputRegression() throws InvalidInputException {
    Matrix mat = null;

    OutputUtils.printSubheader("Input Data Regresi");
    System.out.print("\nMasukkan jumlah sampel dan variabel bebas dalam bilangan bulat,\n"
        + "dengan format \"[jumlah sampel]<spasi>[jumlah variabel]\"\n"
        + "Jika data regresi dalam file, masukkan path file tersebut\n\n");

    try {
      String line = prompt("regresi>");
      Scanner lineScanner = new Scanner(line);

      try {
        if (line.strip().isEmpty()) {
          throw new InvalidInputException("Input kosong.");
        }

        if (!lineScanner.hasNextInt()) {
          throw new ScanFileException();
        }

        int nRows = lineScanner.nextInt();

        if (!lineScanner.hasNextInt()) {
          throw new ScanFileException();
        }

        int nCols = lineScanner.nextInt() + 1;

        System.out.print("\nMasukkan data regresi. Setiap data berada pada satu baris.\n" + "Setiap baris berisi nilai "
            + (nCols - 1) + " variabel bebas diikuti nilai variabel hasil,\n"
            + "dengan setiap variabel dipisah dengan spasi.\n\n");

        mat = Matrix.read(stdinScanner, nRows, nCols);
      } catch (ScanFileException e) {
        try {
          File file = new File(line);
          Scanner scanner = new Scanner(file);
          mat = Matrix.read(scanner);
          scanner.close();
        } catch (FileNotFoundException e2) {
          throw new InvalidInputException("File `" + line + "` tidak dapat ditemukan.");
        }
      } finally {
        lineScanner.close();
      }
    } catch (Exception e) {
      throw new InvalidInputException(e.getMessage());
    }

    return mat;
  }
}
