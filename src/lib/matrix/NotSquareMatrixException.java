package lib.matrix;

/**
 * Exception for when <code>Matrix</code> not square in size. Usually thrown
 * when doing computations that requires square matrices.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @since 2021-09-27
 */
public class NotSquareMatrixException extends Exception {
  /**
   * Constructor: Exception message already specified.
   */
  public NotSquareMatrixException() {
    super("Matriks tidak berukuran n x n.");
  }
}
