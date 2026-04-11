package maps;

/**
 * Lab 6 Exercise 2 — Test application for containKey(k).
 *
 * Demonstrates that containKey correctly distinguishes "key not in map"
 * from "key mapped to null value", which get(k) cannot.
 *
 * Author: Alex Kachur
 */
public class TestContainKey {
  public static void main(String[] args) {
    SortedTableMap<Integer, String> map = new SortedTableMap<>();

    // Populate the map — note the legitimate null value at key 1!
    map.put(5, "Apple");
    map.put(2, "Banana");
    map.put(8, "Cherry");
    map.put(1, null);            // legitimate null value — the tricky case

    System.out.println("=======================================");
    System.out.println("  SortedTableMap containKey(k) Test");
    System.out.println("  Lab 6 Exercise 2 - Alex Kachur");
    System.out.println("=======================================");
    System.out.println("Map size: " + map.size());
    System.out.println();

    // Case 1: key exists, non-null value
    System.out.println("containKey(5)  -> " + map.containKey(5)  + "   (expected: true)");
    // Case 2: key exists but value IS null -- the whole point of this exercise
    System.out.println("containKey(1)  -> " + map.containKey(1)  + "   (expected: true)");
    System.out.println("   get(1)      -> " + map.get(1)         + "   <-- null, but key IS present!");
    // Case 3: key does not exist
    System.out.println("containKey(99) -> " + map.containKey(99) + "  (expected: false)");
    // Case 4: boundary — smaller than everything
    System.out.println("containKey(0)  -> " + map.containKey(0)  + "  (expected: false)");
    // Case 5: boundary — larger than everything
    System.out.println("containKey(100)-> " + map.containKey(100)+ "  (expected: false)");
    // Case 6: removing and re-checking
    map.remove(5);
    System.out.println();
    System.out.println("After remove(5):");
    System.out.println("containKey(5)  -> " + map.containKey(5)  + "  (expected: false)");
    System.out.println("containKey(1)  -> " + map.containKey(1)  + "   (expected: true, still null-valued)");

    System.out.println();
    System.out.println("=======================================");
    System.out.println("  Test Complete");
    System.out.println("=======================================");
  }
}
