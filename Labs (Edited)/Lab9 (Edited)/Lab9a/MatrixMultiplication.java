import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * MatrixMultiplication class.
 * CS2030S Lab 9a
 * AY22/23 Semester 1
 *
 * @author  Lim Xiu Jia (Group 12A)
 */
class MatrixMultiplication extends RecursiveTask<Matrix> {
  
  /** The fork threshold. */
  private static final int FORK_THRESHOLD = 128; // Find a good threshold
  // Threshold should be 32 or 64.

  /** The first matrix to multiply with. */
  private final Matrix m1;

  /** The second matrix to multiply with. */
  private final Matrix m2;

  /** The starting row of m1. */
  private final int m1Row;

  /** The starting col of m1. */
  private final int m1Col;

  /** The starting row of m2. */
  private final int m2Row;

  /** The starting col of m2. */
  private final int m2Col;

  /**
   * The dimension of the input (sub)-matrices and the size of the output
   * matrix.
   */
  private int dimension;

  /**
   * A constructor for the Matrix Multiplication class.
   * @param  m1 The matrix to multiply with.
   * @param  m2 The matrix to multiply with.
   * @param  m1Row The starting row of m1.
   * @param  m1Col The starting col of m1.
   * @param  m2Row The starting row of m2.
   * @param  m2Col The starting col of m2.
   * @param  dimension The dimension of the input (sub)-matrices and the size
   *     of the output matrix.
   */
  MatrixMultiplication(Matrix m1, Matrix m2, int m1Row, int m1Col, int m2Row,
                       int m2Col, int dimension) {
    this.m1 = m1;
    this.m2 = m2;
    this.m1Row = m1Row;
    this.m1Col = m1Col;
    this.m2Row = m2Row;
    this.m2Col = m2Col;
    this.dimension = dimension;
  }


  @Override
  public Matrix compute() {
    // Modify this

    // If the matrix is small enough, just multiple non-recursively.
    if (this.dimension <= MatrixMultiplication.FORK_THRESHOLD) {
      return Matrix.nonRecursiveMultiply(m1, m2, m1Row, m1Col, m2Row, m2Col, dimension);
    }

    // Else, cut the matrix into four blocks of equal size, recursively
    // multiply then sum the multiplication result.
    int size = dimension / 2;
    Matrix result = new Matrix(dimension);
 
    final List<MatrixMultiplication> quarter1 = new CopyOnWriteArrayList<>();
    final List<MatrixMultiplication> quarter2 = new CopyOnWriteArrayList<>();
    final List<MatrixMultiplication> quarter3 = new CopyOnWriteArrayList<>();
    final List<MatrixMultiplication> quarter4 = new CopyOnWriteArrayList<>();

    final MatrixMultiplication a11b11MMulti = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row,
        m2Col, size);
    final MatrixMultiplication a12b21MMulti = new MatrixMultiplication(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col, size);
    quarter1.add(a11b11MMulti);
    quarter1.add(a12b21MMulti);

    final MatrixMultiplication a11b12MMulti = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row,
        m2Col + size, size);
    final MatrixMultiplication a12b22MMulti = new MatrixMultiplication(m1, m2, m1Row, m1Col + size,
        m2Row + size, m2Col + size, size);
    quarter2.add(a11b12MMulti);
    quarter2.add(a12b22MMulti);

    final MatrixMultiplication a21b11MMulti = new MatrixMultiplication(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col, size);
    final MatrixMultiplication a22b21MMulti = new MatrixMultiplication(m1, m2, 
        m1Row + size, m1Col + size,
        m2Row + size, m2Col, size);
    quarter3.add(a21b11MMulti);
    quarter3.add(a22b21MMulti);
 
    final MatrixMultiplication a21b12MMulti = new MatrixMultiplication(m1, m2, m1Row + size, m1Col,
        m2Row, m2Col + size, size);
    final MatrixMultiplication a22b22MMulti = new MatrixMultiplication(m1, m2, 
        m1Row + size, m1Col + size,
        m2Row + size, m2Col + size, size);
    quarter4.add(a21b12MMulti);
    quarter4.add(a22b22MMulti);

    // Should not call ForkJoinTask::invokeAll as you have no control over how they are ::forked
    // Should have fork() all 8 tasks and join() them in reverse order
    ForkJoinTask.invokeAll(quarter1)
            .parallelStream()
              .map(ForkJoinTask::join)
                .reduce((matrix1, matrix2) -> {
                  for (int i = 0; i < size; i++) {
                    double[] m1m = matrix1.m[i];
                    double[] m2m = matrix2.m[i];
                    for (int j = 0; j < size; j++) {
                      result.m[i][j] = m1m[j] + m2m[j];
                    }
                  }
                  return result;
                });
    
    ForkJoinTask.invokeAll(quarter2)
            .parallelStream()
              .map(ForkJoinTask::join)
                .reduce((matrix1, matrix2) -> {
                  for (int i = 0; i < size; i++) {
                    double[] m1m = matrix1.m[i];
                    double[] m2m = matrix2.m[i];
                    for (int j = 0; j < size; j++) {
                      result.m[i][j + size] = m1m[j] + m2m[j];
                    }
                  }
                  return result;
                });
    
    ForkJoinTask.invokeAll(quarter3)
            .parallelStream()
              .map(ForkJoinTask::join)
                .reduce((matrix1, matrix2) -> {
                  for (int i = 0; i < size; i++) {
                    double[] m1m = matrix1.m[i];
                    double[] m2m = matrix2.m[i];
                    for (int j = 0; j < size; j++) {
                      result.m[i + size][j] = m1m[j] + m2m[j];
                    }
                  }
                  return result;
                });

    ForkJoinTask.invokeAll(quarter4)
            .parallelStream()
              .map(ForkJoinTask::join)
                .reduce((matrix1, matrix2) -> {
                  for (int i = 0; i < size; i++) {
                    double[] m1m = matrix1.m[i];
                    double[] m2m = matrix2.m[i];
                    for (int j = 0; j < size; j++) {
                      result.m[i + size][j + size] = m1m[j] + m2m[j];
                    }
                  }
                  return result;
                });
            
    return result;
  }
}
