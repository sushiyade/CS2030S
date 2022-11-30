/**
 * The Array<T> for CS2030S 
 *
 * @author Lim Xiu Jia
 * @version CS2030S AY22/23 Semester 1
 */
class Array<T extends Comparable<T>> { // TODO: Change to bounded type parameter
  private T[] array;

  public Array(int size) {
    /**
     * Only way to put an object into an Array is through the
     * Shop constructor.
     * Can only put object of type T.
     * Hence, it is safe to cast 'Comparable[]' to 'T[]'.
     */
    @SuppressWarnings("rawtypes")
    T[] temp = (T[]) new Comparable[size];
    this.array = temp;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public T min() {
    T min = null;
    int len = this.array.length;
    T[] temp = this.array;

    for (int i = 0; i < len; ++i) {
      if (min == null) {
        min = temp[i];
      }
      if (min.compareTo(temp[i]) > -1) {
        min = temp[i];
      }
    }
    return min;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
