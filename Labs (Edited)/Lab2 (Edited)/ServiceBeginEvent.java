/**
 * This class encapsulates a Service Begin event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class ServiceBeginEvent extends Event {
  /** 
   * The customer associated with this event.
   */
  private Customer customer;

  /** 
   * The counter associated with this event.
   */
  private Counter counter;

  /**
   * The shop that the event is associated to.
   */
  private Shop shop;

  /**
   * Constructor for an Service Begin event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param counter The counter associated with
   *                this event.
   * @param time The time this event occurs.
   * @param shop The shop that the event is associated to.
   */
  public ServiceBeginEvent(Customer customer, Counter counter, double time, Shop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.shop = shop;
  }

  /**
   * Returns the string representation of 
   * the Service Begin event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    return super.toString() + ": " + this.customer.toString() +
      " service begin (by " + this.counter.toString() + ")";
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Service Begin event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    this.counter.setUnavailable();
    double endTime = this.getTime() + this.customer.getServiceTime();
    return new Event[] {
      new ServiceEndEvent(this.customer, this.counter, endTime, this.shop)
    };
  }
}
