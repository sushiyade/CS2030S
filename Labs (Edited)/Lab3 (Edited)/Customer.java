/**
 * This class is a Customer.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class Customer {
  /**
   * The id of a customer.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private int customerId;
  /**
   * The service time of the customer.
   */
  private double serviceTime;

  /**
   * Constructor for a customer.
   *
   * @param customerId The customer Id
   * @param serviceTime The time this customer takes for service.
   *
   */
  public Customer(int customerId, double serviceTime) {
    this.customerId = customerId;
    this.serviceTime = serviceTime;
  }

  /**
   * Returns the string representation of the Customer.
   *
   * @return A string representing the customer.
   */
  @Override
  public String toString() {
    return "C" + this.customerId;
  }

  /**
   * Returns the service time of the Customer.
   *
   * @return The service time field
   */
  public double getServiceTime() {
    return this.serviceTime;
  }
}
