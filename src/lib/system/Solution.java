package lib.system;

import java.util.HashMap;

/**
 * Linear equation system solver
 * 
 * @author Hilya Fadhilah Imania
 * @version 0.1.0
 * @see lib.system.LinearSystem
 * @since 2021-09-30
 */
public class Solution {
  /**
   * Constant value
   */
  public double constant = 0.0;

  /**
   * Parameters value
   */
  public HashMap<String, Double> parameters = new HashMap<>();

  /**
   * Constructor with only constant value
   * 
   * @param constant The constant value of this solution
   */
  public Solution(double constant) {
    this.constant = constant;
  }

  /**
   * Constructor with only parameter name
   * 
   * @param param Parameter name
   */
  public Solution(String param) {
    this.parameters.put(param, 1.0);
  }

  /**
   * Constructor with parameter name and coefficient
   * 
   * @param param Parameter name
   * @param coeff Coefficient value
   */
  public Solution(String param, double coeff) {
    this.parameters.put(param, coeff);
  }

  /**
   * Add parameter and the coefficient
   * 
   * @param param Parameter name
   * @param coeff Coefficient value
   */
  public void add(String param, double coeff) {
    this.parameters.put(param, coeff);
  }

  /**
   * Get a parameter coefficient
   * 
   * @param param Parameter name
   */
  public double get(String param) {
    return this.parameters.get(param);
  }

  /**
   * Whether this solution has parameters or not
   * 
   * @return True if it has
   */
  public boolean hasParams() {
    return !this.parameters.isEmpty();
  }

  /**
   * Converts value to string
   */
  public String toString() {
    String str = "";

    if (!this.hasParams()) {
      if (this.constant == 0.0) {
        str += 0.0; // Avoid -0.0
      } else {
        str += this.constant;
      }
    } else {
      if (this.constant != 0.0) {
        str += this.constant;
      }

      for (String param : this.parameters.keySet()) {
        double coeff = this.parameters.get(param);

        if (coeff != 0.0) {
          if (coeff > 0.0 && !str.isEmpty()) {
            str += " + ";
          } else if (coeff < 0.0) {
            str += str.isEmpty() ? "-" : " - ";
          }

          if (Math.abs(coeff) != 1.0) {
            str += Math.abs(coeff) + " * ";
          }

          str += param;
        }
      }
    }

    return str;
  }
}
