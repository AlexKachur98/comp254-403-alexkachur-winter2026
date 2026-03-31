package com.alexkachur.exercise3;

/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a priority queue using an array-based heap.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
  /** primary collection of priority queue entries */
  protected ArrayList<Entry<K,V>> heap = new ArrayList<>();

  /** Creates an empty priority queue based on the natural ordering of its keys. */
  public HeapPriorityQueue() { super(); }

  /**
   * Creates an empty priority queue using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the priority queue
   */
  public HeapPriorityQueue(Comparator<K> comp) { super(comp); }

  /**
   * Creates a priority queue initialized with the respective
   * key-value pairs.  The two arrays given will be paired
   * element-by-element. They are presumed to have the same
   * length. (If not, entries will be created only up to the length of
   * the shorter of the arrays)
   * @param keys an array of the initial keys for the priority queue
   * @param values an array of the initial values for the priority queue
   */
  public HeapPriorityQueue(K[] keys, V[] values) {
    super();
    for (int j=0; j < Math.min(keys.length, values.length); j++)
      heap.add(new PQEntry<>(keys[j], values[j]));
    heapify();
  }

  // protected utilities
  protected int parent(int j) { return (j-1) / 2; }     // truncating division
  protected int left(int j) { return 2*j + 1; }
  protected int right(int j) { return 2*j + 2; }
  protected boolean hasLeft(int j) { return left(j) < heap.size(); }
  protected boolean hasRight(int j) { return right(j) < heap.size(); }

  /** Exchanges the entries at indices i and j of the array list. */
  protected void swap(int i, int j) {
    Entry<K,V> temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  // =====================================================================
  // EXERCISE 3: Recursive upheap - Alex Kachur
  // Alternative implementation using recursion instead of a loop.
  // Does a single upward swap and recurs if necessary.
  // Based on textbook's HeapPriorityQueue upheap (Ch. 9)
  // =====================================================================

  /**
   * Moves the entry at index j higher, if necessary, to restore the heap property.
   * This is a recursive implementation (no loop).
   * Does a single upward swap and recurs if necessary.
   *
   * @param j the index of the entry to move up
   */
  protected void upheap(int j) {
    // Base case 1: j is the root, cannot go higher
    if (j == 0) return;

    int p = parent(j);

    // Base case 2: heap property is satisfied (parent key <= j's key)
    if (compare(heap.get(j), heap.get(p)) >= 0) return;

    // Recursive case: parent key > j's key, so swap and recur
    swap(j, p);
    upheap(p);    // recur at the parent's position
  }

  /** Moves the entry at index j lower, if necessary, to restore the heap property. */
  protected void downheap(int j) {
    while (hasLeft(j)) {               // continue to bottom (or break statement)
      int leftIndex = left(j);
      int smallChildIndex = leftIndex;     // although right may be smaller
      if (hasRight(j)) {
          int rightIndex = right(j);
          if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
            smallChildIndex = rightIndex;  // right child is smaller
      }
      if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
        break;                             // heap property has been restored
      swap(j, smallChildIndex);
      j = smallChildIndex;                 // continue at position of the child
    }
  }

  /** Performs a bottom-up construction of the heap in linear time. */
  protected void heapify() {
    int startIndex = parent(size()-1);    // start at PARENT of last entry
    for (int j=startIndex; j >= 0; j--)   // loop until processing the root
      downheap(j);
  }

  // public methods

  /**
   * Returns the number of items in the priority queue.
   * @return number of items
   */
  @Override
  public int size() { return heap.size(); }

  /**
   * Returns (but does not remove) an entry with minimal key.
   * @return entry having a minimal key (or null if empty)
   */
  @Override
  public Entry<K,V> min() {
    if (heap.isEmpty()) return null;
    return heap.get(0);
  }

  /**
   * Inserts a key-value pair and return the entry created.
   * @param key     the key of the new entry
   * @param value   the associated value of the new entry
   * @return the entry storing the new key-value pair
   * @throws IllegalArgumentException if the key is unacceptable for this queue
   */
  @Override
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    checkKey(key);      // auxiliary key-checking method (could throw exception)
    Entry<K,V> newest = new PQEntry<>(key, value);
    heap.add(newest);                      // add to the end of the list
    upheap(heap.size() - 1);               // upheap newly added entry
    return newest;
  }

  /**
   * Removes and returns an entry with minimal key.
   * @return the removed entry (or null if empty)
   */
  @Override
  public Entry<K,V> removeMin() {
    if (heap.isEmpty()) return null;
    Entry<K,V> answer = heap.get(0);
    swap(0, heap.size() - 1);              // put minimum item at the end
    heap.remove(heap.size() - 1);          // and remove it from the list;
    downheap(0);                           // then fix new root
    return answer;
  }

  /** Used for debugging purposes only */
  private void sanityCheck() {
    for (int j=0; j < heap.size(); j++) {
      int left = left(j);
      int right = right(j);
      if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0)
        System.out.println("Invalid left child relationship");
      if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0)
        System.out.println("Invalid right child relationship");
    }
  }
  //
 

  public static void main(String[] args) {
    System.out.println("=== COMP-254 Lab Assignment 5 Exercise 3 ===");
    System.out.println("Student: Alex Kachur");
    System.out.println("Recursive upheap for HeapPriorityQueue");
    System.out.println();

    // ---------------------------------------------------------------
    // Test 1: Insert elements and verify heap order is maintained
    // Using the same keys from the bottom-up heap construction example
    // ---------------------------------------------------------------
    System.out.println("Test 1: Inserting elements one by one");
    System.out.println("--------------------------------------");
    HeapPriorityQueue<Integer, String> heap = new HeapPriorityQueue<>();
    heap.insert(47, "A");
    heap.insert(75, "B");
    heap.insert(28, "C");
    heap.insert(51, "D");
    heap.insert(31, "E");
    heap.insert(22, "F");
    heap.insert(15, "G");

    System.out.println("Heap contents after insertions:");
    for (int i = 0; i < heap.size(); i++) {
      System.out.println("  Index " + i + ": (" + heap.heap.get(i).getKey()
          + ", " + heap.heap.get(i).getValue() + ")");
    }
    System.out.println("Min element: (" + heap.min().getKey() + ", " + heap.min().getValue() + ")");
    System.out.println("Expected min key: 15");
    System.out.println(heap.min().getKey() == 15 ? "PASS" : "FAIL");
    System.out.println();

    // ---------------------------------------------------------------
    // Test 2: RemoveMin to verify heap-sort works correctly
    // ---------------------------------------------------------------
    System.out.println("Test 2: Removing elements in sorted order (heap-sort)");
    System.out.println("-----------------------------------------------------");
    StringBuilder sortedOrder = new StringBuilder();
    int heapSize = heap.size();
    for (int i = 0; i < heapSize; i++) {
      Entry<Integer, String> removed = heap.removeMin();
      System.out.println("  Removed: (" + removed.getKey() + ", " + removed.getValue() + ")");
      if (sortedOrder.length() > 0) sortedOrder.append(", ");
      sortedOrder.append(removed.getKey());
    }
    System.out.println("Sorted order: " + sortedOrder);
    System.out.println("Expected:     15, 22, 28, 31, 47, 51, 75");
    System.out.println(sortedOrder.toString().equals("15, 22, 28, 31, 47, 51, 75") ? "PASS" : "FAIL");
    System.out.println();

    // ---------------------------------------------------------------
    // Test 3: Insert in reverse sorted order (worst case for upheap)
    // Every insert requires swapping all the way up to the root
    // ---------------------------------------------------------------
    System.out.println("Test 3: Inserting in reverse sorted order (worst case)");
    System.out.println("------------------------------------------------------");
    HeapPriorityQueue<Integer, String> heap2 = new HeapPriorityQueue<>();
    int[] reverseKeys = {50, 40, 30, 20, 10, 5, 1};
    for (int key : reverseKeys) {
      heap2.insert(key, "V" + key);
      System.out.println("  Inserted " + key + ", current min: " + heap2.min().getKey());
    }
    System.out.println("Final min: " + heap2.min().getKey() + "  [expected: 1]  "
        + (heap2.min().getKey() == 1 ? "PASS" : "FAIL"));
    System.out.println();

    // ---------------------------------------------------------------
    // Test 4: Insert in already sorted order (best case for upheap)
    // ---------------------------------------------------------------
    System.out.println("Test 4: Inserting in sorted order (best case)");
    System.out.println("----------------------------------------------");
    HeapPriorityQueue<Integer, String> heap3 = new HeapPriorityQueue<>();
    int[] sortedKeys = {1, 5, 10, 20, 30, 40, 50};
    for (int key : sortedKeys) {
      heap3.insert(key, "V" + key);
    }
    System.out.println("Min: " + heap3.min().getKey() + "  [expected: 1]  "
        + (heap3.min().getKey() == 1 ? "PASS" : "FAIL"));
    StringBuilder sorted2 = new StringBuilder();
    int size3 = heap3.size();
    for (int i = 0; i < size3; i++) {
      Entry<Integer, String> removed = heap3.removeMin();
      if (sorted2.length() > 0) sorted2.append(", ");
      sorted2.append(removed.getKey());
    }
    System.out.println("Heap-sort: " + sorted2);
    System.out.println("Expected:  1, 5, 10, 20, 30, 40, 50");
    System.out.println(sorted2.toString().equals("1, 5, 10, 20, 30, 40, 50") ? "PASS" : "FAIL");
    System.out.println();

    // ---------------------------------------------------------------
    // Test 5: Single element
    // ---------------------------------------------------------------
    System.out.println("Test 5: Single element");
    HeapPriorityQueue<Integer, String> heap4 = new HeapPriorityQueue<>();
    heap4.insert(42, "Only");
    System.out.println("Min: " + heap4.min().getKey() + "  [expected: 42]  "
        + (heap4.min().getKey() == 42 ? "PASS" : "FAIL"));

    System.out.println();
    System.out.println("=== All tests completed ===");
  }

  //
}
