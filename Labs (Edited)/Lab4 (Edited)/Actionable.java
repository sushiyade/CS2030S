/**
 * The Actionable interface that can
 * act when given an action.
 * Contains a single abstract method act.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Lim Xiu Jia (Lab 12A)
 */
public interface Actionable<T> {
  // Abstract method
  public abstract void act(Action<? super T> a);
}
