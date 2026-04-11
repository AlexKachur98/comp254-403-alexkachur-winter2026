package maps;

import java.util.Random;

/**
 * Lab 6 Exercise 1 — Test application.
 *
 * Measures insertion time for ChainHashMap and ProbeHashMap across different
 * max load factors, using random integer keys with a fixed seed for fair comparison.
 *
 * Author: Alex Kachur
 */
public class TestLoadFactor {

  private static final int NUM_KEYS = 50_000;
  private static final int INITIAL_CAPACITY = 17;
  private static final double[] LOAD_FACTORS = {0.25, 0.5, 0.75, 0.9};

  public static void main(String[] args) {
    System.out.println("============================================");
    System.out.println("  Hash Map Load Factor Experiment");
    System.out.println("  Lab 6 Exercise 1 - Alex Kachur");
    System.out.println("============================================");
    System.out.println("Inserting " + NUM_KEYS + " random integer keys per trial.");
    System.out.println("Initial capacity: " + INITIAL_CAPACITY);
    System.out.println();

    System.out.printf("%-18s %-12s %-15s%n", "Map Type", "Max Load", "Time (ms)");
    System.out.println("--------------------------------------------");

    for (double load : LOAD_FACTORS) {
      long chainTime = timeInsertions(
          new ChainHashMap<Integer,Integer>(INITIAL_CAPACITY, 109345121, load));
      System.out.printf("%-18s %-12.2f %-15d%n", "ChainHashMap", load, chainTime);
    }
    System.out.println();
    for (double load : LOAD_FACTORS) {
      long probeTime = timeInsertions(
          new ProbeHashMap<Integer,Integer>(INITIAL_CAPACITY, 109345121, load));
      System.out.printf("%-18s %-12.2f %-15d%n", "ProbeHashMap", load, probeTime);
    }

    System.out.println();
    System.out.println("============================================");
    System.out.println("  Observations:");
    System.out.println("  - Higher load = fewer resizes but more collisions.");
    System.out.println("  - ProbeHashMap degrades more at high loads due to");
    System.out.println("    primary clustering in linear probing.");
    System.out.println("  - ChainHashMap degrades more gracefully because");
    System.out.println("    collisions just lengthen bucket lists.");
    System.out.println("============================================");

    // Quick correctness check: verify setMaxLoad works on a live instance
    System.out.println();
    System.out.println("Correctness check: setMaxLoad() on a live map");
    ChainHashMap<Integer,String> m = new ChainHashMap<>();
    System.out.println("  initial maxLoad = " + m.getMaxLoad());
    m.setMaxLoad(0.75);
    System.out.println("  after setMaxLoad(0.75) = " + m.getMaxLoad());
    m.put(1, "one"); m.put(2, "two"); m.put(3, "three");
    System.out.println("  put 3 entries, size = " + m.size() + ", get(2) = " + m.get(2));
  }

  /** Inserts NUM_KEYS random integer keys into the given map and returns elapsed ms. */
  private static long timeInsertions(AbstractHashMap<Integer,Integer> map) {
    Random rand = new Random(42);                    // fixed seed for fairness
    long start = System.currentTimeMillis();
    for (int i = 0; i < NUM_KEYS; i++) {
      int key = rand.nextInt(Integer.MAX_VALUE);
      map.put(key, i);
    }
    return System.currentTimeMillis() - start;
  }
}
