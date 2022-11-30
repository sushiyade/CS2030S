/**
 * The Applicable interface that can probably
 * transform if given something that is
 * probably an Immutator.
 * Contains a single abstract method apply.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * @author Lim Xiu Jia (Lab 12A)
 */
public interface Applicable<T> {
  // Abstract method
  public abstract <S> Probably<S> apply(Probably<Immutator<S, T>> p);
  // public abstract <S> Probably<S> apply(Probably<? extends Immutator<? extends S, ? super T>> p);
}
