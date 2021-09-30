package app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A printer that can save its output to a file.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @see java.io.PrintWriter
 * @since 2021-09-30
 */
public class Printer {
  /**
   * Buffer for saving output
   */
  private ArrayList<String> buffer = new ArrayList<>();

  /**
   * Print and save to buffer
   * 
   * @param s String
   * @see java.io.PrintWriter#print(String)
   */
  public void print(String s) {
    System.out.print(s);
    buffer.set(buffer.size() - 1, buffer.get(buffer.size() - 1) + s);
  }

  /**
   * Print and save to buffer
   * 
   * @param x Object
   * @see java.io.PrintWriter#print(Object)
   */
  public void print(Object x) {
    print(String.valueOf(x));
  }

  /**
   * Print line and save to buffer
   * 
   * @param s String
   * @see java.io.PrintWriter#println(String)
   */
  public void println(String s) {
    System.out.println(s);
    buffer.add(s);
  }

  /**
   * Print object using {@link java.io.PrintWriter#println(Object)} and save to
   * buffer
   * 
   * @param x Object
   * @see java.io.PrintWriter#println(Object)
   */
  public void println(Object x) {
    println(String.valueOf(x));
  }

  /**
   * Display a header with a specified title, then save to buffer.
   * 
   * @param title The title
   */
  public void printHeader(String title) {
    String heading = OutputUtils.getHeading(title, OutputUtils.headerPadding);
    System.out.print(heading);

    if (this.buffer.isEmpty()) {
      this.buffer.add(heading.strip() + "\n");
    } else {
      this.buffer.add("\n" + heading.strip() + "\n");
    }
  }

  /**
   * Display a subheader with a specified title, then save to buffer.
   * 
   * @param title The title
   */
  public void printSubheader(String title) {
    String heading = OutputUtils.getHeading(title, OutputUtils.subheaderPadding);
    System.out.print(heading);

    if (this.buffer.isEmpty()) {
      this.buffer.add(heading.strip() + "\n");
    } else {
      this.buffer.add(heading.strip() + "\n");
    }
  }

  /**
   * Prompts the user for a file path then store its buffered output. If the user
   * enters none, it skips ahead.
   * 
   * @throws FileNotFoundException
   * @throws SecurityException
   */
  public void toFile() throws FileNotFoundException, SecurityException {
    System.out.println();
    OutputUtils.printHeader("*** Simpan output ke dalam file? ***");
    System.out.println("Jika ya, masukkan path file. Jika tidak, kosongkan.\n");

    String path = InputUtils.prompt("file>");

    if (!path.strip().isEmpty()) {
      File file = new File(path);
      PrintWriter writer = new PrintWriter(file);

      // Normalize last line
      String lastLine = this.buffer.get(this.buffer.size() - 1);
      char lastChar = lastLine.charAt(lastLine.length() - 1);

      if (lastChar == '\n') {
        this.buffer.set(this.buffer.size() - 1, lastLine.substring(0, lastLine.length() - 1));
      }

      // Write
      for (String line : this.buffer) {
        writer.println(line);
      }

      writer.close();

      OutputUtils.printSubheader("*** File berhasil disimpan ***");
      System.out.println();
    }
  }
}
