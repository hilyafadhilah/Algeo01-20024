package lib.matrix;

public class Determinant {
  public static double cofactorMethod(Matrix mat) throws Exception {
    if (!mat.isSquare()) {
      throw new Exception();
    }

    // Basis
    if (mat.getNRows() == 2) {
      return mat.get(0, 0) * mat.get(1, 1) - mat.get(0, 1) * mat.get(1, 0);
    } else {
      /* Rekurens: Matriks N x N */
      int size = mat.getNRows();
      double det = 0.0;

      for (int col = 0; col < size; col++) {
        /* Baris acuan: Baris pertama */
        /* Buat kofaktor */
        Matrix cofactor = new Matrix(size - 1, size - 1);
        for (int i = 1; i < size; i++) {
          for (int j = 0; j < size; j++) {
            if (j != col) {
              cofactor.set(i - 1, (j < col ? j : j - 1), mat.get(i, j));
            }
          }
        }

        /* Hitung faktor penambah */
        double addend = mat.get(0, col) * cofactorMethod(cofactor);
        if (col % 2 != 0) {
          addend = -addend;
        }

        /* Tambahkan ke determinan */
        det += addend;
      }
      return det;
    }
  }

  public static double reductionMethod() throws Exception {
    return 0.0;
  }
}
