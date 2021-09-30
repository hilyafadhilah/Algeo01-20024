package lib.matrix;

public class Inverse {
  
  public static Matrix GaussMethod(Matrix m) throws Exception {
    Matrix mProcces = new Matrix(m.getNRows(), 2*m.getNCols());
    for (int a = 0; a < mProcces.getNRows(); a++) {
      for (int b = 0; b < mProcces.getNCols(); b++) {
        if (a == (b - m.getNRows())) {
          mProcces.set(a, b, 1);
        } else if (b > mProcces.getNRows()-1) {
          mProcces.set(a, b, 0);
        } else {
          mProcces.set(a, b, m.get(a, b));
        }
      }
    }
    System.out.println(mProcces.toString());
    Matrix mTemp = mProcces.toReducedEchelon();
    Matrix mResult = new Matrix(m.getNRows(), m.getNCols());
    for (int c = 0; c < mResult.getNRows(); c++){
      for (int d = 0; d < mResult.getNCols(); d++) {
          mResult.set(c, d, mTemp.get(c, d + m.getNCols()));
      }
    }
    return mResult;
  }

  public static Matrix CofactorMethod(Matrix m) throws Exception {
    if (Determinant.cofactorMethod(m) != 0) {
      Matrix mCofactor = m.toCofactor().transpose();
      Matrix mInvers = mCofactor.divide(Determinant.cofactorMethod(m));
      return mInvers;
    } else {
      throw new Exception();
    }
  }
}
