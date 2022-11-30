/**
 * The Transformer class.
 * CS2030S Lab 5
 * AY22/23 Semester 1
 *
 * @author Lim Xiu Jia (Lab 12A)
 */

package cs2030s.fp; // Places the Java file into the Package

public abstract class Transformer<R, P> implements Immutator<R, P> {
  private Transformer<R, P> f = this;

  public abstract R invoke(P p);

  // Non-Abstract Methods
  public <N> Transformer<R, N> after(Transformer<P, N> g) {

    // Local class
    class After extends Transformer<R, N> implements Immutator<R, N> {
      @Override
      public R invoke(N n) {
        return f.invoke(g.invoke(n));
      }
    }

    return new After();
  }

  public <T> Transformer<T, P> before(Transformer<T, R> g) {

    // Local class
    class Before extends Transformer<T, P> implements Immutator<T, P> {
      @Override
      public T invoke(P p) {
        return g.invoke(f.invoke(p));
      }
    }
    
    return new Before();
  }
}

