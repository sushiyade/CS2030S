/**
 * This class encapsulates a Join Shop Queue event in the shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class JoinShopQueueEvent extends Event {
  /** 
   * The customer associated with this event.
   */
  private Customer customer;

  /**
   * The shop that the event is associated to.
   */
  private Shop shop;

  /**
   * Constructor for a Join Shop Queue event.
   *
   * @param customer The customer associated with
   *                 this event.
   * @param time The time this event occurs.
   * @param shop The shop that the event is associated to.
   */
  public JoinShopQueueEvent(Customer customer, double time, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  /**
   * Returns the string representation of 
   * the Join Shop Queue event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    String str = super.toString() +
                 ": " +
                 this.customer.toString() + 
                 " joined shop queue " + 
                 this.shop.getQueueStr();
    return str;
  }

  /**
   * The logic that the simulation should follow when
   * simulating this Join Shop Queue event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    shop.joinQueue(this.customer);
    // If there is no counter, the customer will leave
    return new Event[] {};
  }
}
