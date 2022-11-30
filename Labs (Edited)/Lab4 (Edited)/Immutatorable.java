/**
 * The Immutatorable interface that can
 * transform when given something that is
 * Immutator.
 * Contains a single abstract method transform.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * @author Lim Xiu Jia (Lab 12A)
 */
public interface Immutatorable<T> {
  public abstract <S> Immutatorable<S> transform(Immutator<S, T> input);
  // public abstract <S> Immutatorable<S> transform(Immutator<? extends S, ? super T> input);
}
