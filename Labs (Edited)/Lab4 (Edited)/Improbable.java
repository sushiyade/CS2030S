/**
 * A generic Immutator that takes in an object
 * that is T and returns an object that is probably T.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * @author Lim Xiu Jia (Lab 12A)
 */
public class Improbable<T> implements Immutator<Probably<T>, T> {
  public Probably<T> invoke(T input) {
    return Probably.just(input);
  }
}
