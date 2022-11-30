/**
 * This class encapsulates a Service End
 * event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class ServiceEndEvent extends Event {
  /** 
   * The customer associated with this event
   */
  private Customer customer;

  /** 
   * The counter associated with this event
   */
  private Counter counter;

  /**
   * Constructor for an Service End event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param counter The counter associated with
   *                this event.
   * @param time The time this event occurs.
   */
  public ServiceEndEvent(Customer customer, Counter counter, double time) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  /**
   * Returns the string representation of 
   * the Service End event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString() +
      " service done (by " + this.counter.toString() + ")";
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Service End event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    this.counter.setAvailable();
    return new Event[] {
        new DepartureEvent(this.customer, this.getTime())
      };
  }
}
