import java.util.Scanner;

/**
 * This class implements a shop simulation.
 *
 * @author Lim Xiu Jia (Group 12A)
 * @version CS2030S AY22/23 Semester 1
 */ 
class ShopSimulation extends Simulation {
  /** 
   * The list of events to populate
   * the simulation with.
   */
  public Event[] initEvents;

  /**
   * The shop that the event is associated to.
   */
  private Shop shop;

  /** 
   * Constructor for a shop simulation. 
   *
   * @param sc A scanner to read the parameters from.  The first
   *           integer scanned is the number of customers; followed
   *           by the number of service counters, max counter queue length (l),
   *           and max Entrance/Shop queue length (m). Next is a 
   *           sequence of (arrival time, service time) pair, each
   *           pair represents a customer.
   */
  public ShopSimulation(Scanner sc) {
    initEvents = new Event[sc.nextInt()];
    int numOfCounters = sc.nextInt();
    int l = sc.nextInt();
    int m = sc.nextInt();
    this.shop = new Shop(numOfCounters, l, m);

    int id = 0;
    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      
      Customer customer = new Customer(id, serviceTime);

      initEvents[id] = new ArrivalEvent(customer, arrivalTime, this.shop);
      id += 1;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}
