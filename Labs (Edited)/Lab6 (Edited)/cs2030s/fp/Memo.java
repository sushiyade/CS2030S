package cs2030s.fp;

/**
 * Generic Memo class inherited from Lazy class.
 * Computes a value only once and 
 * stores the computed value.
 * CS2030S Lab 6
 * AY22/23 Semester 1
 *
 * @author    Lim Xiu Jia (Lab 12A)
 * @param <T> The type of the variable to be processed within the Memo class.
 */
public class Memo<T> extends Lazy<T> {
  /** Memo Class Field for storing the computed value. */
  private Actually<T> value;

  // No public constructor so Private/Protected constructor ?
  /**
   * Protected Constructor of Memo for inputs of type Constant.
   *
   * @param init  The Constant to be passed in.
   * @param value The Actually object to be passed in.
   */
  protected Memo(Constant<? extends T> init, Actually<T> value) {
    super(init);
    this.value = value;
  }

  // Initializes Memo object with given value
  /**
   * Initializes the Memo Object with a given value.
   * {@code Actually<T>} is a {@code Success<T>} that stores the value v.
   *
   * @param <T> Type of Value
   * @param v   The value to be initialized by Memo
   * @return    An instance of Memo.
   */
  public static <T> Memo<T> from(T v) {
    return new Memo<T>(() -> v, Actually.ok(v));
  }

  // Takes in a Constant that produces the value when needed
  /**
   * Takes in a Constant that produces the value when needed.
   * As it is not an evaluated value, a Failure object that 
   * stores a general Exception with the message "?" is passed.
   *
   * @param <T> Type of value to be produced when evaluated.
   * @param c   The value to be produced and evaluated.
   * @return    An instance of Memo.
   */
  public static <T> Memo<T> from(Constant<? extends T> c) {
    return new Memo<T>(c, Actually.err(new Exception("?")));
  }

  // Called when Value is needed.
  // Return value if value already available.
  // Compute and return value if otherwise.
  /**
   * Returns value if value is already available;
   * Compute and return the value if otherwise.
   *
   * @return The evaluated value of type T passed into Memo.
   */
  @Override
  public T get() {
    this.value = Actually.ok(this.value.except(() -> super.get()));
    return this.value.except(() -> null);
  }

  // Returns "?" if Value is not yet available; returns the String
  // Representation of the value otherwise.
  /**
   * Returns "?" if Value not yet available; Returns the String
   * Representation of the value otherwise.
   * 
   * @return Returns "?" if value not available;
   *         Otherwise, return a string representation of
   *         the value of type T.
   */
  @Override
  public String toString() {
    Immutator<Actually<String>, T> immutator = v -> Actually.ok(String.valueOf(v));
    return this.value.next(immutator).except(() -> "?");
  }

  // Transform method
  /**
   * Transform the evaluated value in Memo from type T 
   * to type R, then return it as {@code Memo<R>}.
   *
   * @param <R>       The type of the transformed value.
   * @param immutator The immutator to be passed in.
   * @return          An instance of Memo.
   */
  @Override
  public <R> Memo<R> transform(Immutator<? extends R, ? super T> immutator) {
    Constant<R> c = () -> immutator.invoke(this.get());
    return Memo.from(c);
  }

  // Next method
  /**
   * Transforms the evaluated value in Memo from type T
   * to a value of type R stored within {@code Lazy<R>}.
   *
   * @param <R>       The type of the transformed vlaue.
   * @param immutator The immutator to be passed in.
   * @return          An instance of Memo.
   */
  @Override
  public <R> Memo<R> next(Immutator<? extends Lazy<? extends R>, ? super T> immutator) {
    Constant<R> c = () -> immutator.invoke(this.get()).get();
    return Memo.from(c);
  }

  // Combine method
  /**
   * Combines two values, of type S and T respectively,
   * into a result of type R.
   *
   * @param <R>         The type of the combined value.
   * @param <S>         The type of value in the second Memo.
   * @param secondMemo  The other Memo to be passed in.
   * @param combiner    The Combiner to be passed in.
   * @return            An instance of Memo.
   */
  public <R, S> Memo<R> combine(Memo<S> secondMemo,
      Combiner<? extends R, ? super T, ? super S> combiner) {
    Constant<R> c = () -> combiner.combine(this.get(), secondMemo.get());
    return Memo.from(c);
  }
}
