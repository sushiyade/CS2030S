/**
 * This clsas encapsulates a counter.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class Counter {
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
   * Constructor for a counter.
   *
   * @param counterId The id of the counter.
   */
  public Counter(int counterId) {
    this.counterId = counterId;
    this.status = true;
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
