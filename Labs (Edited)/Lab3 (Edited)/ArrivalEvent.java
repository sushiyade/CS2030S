/**
 * This class encapsulates an Arrival event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class ArrivalEvent extends Event {
  /** 
   * The customer associated with this event.
   */
  private Customer customer;

  /**
   * The shop that the event is associated to.
   */
  private Shop shop;

  /**
   * Constructor for an Arrival event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param time The time this event occurs.
   * @param shop The shop that the event is associated to.
   */
  public ArrivalEvent(Customer customer, double time, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  /**
   * Returns the string representation of 
   * the Arrival event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    String str = super.toString() + ": " +
                 this.customer.toString() +
                 " arrived " + shop.getQueueStr();
    return str;
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Arrival event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    Counter openCounter = shop.findOpenCounter();
    if (openCounter != null) {
      if (openCounter.getStatus()) {
        return new Event[] {
          new ServiceBeginEvent(this.customer, openCounter, this.getTime(), this.shop)
        };
      } else {
        return new Event[] {
          new JoinCounterQueueEvent(this.customer, this.getTime(), openCounter)
        };
      }
    } else {
      if (!shop.isQueueFull()) {
        return new Event[] {
          new JoinShopQueueEvent(this.customer, this.getTime(), this.shop)
        };
      } else {
        // If there is no counter, the customer will leave
        return new Event[] {
          new DepartureEvent(this.customer, this.getTime())
        };
      }
    }
  }
}
