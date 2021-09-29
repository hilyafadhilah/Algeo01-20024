package lib.utils;

/**
 * Utilities for output, specific to this application.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @since 2021-09-30
 */
public class OutputUtils {
  /**
   * Padding for header
   */
  public static final String headerPadding = "=";

  /**
   * Padding for subheader
   */
  public static final String subheaderPadding = "-";

  /**
   * Get a heading string with a specified title and padding.
   * 
   * @param title  Title
   * @param padder Padding character/string
   */
  public static String getHeading(String title, String padder) {
    int n = (60 - title.length() - 4) / 2;
    String header = "";

    if (n > 0) {
      String padding = repeat(padder, n);
      header = String.format("\n %s %s %s \n", padding, title, padding);
    } else {
      header = '\n' + title + '\n';
    }

    return header;
  }

  /**
   * Display a header with a specified title.
   * 
   * @param title The title
   */
  public static void printHeader(String title) {
    System.out.println(getHeading(title, headerPadding));
  }

  /**
   * Display a subheader with a specified title.
   * 
   * @param title The title
   */
  public static void printSubheader(String title) {
    System.out.println(getHeading(title, subheaderPadding));
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
