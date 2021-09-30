package lib.system;

import java.util.HashMap;

public class Solution {
  public double constant = 0.0;
  public HashMap<String, Double> parameters = new HashMap<>();

  public Solution(double constant) {
    this.constant = constant;
  }

  public Solution(String param) {
    this.parameters.put(param, 1.0);
  }

  public Solution(String param, double coeff) {
    this.parameters.put(param, coeff);
  }

  public void add(String param, double coeff) {
    this.parameters.put(param, coeff);
  }

  public void get(String param) {
    this.parameters.get(param);
  }

  public boolean hasParams() {
    return !this.parameters.isEmpty();
  }

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
            str += coeff + " * ";
          }

          str += param;
        }
      }
    }

    return str;
  }
}
