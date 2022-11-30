/**
 * The Action interface that can be called
 * on an object of type T to act.
 * Contains a single abstract method call.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Lim Xiu Jia (Lab 12A)
 */

package cs2030s.fp; // Places the java file into the Package

public abstract interface Action<T> {
  // Abstract method
  public abstract void call(T input);
}
