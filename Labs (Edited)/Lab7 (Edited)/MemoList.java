import cs2030s.fp.Combiner;
import cs2030s.fp.Constant;
import cs2030s.fp.Immutator;
import cs2030s.fp.Memo;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper around a lazily evaluated and memoized
 * list that can be generated with a lambda expression.
 * CS2030S Lab 7
 * AY22/23 Semester 1
 *
 * @author    Lim Xiu Jia (Lab 12A)
 * @version   CS2030S AY 22/23 Sem 1
 * @param <T> The type of the variable to be processed within Memo class.
 */
public class MemoList<T> {
  /** The wrapped java.util.List object */
  private List<Memo<T>> list;

  /** 
   * A private constructor to initialize the list to the given one. 
   *
   * @param list The given java.util.List to wrap around.
   */
  private MemoList(List<Memo<T>> list) {
    this.list = list;
  }

  /** 
   * Generate the content of the list.  Given x and a lambda f, 
   * generate the list of n elements as [x, f(x), f(f(x)), f(f(f(x))), 
   * ... ]
   *
   * @param <T> The type of the elements in the list.
   * @param n The number of elements.
   * @param seed The first element.
   * @param f The immutator function on the elements.
   * @return The created list.
   */
  public static <T> MemoList<T> generate(int n, T seed, Immutator<? extends T, ? super T> f) {
    MemoList<T> memoList = new MemoList<>(new ArrayList<>());
    Memo<T> curr = Memo.from(seed);
    for (int i = 0; i < n; i++) {
      memoList.list.add(curr);
      curr = curr.transform(f);
    }
    return memoList;
  }

  /** 
   * Generate the content of the list.  Given x, y and a combiner f, 
   * generate the list of n elements as [x, y, f(x, y), f(y, f(x, y)),
   * f(f(x, y), f(y, f(x, y))), ... ]
   *
   * @param <T>   The type of the elements in the list.
   * @param n     The number of elements in the list.
   * @param fst   The first element in the list
   * @param snd   The second element in the list.
   * @param f     The combiner 
   * @return The created list.
   */
  public static <T> MemoList<T> generate(int n, T fst, T snd,
      Combiner<? extends T, ? super T, ? super T> f) {
    MemoList<T> memoList = new MemoList<>(new ArrayList<>());
    
    Memo<T> fstMemo = Memo.from(fst);
    memoList.list.add(fstMemo);
    Memo<T> curr = Memo.from(snd);
    Memo<T> temp;
    for (int i = 1; i < n; i++) {
      memoList.list.add(curr);
      temp = curr;
      curr = fstMemo.combine(curr, f);
      fstMemo = temp;
    }
    return memoList;
  }

  /** 
   * Applies given Immutator on each element in the list.
   * Returns resulting MemoList.
   *
   * @param <R>   The type of the elements in the resulting list.
   * @param f     The given Immutator.
   * @return      The resulting MemoList.
   */
  public <R> MemoList<R> map(Immutator<? extends R, ? super T> f) {
    MemoList<R> memolistR = new MemoList<>(new ArrayList<>());
    for (int i = 0; i < this.list.size(); i++) {
      Memo<T> curr = this.list.get(i);
      Memo<R> r = curr.transform(f);
      memolistR.list.add(r);
    }
    return memolistR;
  }

  /** 
   * Applies given Immutator on each element in the list.
   * Flattens the lists in the list such that it is not nested.
   *
   * @param <R>   The type of the elements in the resulting list
   * @param f     The given Immutator
   * @return      The resulting MemoList.
   */
  public <R> MemoList<R> flatMap(Immutator<? extends MemoList<R>, ? super T> f) {
    MemoList<R> memolistR = new MemoList<>(new ArrayList<>());
    for (int i = 0; i < this.list.size(); i++) {
      Memo<T> curr = this.list.get(i);
      MemoList<R> nestedlistR = curr.transform(f).get();
      for (int j = 0; j < nestedlistR.list.size(); j++) {
        Memo<R> r = nestedlistR.list.get(j);
        memolistR.list.add(r);
      }
    }
    return memolistR;
  }

  /** 
   * Return the element at index i of the list.  
   *
   * @param i The index of the element to retrieved (0 for the 1st element).
   * @return The element at index i.
   */
  public T get(int i) {
    return this.list.get(i).get();
  }

  /** 
   * Find the index of a given element.
   *
   * @param v The value of the element to look for.
   * @return The index of the element in the list.  -1 is element is not in the list.
   */
  public int indexOf(T v) {
    return this.list.indexOf(Memo.from(v));
  }

  /** 
   * Return the string representation of the list.
   *
   * @return The string representation of the list.
   */
  @Override
  public String toString() {
    return this.list.toString();
  }
}
