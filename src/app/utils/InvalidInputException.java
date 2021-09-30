package app.utils;

/**
 * Exception for invalid input
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.0
 * @since 2021-09-30
 */
public class InvalidInputException extends Exception {
  public InvalidInputException() {
    super("Input tidak valid.");
  }

  public InvalidInputException(String msg) {
    super(msg.isEmpty() ? "Input tidak valid." : msg);
  }
}
