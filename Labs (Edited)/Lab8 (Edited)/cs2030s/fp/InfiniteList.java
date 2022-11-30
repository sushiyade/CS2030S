package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic InfiniteList class that represents an infinite list of items.
 * CS2030S Lab 8
 * AY22/23 Semester 1
 *
 * @author    Lim Xiu Jia (Group 12A)
 * @param <T> Type of object to be used in InfiniteList.
 */
public class InfiniteList<T> {

  /**
   * Head of the InfiniteList.  
   */
  private Memo<Actually<T>> head;
  
  /**
   * Tail of the InfiniteList.
   */
  private Memo<InfiniteList<T>> tail;
  
  /**
   * Private Constructor of InfiniteList.
   *
   * @param head  Wrapped head item.
   * @param tail  Wrapped tail item.
   */
  private InfiniteList(Memo<Actually<T>> head, Memo<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Final class field for caching a single instance of the End.
   */
  private static final InfiniteList<?> END = new End();

  // You may add other private constructor but it's not necessary.

  /**
   * Generates an infinite list.
   *
   * @param <T>   Type of value in Memo.
   * @param prod  The constant to be passed in.
   * @return      A single InfiniteList object with a head and tail.
   */
  public static <T> InfiniteList<T> generate(Constant<T> prod) {
    return new InfiniteList<>(Memo.from(() -> Actually.ok(prod.init())), 
        Memo.from(() -> generate(prod)));
  }

  /**
   * Produces an infinite list.
   *
   * @param <T>   Type of the value in Memo.
   * @param seed  Starting item of the infinite InfiniteList.
   * @param func  Delayed evaluation of the next item in list of InfiniteList.
   * @return      An infinite list of InfiniteList object.
   */
  public static <T> InfiniteList<T> iterate(T seed, Immutator<T, T> func) {
    return new InfiniteList<>(
        Memo.from(Actually.ok(seed)),
        Memo.from(() -> iterate(func.invoke(seed), func)));
  }
  
  /**
   * Returns the head of an InfiniteList.
   *
   * @return The head of an InfiniteList.
   */
  public T head() {
    return this.head.get().except(() -> this.tail.get().head());
  }
  
  /**
   * Returns the tail of an InfiniteList.
   *
   * @return The tail of an InfiniteList.
   */
  public InfiniteList<T> tail() {
    return this.head.get()
      .transform(ignr -> this.tail.get())
      .except(() -> this.tail.get().tail());
  }
  
  /**
   * Applies the given transformation to each element in the list
   * and returns the resulting InfiniteList.
   *
   * @param <R> Type of new InfiniteList.
   * @param f   Immutator to be given.
   * @return    New InfiniteList instance.
   */
  public <R> InfiniteList<R> map(Immutator<? extends R, ? super T> f) {
    Memo<Actually<R>> memoHead = Memo.from(() -> Actually.ok(f.invoke(this.head())));
    Memo<InfiniteList<R>> memoTail = Memo.from(() -> this.tail().map(f));
    return new InfiniteList<>(memoHead, memoTail);
  }
  
  /**
   * Filter out elements in the list.
   *
   * @param pred  Condition to be tested on each element.
   * @return      New InfiniteList instance.
   */
  public InfiniteList<T> filter(Immutator<Boolean, ? super T> pred) {
    Memo<Actually<T>> memoHead = Memo.from(() -> this.head.get().check(pred));
    Memo<InfiniteList<T>> memoTail = Memo.from(() -> this.tail.get().filter(pred));
    return new InfiniteList<>(memoHead, memoTail);
  }
  
  /**
   * Truncate the InfiniteList to a finite
   * list with at most n elements.
   *
   * @param n Maximum number of elements to truncate list to.
   * @return  New truncated InfiniteList instance.
   */
  public InfiniteList<T> limit(long n) {
    Memo<InfiniteList<T>> memoTail = Memo.from(() -> this.head.get()
        .transform(ignr -> this.tail.get().limit(n - 1))
        .except(() -> this.tail.get().limit(n)));

    return Actually.ok(n)
      .check(x -> x > 0)
      .transform(ignr -> new InfiniteList<>(this.head, memoTail))
      .except(() -> end());
  }
  
  /**
   * Truncates the InfiniteList as soon as it finds
   * an element taht evaluates the condition to be false.
   *
   * @param pred  Condition to be tested on each element.
   * @return      New truncated InfiniteList instance.
   */
  public InfiniteList<T> takeWhile(Immutator<Boolean, ? super T> pred) {
    // TODO
    Memo<Actually<T>> memoHead = Memo.from(() -> this.head.get().check(pred));
    Memo<InfiniteList<T>> memoTail = Memo.from(() -> this.head.get()
        .transform(x -> memoHead.get()
          .transform(y -> this.tail.get().takeWhile(pred))
          .except(() -> end()))
        .except(() -> this.tail.get().takeWhile(pred)));
    return new InfiniteList<>(memoHead, memoTail);
  }
  
  /**
   * Collects the elements in the InfiniteList into a Java List.
   *
   * @return Java List representation of the InfiniteList.
   */
  public List<T> toList() {
    List<T> l = new ArrayList<>();
    this.head.get().finish(head -> l.add(head));
    l.addAll(this.tail.get().toList());
    return l;
  }

  /**
   * Reduce the elements of the InfiniteList using the
   * provided identity value and an associative
   * accumulation function, and return the reduced value.
   *
   * @param <U> Type of the identity and result of the reduction
   * @param id  Identity of the accumulator function.
   * @param acc Function for combining two values.
   * @return    Result of the reduction.
   */
  public <U> U reduce(U id, Combiner<U, U, ? super T> acc) {
    return this.tail.get().reduce(this.head.get()
        .transform(tail -> acc.combine(id, tail))
        .unless(id), acc);
  }

  /**
   * Returns the number of elements in the InfiniteList.
   *
   * @return Number of elements in the InfiniteList.
   */
  public long count() {
    return this.reduce(0, (x, y) -> x + 1);
  }

  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }
  
  /**
   * Abstraction for checking if the list is an instance of End.
   *
   * @return Boolean.
   */
  public boolean isEnd() {
    return false;
  }

  /**
   * Returns an End.
   *
   * @param <T> Type of the End.
   * @return    An instance of End.
   */
  public static <T> InfiniteList<T> end() {
    /*
     * END is only used to signify the end of an
     * InfiniteList and can only contain null, therefore
     * it can safely be passed into wildcard during type casting.
     */
    @SuppressWarnings("unchecked")
    InfiniteList<T> temp = (InfiniteList<T>) END;
    return temp;
  }
  
  // Add your End class here...
  /**
   * Static Nested End class that represent a list that
   * contains nothing and is used to mark the end of the list.
   */
  private static class End extends InfiniteList<Object> {
    /**
     * Private Constructor for End.
     */
    private End() {
      super(null, null);
    }

    /**
     * Returns a String Representation of End.
     *
     * @return the String Representation of End.
     */
    @Override
    public String toString() {
      return "-";
    }

    /**
     * Abstraction for checking if the list is an instance of End.
     *
     * @return Boolean.
     */
    @Override
    public boolean isEnd() {
      return true;
    }

    /**
     * Calling head on an empty InfiniteList should simply
     * throw an error.
     *
     * @return No such element exception
     */
    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    /**
     * Calling tail on an empty InfiniteList should simple
     * throw an error.
     *
     * @return  No such element exception.
     */
    @Override
    public InfiniteList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public <R> InfiniteList<R> map(Immutator<? extends R, ? super Object> f) {
      return end();
    }
    
    @Override
    public InfiniteList<Object> filter(Immutator<Boolean, ? super Object> pred) {
      return end();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return end();
    }

    public InfiniteList<Object> takeWhile(Immutator<Boolean, ? super Object> pred) {
      return end();
    }
 
    @Override
    public List<Object> toList() {
      return List.of();
    }
    
    @Override
    public <U> U reduce(U id, Combiner<U, U, ? super Object> acc) {
      return id;
    }

    @Override
    public long count() {
      return 0L;
    }
  }
}
