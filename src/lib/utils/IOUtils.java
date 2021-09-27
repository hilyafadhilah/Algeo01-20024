package lib.utils;

import java.io.File;
import java.util.Scanner;

import lib.matrix.Matrix;

/**
 * Utilities for I/O, specific to this application.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.4
 * @see java.util.Collection
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
   * @return
   * @throws Exception If input is invalid
   * @see lib.matrix.Matrix#read(Scanner, int, int)
   * @see lib.matrix.Matrix#read(Scanner)
   */
  public static Matrix inputMatrix() throws Exception {
    File file = null;
    Matrix mat = null;
    int nRows = 0, nCols = 0;

    String line = prompt();
    Scanner lineScanner = new Scanner(line);

    try {
      nRows = lineScanner.nextInt();
      nCols = lineScanner.nextInt();
      mat = Matrix.read(stdinScanner, nRows, nCols);
    } catch (Exception e) {
      file = new File(line);
      Scanner scanner = new Scanner(file);
      mat = Matrix.read(scanner);
      scanner.close();
    } finally {
      lineScanner.close();
    }

    return mat;
  }

  /**
   * Display a header with a specified title.
   * 
   * @param title The title
   */
  public static void printHeader(String title) {
    int n = (60 - title.length() - 4) / 2;
    String header = "";

    if (n > 0) {
      String dashes = repeat("=", n);
      header = String.format("\n %s %s %s \n", dashes, title, dashes);
    } else {
      header = '\n' + title + '\n';
    }

    System.out.print(header);
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
