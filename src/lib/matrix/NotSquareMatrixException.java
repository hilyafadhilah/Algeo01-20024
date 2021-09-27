package lib.matrix;

public class NotSquareMatrixException extends Exception {
  public NotSquareMatrixException() {
    super("Matriks tidak berukuran n x n.");
  }
}
