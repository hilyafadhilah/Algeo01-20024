package lib.utils;

import java.util.Scanner;

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
