package cs2030s.fp;

/**
 * Generic Lazy class that produces a value using lazy evaluation.
 * CS2030S Lab 6
 * AY22/23 Semester 1
 *
 * @author    Lim Xiu Jia (Lab 12A)
 * @param <T> The type of the variable to be processed within the Lazy class.
 */
public class Lazy<T> implements Immutatorable<T> {

  /** Lazy Class Field for storing unevaluated Lazy. */
  private Constant<? extends T> init;

  // Protected Constructor
  // Takes in Constant that produces value when needed
  /**
   * Protected Constructor of Lazy for inputs of type Constant.
   * 
   * @param c The Constant to be passed in.
   */
  protected Lazy(Constant<? extends T> c) { 
    this.init = c;
  }

  // Instantiate Lazy obj with given v using Constructor
  /**
   * Initializes the Lazy Object with a given value.
   *
   * @param <T> Type of Value
   * @param v   The value to be initialised by Lazy.
   * @return    An instance of Lazy.
   */
  public static <T> Lazy<T> from(T v) {
    return new Lazy<T>(() -> v);
  }

  // Takes in constant that produces value when needed
  // and Instantiate Lazy obj
  /**
   * Takes in a Constant that produces the value when needed.
   *
   * @param <T> Type of value to be produced when evaluated.
   * @param c   The value to be produced when evaluated.
   * @return    An instance of Lazy.
   */
  public static <T> Lazy<T> from(Constant<? extends T> c) {
    return new Lazy<T>(c);
  }

  // Called when Value is needed
  /**
   * Computes the value and returns it.
   *
   * @return The evaluation of value of type T passed into Lazy.
   */
  public T get() {
    return this.init.init();
  }

  // Returns string representation of value
  /**
   * Returns the String Representation of the value.
   *
   * @return Returns a string representation of the value of type T.
   */
  @Override
  public String toString() {
    return this.get().toString();
  }

  // Transform method
  /**
   * Transforms the evaluated value from type T to type R,
   * then return it as {@code Lazy<R>}.
   * 
   * @param <R>       The type of the transformed value.
   * @param immutator The immutator to be passed in.
   * @return          An instance of Lazy.
   */
  @Override
  public <R> Lazy<R> transform(Immutator<? extends R, ? super T> immutator) {
    Constant<R> c = () -> immutator.invoke(this.get());
    return Lazy.from(c);
  }

  // Next method
  /**
   * Transforms the evaluated value from type T to a value of
   * type R stored within {@code Lazy<R>}.
   *
   * @param <R>       The type of the transformed value.
   * @param immutator The immutator to be passed in.
   * @return          An instance of Lazy.
   */
  public <R> Lazy<R> next(Immutator<? extends Lazy<? extends R>, ? super T> immutator) {
    Constant<R> c = () -> immutator.invoke(this.get()).get();
    return Lazy.from(c);
  }
}
