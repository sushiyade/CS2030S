/**
 * The Actually class. 
 * CS2030S Lab 5
 * AY22/23 Semester 1
 *
 * @author Lim Xiu Jia (Lab 12A)
 */

package cs2030s.fp; // Places the Java file into the Package

public abstract class Actually<T> implements Immutatorable<T>, Actionable<T> {

  // Actually - Abstract Methods
  public abstract T unwrap() throws Exception;

  public abstract <U extends T> T except(Constant<? extends U> c);
  
  public abstract void finish(Action<? super T> a);
  
  public abstract <U extends T> T unless(U param);

  public abstract <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> imm);

  public abstract void act(Action<? super T> action);

  public abstract <R> Actually<R> transform(Immutator<? extends R, ? super T> immutator);

  // Actually - Static Factory Methods.

  public static <T> Actually<T> ok(T res) {
    //Actually<T> success = new Success<>(res);
    //return success;
    return new Success<T>(res);
  }

  public static <T> Actually<T> err(Exception exc) {
    /*
     * Ensured that T is a subtype of Object
     * Only way to put an Exception into Actually is through the
     * Failure constructor. Exception <: Object, T <: Object
     * Can put object of type T extends Object.
     * Hence, it is safe to cast 'Failure(exc)' into 'Actually<T>'
     */
    // Failure does not have Generics, hence it is okay to cast.
    @SuppressWarnings("unchecked")
    Actually<T> failure = (Actually<T>) new Failure(exc);
    return failure;
  }

  // Concrete, Static, Nested classes

  /**
   * Nested Static Class Success.
   */
  // Usually if don't want to access from outside,
  // you'll normally not want it to be accessed by
  // the inherited class too.
  // ==> private instead of protected
  private static final class Success<T> extends Actually<T> {
    
    // Success variables
    private final T content;

    // Private Constructor of Success
    private Success(T content) {
      this.content = content;
    }

    /**
     * String representation of Success that
     * has a value or actually nothing.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
      return "<" + this.content.toString() + ">";
    }

    /**
     * Checks for equality between both
     * Success.
     *
     * @param obj The other Success to be compared
     * @return    True if the two Successes are equal,
     *            False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Success<?>) {
        // Type casts obj to type Success
        Success<?> success = (Success<?>) obj;
        if (this.isNull()) {
          return success.isNull();
        }
        return this.content.equals(success.content);
      }
      return false;
    }

    // Checks if content of Success is null
    public boolean isNull() {
      return this.content == null;
    }

    // Success Methods - get results in a safe way
    @Override
    public T unwrap() {
      return this.content;
    }

    @Override
    public <U extends T> T except(Constant<? extends U> c) {
      return this.content;
    }

    @Override
    public void finish(Action<? super T> a) {
      a.call(this.content);
    }

    @Override
    public <U extends T> T unless(U param) {
      return this.content;
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> immutator) {
      try {
        // Immutator returns an Actually<R>
        // Hence, it is safe to cast the Actually obj from Immutator to 'Actually<R>'.
        @SuppressWarnings("unchecked")
        Actually<R> obj = (Actually<R>) immutator.invoke(this.content);
        return obj;
      } catch (Exception exc) {
        return Actually.<R>err(exc);
      }
    }

    // Transform Method from Immutatorable<T>
    @Override
    public <R> Actually<R> transform(Immutator<? extends R, ? super T> immutator) {
      try {
        return Actually.<R>ok(immutator.invoke(this.content));
      } catch (Exception exc) {
        return Actually.<R>err(exc);
      }
    }

    // Act Method from Actionable<T>
    @Override
    public void act(Action<? super T> action) {
      action.call(this.content);
    }
  }

  /**
   * Nested Static Class Failure.
   */
  // Usually if don't want to access from outside,
  // you'll normally not want it to be accessed by
  // the inherited class too.
  // ==> private instead of protected
  private static final class Failure extends Actually<Object> {

    // Failure variables
    private final Exception content;

    // Private Constructor of Failure
    private Failure(Exception content) {
      this.content = content;
    }

    /**
     * String representation of Failure that
     * has an exception.
     *
     * @return String representation of Failure
     */
    @Override
    public String toString() {
      String str = "[" + this.content.getClass().getName() + "] "
                  + this.content.getMessage();
      return str;
      //return String.format("[%s] %s",
      //    this.content.getClass().getName(),
      //    this.content.getMessage());
    }
    
    /**
     * Checks for equality between both
     * Failure.
     *
     * @param obj The other Failure to be compared.
     * @return    True if the contents are equal,
     *            False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Failure) {
        // Type casts obj to type Failure
        Failure failure = (Failure) obj;
        
        // Edited in after Lab lesson
        if (this.content == null || failure.content == null) {
          return false;
        }
        // Edited ends here

        // This is okay without the above Edited code
        // as Exception will never be null.
        // ==> this.content or failure.content will not be null
        //     if getMessage() can be called.
        if (this.content.getMessage() != null || failure.content.getMessage() != null) {
          return (this.content.getMessage().equals(failure.content.getMessage()));
        }
      }
      return false;
    }

    // Failure Methods - get results in a safe way
    @Override
    public Object unwrap() throws Exception {
      // public Exception unwrap() throws Exception {
      // Is this wrong ??
      // Technically not wrong, but if it returns an Exception,
      // it may have to run other codes, if there are,
      // before finally returning the Exception.
      // Whereas for Throw, it just jumps straight to
      // "returning" the Exception to the user.
      // return this.content;

      // Edited in after Lab lesson
      throw this.content;
      // Edited ends here
    }

    @Override
    public <U extends Object> Object except(Constant<? extends U> c) {
      return c.init();
    }

    @Override
    public void finish(Action<? super Object> a) {
    }

    @Override
    public <U extends Object> Object unless(U param) {
      return param;
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super Object> imm) {
      return Actually.<R>err(this.content);
    }

    // Transform Method from Immutatorable<T>
    @Override
    public <R> Actually<R> transform(Immutator<? extends R, ? super Object> immutator) {
      return Actually.<R>err(this.content);
    }

    // Act Method from Actionable<T>
    @Override
    public void act(Action<? super Object> action) {
    }
  }
}
