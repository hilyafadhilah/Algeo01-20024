package lib.utils;

import java.io.File;
import java.util.Scanner;

import lib.matrix.Matrix;

public class IOUtils {
  protected static Scanner stdinScanner;

  public static void init() {
    stdinScanner = new Scanner(System.in);
  }

  public static void close() {
    stdinScanner.close();
  }

  public static Scanner getStdinScanner() {
    return stdinScanner;
  }

  public static String prompt(String query) {
    System.out.print(query + " ");
    return stdinScanner.nextLine();
  }

  public static String prompt() {
    return stdinScanner.nextLine();
  }

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

  public static String repeat(String str, int n) {
    String result = "";
    for (int i = 0; i < n; i++) {
      result += str;
    }
    return result;
  }
}
