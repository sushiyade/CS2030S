/**
 * This class encapsulates a shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class Shop {
  /**
   * An Array of Counters in the shop.
   */
  private Array<Counter> counters;

  /**
   * The Queue in the shop.
   */
  private Queue<Customer> queue;

  /**
   * Constructor for a shop.
   *
   * @param numOfCounters The total number of counters
   *                      in the Shop.
   * @param l The maximum length of the counter queue
   *          in the Shop.
   * @param m The maximum length of the entrance queue
   *          in the Shop.
   */
  public Shop(int numOfCounters, int l, int m) {
    this.counters = new Array<Counter>(numOfCounters);
    this.queue = new Queue<Customer>(m);

    // Creates the Counters
    for (int i = 0; i < numOfCounters; i++) {
      Counter counter = new Counter(i, l);
      this.counters.set(i, counter);
    }
  }

  /**
   * Returns a counter that is available.
   * If there is no counter, return null.
   * 
   * @return An available counter in the shop.
   */
  public Counter findOpenCounter() {
    Counter counter = counters.min();
    if (counter.getStatus()) {
      return counter;
    } else if (!counter.isQueueFull()) {
      return counter;
    } else {
      return null;
    }
  }

  /**
   * Checks if the queue is full.
   *
   * @return true if the queue is full; false otherwise.
   */
  public boolean isQueueFull() {
    return this.queue.isFull();
  }

  /**
   * Add the customer into the queue.
   *
   * @param customer The customer to put in the queue.
   * @return false if the queue is full;
   *         true if customer is added successfully.
   */
  public boolean joinQueue(Customer customer) {
    return this.queue.enq(customer);
  }

  /**
   * Remove the object from the queue.
   *
   * @return null if the queue is empty;
   *         the object removed from the queue otherwise.
   */
  public Customer leaveQueue() {
    return (Customer) this.queue.deq();
  }
  
  /**
   * Returns the string representation of the queue.
   * 
   * @return A string consisting of the string 
   *         representation of every object in the queue.
   */
  public String getQueueStr() {
    return queue.toString();
  }
}
