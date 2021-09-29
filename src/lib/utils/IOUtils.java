package lib.utils;

import java.io.File;
import java.util.Scanner;

import lib.matrix.Matrix;
import lib.matrix.NotSquareMatrixException;
import lib.system.Interpolation;

/**
 * Utilities for I/O, specific to this application.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.4
 * @since 2021-09-26
 */
public class IOUtils {
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
  public static Matrix inputMatrix(boolean isSquare) throws Exception {
    File file = null;
    Matrix mat = null;

    printHeader("Input Matriks", "-");
    System.out.print("\nMasukkan ukuran matriks dalam bilangan bulat, dengan format\n" + " [baris]<spasi>[kolom]\n"
        + "Jika matriks dalam file, masukkan path file tersebut\n\n");

    String line = prompt("matriks>");
    Scanner lineScanner = new Scanner(line);

    try {
      if (!lineScanner.hasNextInt()) {
        throw new ScanFileException();
      }

      int nRows = lineScanner.nextInt();

      if (!lineScanner.hasNextInt()) {
        throw new ScanFileException();
      }

      int nCols = lineScanner.nextInt();

      if (isSquare && nRows != nCols) {
        throw new NotSquareMatrixException();
      }

      System.out.print("\nMasukkan elemen matriks, setiap baris dipisah dengan newline\n"
          + "dan setiap elemen dalam satu baris dipisah dengan spasi.\n\n");

      mat = Matrix.read(stdinScanner, nRows, nCols);
    } catch (ScanFileException e) {
      file = new File(line);
      Scanner scanner = new Scanner(file);
      mat = Matrix.read(scanner);
      scanner.close();

      if (isSquare && !mat.isSquare()) {
        throw new NotSquareMatrixException();
      }
    } finally {
      lineScanner.close();
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
  public static Interpolation inputInterpolation() throws Exception {
    Interpolation intpl = new Interpolation();

    printHeader("Input Interpolasi", "-");
    System.out.print("\nMasukkan jumlah titik dalam bilangan bulat.\n"
        + "Jika titik dalam file, langsung masukkan path file tersebut.\n\n");

    String line = prompt("interpolasi>");
    Scanner lineScanner = new Scanner(line);

    try {
      if (!lineScanner.hasNextInt()) {
        throw new ScanFileException();
      }

      int nPoints = lineScanner.nextInt();

      System.out.print("\nMasukkan titik yang diketahui, setiap titik dipisah dengan newline\n"
          + "dan dalam format \"[x]<spasi>[y]\".\n\n");

      intpl.setPoints(Matrix.read(stdinScanner, nPoints, 2));
    } catch (ScanFileException e) {
      File file = new File(line);
      Scanner scanner = new Scanner(file);

      intpl.setPoints(Matrix.read(scanner));

      scanner.close();
    } finally {
      lineScanner.close();
    }

    double min = intpl.getMinAbscissa();
    double max = intpl.getMaxAbscissa();

    System.out.println("\nMasukkan absis dari nilai yang ingin ditaksir dengan interpolasi,\n"
        + "dengan nilai absis antara " + min + " dan " + max + "\n");
    intpl.setSearchValue(promptDouble());

    return intpl;
  }

  /**
   * Display a header with a specified title and padding.
   * 
   * @param title   Title
   * @param padding Padding character/string
   */
  public static void printHeader(String title, String padding) {
    int n = (60 - title.length() - 4) / 2;
    String header = "";

    if (n > 0) {
      String dashes = repeat(padding, n);
      header = String.format("\n %s %s %s \n", dashes, title, dashes);
    } else {
      header = '\n' + title + '\n';
    }

    System.out.print(header);
  }

  /**
   * Display a header with a specified title.
   * 
   * @param title The title
   */
  public static void printHeader(String title) {
    printHeader(title, "=");
  }

  /**
   * Display a divider
   */
  public static void printDivider() {
    System.out.println(repeat("=", 60));
  }

  /**
   * Repeats a string, <code>n</code> number of times.
   * 
   * @param str The string to be repeated
   * @param n   The number of repetition
   * @return The repeated string
   */
  public static String repeat(String str, int n) {
    String result = "";
    for (int i = 0; i < n; i++) {
      result += str;
    }
    return result;
  }
}
