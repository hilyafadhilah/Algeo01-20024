package lib.system;

public class CannotComputeException extends Exception {
  public enum Method {
    CRAMER, INVERSE
  }

  public enum Cause {
    NON_UNIQUE_SOLUTION, TOO_MANY_EQ
  }

  private String methodLabel;
  private String causeMsg;

  public CannotComputeException(Method method, Cause cause) {
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
