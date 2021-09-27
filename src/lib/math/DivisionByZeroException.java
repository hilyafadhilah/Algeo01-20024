package lib.math;

public class DivisionByZeroException extends Exception {
  public DivisionByZeroException() {
    super("Tidak dapat melakukan pembagian terhadap 0.");
  }
}
