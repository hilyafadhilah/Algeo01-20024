package lib.system;

/**
 * Exception when a system cannot be solved using a particular method
 * 
 * @author Jundan Haris
 * @version 0.1.0
 * @see LinearSystem
 * @since 2021-09-28
 */
public class CannotSolveException extends Exception {
  /**
   * Available solving methods
   */
  public enum Method {
    CRAMER, INVERSE
  }

  /**
   * Available exception causes
   */
  public enum Cause {
    NON_UNIQUE_SOLUTION, TOO_MANY_EQ
  }

  /**
   * The label of the method
   */
  private String methodLabel;

  /**
   * The cause in message form
   */
  private String causeMsg;

  /**
   * Constructor
   * 
   * @param method The method that is used to solve
   * @param cause  The cause of error
   */
  public CannotSolveException(Method method, Cause cause) {
    super();

    if (method == Method.CRAMER)
      this.methodLabel = "kaidah Cramer";
    else if (method == Method.INVERSE)
      this.methodLabel = "metode invers";

    if (cause == Cause.NON_UNIQUE_SOLUTION)
      this.causeMsg = "SPL memiliki nol atau tak hingga solusi";
    else if (cause == Cause.TOO_MANY_EQ)
      this.causeMsg = "jumlah persamaan lebih dari jumlah peubah";
  }

  public String getMessage() {
    return "Tidak dapat mencari solusi SPL dengan " + methodLabel + " karena " + causeMsg + ".";
  }
}
