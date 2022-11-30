/**
 * This class encapsulates a Departure event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class DepartureEvent extends Event {
  /** 
   * The customer associated with this event.
   */
  private Customer customer;

  /**
   * Constructor for a Departure event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param time The time this event occurs.
   */
  public DepartureEvent(Customer customer, double time) {
    super(time);
    this.customer = customer;
  }

  /**
   * Returns the string representation of 
   * the Departure event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    String str = super.toString() +
                 ": " +
                 this.customer.toString() +
                 " departed";
    return str;
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Departure event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    return new Event[] {};
  }
}
