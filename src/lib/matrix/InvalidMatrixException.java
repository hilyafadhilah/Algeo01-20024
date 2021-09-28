package lib.matrix;

/**
 * Invalid <code>Matrix</code> exception. Usually thrown at input.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @since 2021-09-27
 */
public class InvalidMatrixException extends Exception {
  /**
   * Constructor: Exception message already specified.
   */
  public InvalidMatrixException() {
    super("Matriks tidak valid.");
  }
}
