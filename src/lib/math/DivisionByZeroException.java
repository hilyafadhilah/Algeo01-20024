package lib.math;

/**
 * Division by zero exception.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @since 2021-09-27
 */
public class DivisionByZeroException extends Exception {
  /**
   * Constructor: Exception message already specified.
   */
  public DivisionByZeroException() {
    super("Tidak dapat melakukan pembagian terhadap 0.");
  }
}
