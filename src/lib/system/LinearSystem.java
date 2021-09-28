package lib.system;

import java.util.ArrayList;

import lib.matrix.Determinant;
import lib.matrix.Matrix;
import lib.system.CannotComputeException.Cause;
import lib.system.CannotComputeException.Method;

public class LinearSystem {
    public static ArrayList<Double> cramerMethod(Matrix m) throws Exception {
        if (m.getNRows() < (m.getNCols() - 1)) {
          throw new CannotComputeException(Method.CRAMER, Cause.NON_UNIQUE_SOLUTION);
        } else if (m.getNRows() > (m.getNCols() - 1)) {
          throw new CannotComputeException(Method.CRAMER, Cause.TOO_MANY_EQ);
        }
    
        Matrix mCoeff = m.subMatrix(0, 0, m.getNRows(), m.getNCols() - 1);
        double det = Determinant.cofactorMethod(mCoeff);
    
        if (det == 0.0) {
          throw new CannotComputeException(Method.CRAMER, Cause.NON_UNIQUE_SOLUTION);
        }
    
        Matrix mConst = m.subMatrix(0, m.getNCols() - 1);
        ArrayList<Double> solution = new ArrayList<>();
    
        for (int j = 0; j < mCoeff.getNCols(); j++) {
          Matrix mTmp = mCoeff.copy();
          for (int i = 0; i < mCoeff.getNRows(); i++) {
            mTmp.set(i, j, mConst.get(i, 0));
          }
    
          solution.add(Determinant.cofactorMethod(mTmp) / det);
        }
    
        return solution;
      }
}
