package app.utils;

public class InvalidInputException extends Exception {
  public InvalidInputException() {
    super("Input tidak valid.");
  }

  public InvalidInputException(String msg) {
    super(msg.isEmpty() ? "Input tidak valid." : msg);
  }
}
