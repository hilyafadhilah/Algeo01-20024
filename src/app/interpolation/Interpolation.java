package app.interpolation;

import lib.matrix.Matrix;

/**
 * Representation of an interpolation data. Points are stored as an Nx2
 * <code>Matrix</code>.
 * 
 * @author Hilya Fadhilah Imania
 * @version 0.1.0
 * @see lib.matrix.Matrix
 * @since 2021-09-28
 */
public class Interpolation {
  /**
   * Data points stored as Nx2 <code>Matrix</code>
   * 
   * @see lib.matrix.Matrix
   */
  protected Matrix points;

  /**
   * Abscissa value to use for "searching" the approximation value
   */
  protected double searchValue;

  /**
   * Data points setter
   * 
   * @param p Data points as Nx2 <code>Matrix</code>
   * @throws InvalidInterpolationException If the matrix doesn't have 2 columns
   * @see #points
   */
  public void setPoints(Matrix p) throws InvalidInterpolationException {
    if (p.getNCols() != 2) {
      throw new InvalidInterpolationException();
    }

    this.points = p;
  }

  /**
   * Data points getter
   * 
   * @return Data points <code>Matrix</code>
   * @see #points
   */
  public Matrix getPoints() {
    return this.points;
  }

  /**
   * Search value setter
   * 
   * @param value The abscissa
   * @throws InvalidInterpolationException If the value is not within the range of
   *                                       interpolation data points
   * @see {@link #searchValue}
   */
  public void setSearchValue(double value) throws InvalidInterpolationException {
    if (value < getMinAbscissa() || value > getMaxAbscissa()) {
      throw new InvalidInterpolationException();
    }

    this.searchValue = value;
  }

  /**
   * Search value getter
   * 
   * @return Search value
   * @see #searchValue
   */
  public double getSearchValue() {
    return this.searchValue;
  }

  /**
   * Find the minimum abscissa within the data points
   * 
   * @return The minimum abscissa value
   */
  public double getMinAbscissa() {
    double min = 0.0;
    for (int i = 0; i < this.points.getNRows(); i++) {
      double abs = this.points.get(i, 0);
      if (abs < min) {
        min = abs;
      }
    }

    return min;
  }

  /**
   * Find the maximum abscissa within the data points
   * 
   * @return The maximum abscissa value
   */
  public double getMaxAbscissa() {
    double max = 0.0;
    for (int i = 0; i < this.points.getNRows(); i++) {
      double abs = this.points.get(i, 0);
      if (abs > max) {
        max = abs;
      }
    }

    return max;
  }

  /**
   * Convert this interpolation to string for display purposes
   */
  public String toString() {
    String str = "";
    for (int i = 0; i < this.points.getNRows(); i++) {
      str += String.format("(%f,%f)", this.points.get(i, 0), this.points.get(i, 1));

      if (i + 1 < this.points.getNRows()) {
        str += "\n";
      }
    }
    return str;
  }
}
