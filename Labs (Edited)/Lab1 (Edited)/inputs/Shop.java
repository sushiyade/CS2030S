/**
 * This class encapsulates a shop.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */
class Shop {
  /**
   * The list of Counters in the shop.
   */
  private Counter[] counters;

  /**
   * Constructor for a shop
   *
   * @param numOfCounters The total number of counters
   *                      in the Shop.
   */
  public Shop(int numOfCounters) {
    Counter[] counters = new Counter[numOfCounters];

    // Creates the Counters
    for (int i = 0; i < numOfCounters; i++) {
      Counter counter = new Counter(i);
      counters[i] = counter;
    }

    this.counters = counters;
  }

  /**
   * Returns a counter that is available.
   * If there is no counter, return null.
   * 
   * @return An available counter in the shop.
   */
  public Counter findOpenCounter() {
    int numOfCounters = this.counters.length;

    for (int i = 0; i < numOfCounters; i++) {
      if (this.counters[i].getStatus()) {
        return this.counters[i];
      }
    }
    return null;
  }
}
