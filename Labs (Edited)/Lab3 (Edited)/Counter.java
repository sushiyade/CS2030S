/**
 * This clsas encapsulates a counter.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
public class Counter implements Comparable<Counter> {
  /**
   * The id of a counter.
   * First counter has id 0. Next is 1, 2, etc.
   */
  private int counterId;

  /**
   * The status of the counter.
   * If the counter is available, the status is True.
   * Else if the counter is unavailable, the status is False.
   */
  private boolean status;

  /**
   * The queue of the counter.
   */
  private Queue<Customer> queue;

  /**
   * Constructor for a counter.
   *
   * @param counterId The id of the counter.
   * @param l The maximum length of the counter queue.
   */
  public Counter(int counterId, int l) {
    this.counterId = counterId;
    this.status = true;
    this.queue = new Queue<Customer>(l);
  }

  /**
   * Returns the status of the Counter.
   *
   * @return The status of the counter.
   */
  public boolean getStatus() {
    return this.status;
  }

  /**
   * Set status of Counter to true.
   */
  public void setAvailable() {
    this.status = true;
  }

  /**
   * Set status of Counter to false.
   */
  public void setUnavailable() {
    this.status = false;
  }

  public boolean isQueueFull() {
    return this.queue.isFull();
  }

  public boolean joinQueue(Customer customer){
    return this.queue.enq(customer);
  }

  public Customer leaveQueue() {
    return this.queue.deq();
  }

  public String getQueue() {
    return queue.toString();
  }

  @Override
  public int compareTo(Counter nextCounter) {
    int currLength = this.queue.length();
    int nextCounterLength = nextCounter.queue.length();

    if (this.status == true && nextCounter.status == true) {
      return -1;
    } else if (this.status == true && nextCounter.status == false) {
      return -1;
    } else if (this.status == false && nextCounter.status == false) {
      if (currLength == nextCounterLength) {
        if (this.counterId > nextCounter.counterId) {
          return 1;
        } else {
          return -1;
        }
      } else if (currLength > nextCounterLength) {
        return 1;
      } else {
        return -1;
      }
    } else {
      return 1;
    }
  }

  /**
   * Returns the string representation of the Counter.
   *
   * @return A string representing the counter.
   */
  @Override
  public String toString() {
    return "S" + this.counterId;
  }
}
