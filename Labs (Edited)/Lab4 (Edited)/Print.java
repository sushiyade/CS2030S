/**
 * A non-generic Action to print the String
 * representation of the object.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * @author Lim Xiu Jia (Lab 12A)
 */
public class Print implements Action<Object> {
  @Override
  public void call(Object value) {
    System.out.println(value);
    // System.out.println("" + value);
  }
}
