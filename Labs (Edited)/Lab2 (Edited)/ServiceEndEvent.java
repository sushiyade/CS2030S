/**
 * This class encapsulates a Service End event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class ServiceEndEvent extends Event {
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
   * Constructor for an Service End event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param counter The counter associated with
   *                this event.
   * @param time The time this event occurs.
   * @param shop The shop that the event is associated to.
   */
  public ServiceEndEvent(Customer customer, Counter counter, double time, Shop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.shop = shop;
  }

  /**
   * Returns the string representation of 
   * the Service End event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    String str = super.toString() + 
                 ": " + 
                 this.customer.toString() + 
                 " service done (by " + 
                 this.counter.toString() + 
                 ")";
    return str;
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
    Customer nextCustomer = shop.leaveQueue();
    if (nextCustomer == null) {
      return new Event[] {
        new DepartureEvent(this.customer, this.getTime())
      };
    } else {
      return new Event[] {
        new DepartureEvent(this.customer, this.getTime()),
        new ServiceBeginEvent(nextCustomer,
                              this.counter,
                              this.getTime(),
                              this.shop)
      };
    }
  }
}
