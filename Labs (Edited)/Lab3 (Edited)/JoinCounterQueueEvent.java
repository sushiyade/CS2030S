/**
 * This class encapsulates a Join Counter Queue event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class JoinCounterQueueEvent extends Event {
  /** 
   * The customer associated with this event.
   */
  private Customer customer;

  /**
   * The counter that the event is associated to.
   */
  private Counter counter;

  /**
   * Constructor for a Join Counter Queue event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param time The time this event occurs.
   * @param counter The counter that the event is associated to.
   */
  public JoinCounterQueueEvent(Customer customer, double time, Counter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  /**
   * Returns the string representation of 
   * the Join Counter Queue event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    String str = super.toString() +
                 ": " +
                 this.customer.toString() + 
                 " joined counter queue (at " + 
                 this.counter.toString() +
                 " " +
                 counter.getQueue() +
                 ")";
    return str;
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Join Counter Queue event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    counter.joinQueue(this.customer);
    // If there is no counter, the customer will leave
    return new Event[] {};
  }
}
