/**
 * A non-generic Immutator with parameter
 * div and mod that takes in an integer val
 * and return the boolean value by checking
 * if the given value is equal to mod when
 * divided by div.
 * CS2030S Lab 4
 * AY22/23 Semester 1
 * @author Lim Xiu Jia (Lab 12A)
 */
public class IsModEq implements Immutator<Boolean, Integer> {
  private Integer div;
  private Integer check;

  // Constructor
  public IsModEq(Integer div, Integer check) {
    this.div = div;
    this.check = check;
  }

  @Override
  public Boolean invoke(Integer val) {
    int remainder = val % div;
    return remainder == check;
  }
}
