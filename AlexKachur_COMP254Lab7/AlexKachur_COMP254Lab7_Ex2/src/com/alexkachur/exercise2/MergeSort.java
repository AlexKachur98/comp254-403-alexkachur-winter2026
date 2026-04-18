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



package com.alexkachur.exercise2;
import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

  //-------- support for top-down merge-sort of arrays --------
  /** Merge contents of arrays S1 and S2 into properly sized array S. */
  public static <K> void merge(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
    int i = 0, j = 0;
    while (i + j < S.length) {
      if (j == S2.length || (i < S1.length && comp.compare(S1[i], S2[j]) < 0))
        S[i+j] = S1[i++];                     // copy ith element of S1 and increment i
      else
        S[i+j] = S2[j++];                     // copy jth element of S2 and increment j
    }
  }

  /** Merge-sort contents of array S. */
  public static <K> void mergeSort(K[] S, Comparator<K> comp) {
    int n = S.length;
    if (n < 2) return;                        // array is trivially sorted
    // divide
    int mid = n/2;
    K[] S1 = Arrays.copyOfRange(S, 0, mid);   // copy of first half
    K[] S2 = Arrays.copyOfRange(S, mid, n);   // copy of second half
    // conquer (with recursion)
    mergeSort(S1, comp);                      // sort copy of first half
    mergeSort(S2, comp);                      // sort copy of second half
    // merge results
    merge(S1, S2, S, comp);               // merge sorted halves back into original
  }

  //-------- support for top-down merge-sort of queues --------
  /** Merge contents of sorted queues S1 and S2 into empty queue S. */
  public static <K> void merge(Queue<K> S1, Queue<K> S2, Queue<K> S,
                                                        Comparator<K> comp) {
    while (!S1.isEmpty() && !S2.isEmpty()) {
      if (comp.compare(S1.first(), S2.first()) < 0)
        S.enqueue(S1.dequeue());           // take next element from S1
      else
        S.enqueue(S2.dequeue());           // take next element from S2
    }
    while (!S1.isEmpty())
      S.enqueue(S1.dequeue());             // move any elements that remain in S1
    while (!S2.isEmpty())
      S.enqueue(S2.dequeue());             // move any elements that remain in S2
  }

  /** Merge-sort contents of queue. */
  public static <K> void mergeSort(Queue<K> S, Comparator<K> comp) {
    int n = S.size();
    if (n < 2) return;                     // queue is trivially sorted
    // divide
    Queue<K> S1 = new LinkedQueue<>();     // (or any queue implementation)
    Queue<K> S2 = new LinkedQueue<>();
    while (S1.size() < n/2)
      S1.enqueue(S.dequeue());             // move the first n/2 elements to S1
    while (!S.isEmpty())
      S2.enqueue(S.dequeue());             // move remaining elements to S2
    // conquer (with recursion)
    mergeSort(S1, comp);                   // sort first half
    mergeSort(S2, comp);                   // sort second half
    // merge results
    merge(S1, S2, S, comp);                // merge sorted halves back into original
  }

  //-------- support for bottom-up merge-sort of arrays --------
  /** Merges in[start..start+inc-1] and in[start+inc..start+2*inc-1] into out. */
  public static <K> void merge(K[] in, K[] out, Comparator<K> comp,
                                                       int start, int inc) {
    int end1 = Math.min(start + inc, in.length);      // boundary for run 1
    int end2 = Math.min(start + 2 * inc, in.length);  // boundary for run 2
    int x=start;                                      // index into run 1
    int y=start+inc;                                  // index into run 2
    int z=start;                                      // index into output
    while (x < end1 && y < end2)
      if (comp.compare(in[x], in[y]) < 0)
        out[z++] = in[x++];                           // take next from run 1
      else
        out[z++] = in[y++];                           // take next from run 2
    if (x < end1) System.arraycopy(in, x, out, z, end1 - x);       // copy rest of run 1
    else if (y < end2) System.arraycopy(in, y, out, z, end2 - y);  // copy rest of run 2
  }

  @SuppressWarnings({"unchecked"})
  /** Merge-sort contents of data array. */
  public static <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {
    int n = orig.length;
    K[] src = orig;                                   // alias for the original
    K[] dest = (K[]) new Object[n];                   // make a new temporary array
    K[] temp;                                         // reference used only for swapping
    for (int i=1; i < n; i *= 2) {                    // each iteration sorts all runs of length i
      for (int j=0; j < n; j += 2*i)                  // each pass merges two runs of length i
        merge(src, dest, comp, j, i);
      temp = src; src = dest; dest = temp;      // reverse roles of the arrays
    }
    if (orig != src)
      System.arraycopy(src, 0, orig, 0, n);           // additional copy to get result to original
  }
  

  // =====================================================================
  // EXERCISE 2: Bottom-up merge-sort using a QUEUE OF QUEUES - Alex Kachur
  // =====================================================================
  //
  // The textbook provides a top-down recursive queue merge-sort (lines above).
  // This is an iterative BOTTOM-UP version:
  //   1. Wrap each element of S in its own singleton queue.
  //   2. Put all singleton queues into a master "queue of queues".
  //   3. Repeatedly dequeue two queues from the master, merge them using
  //      the textbook's existing merge(Queue,Queue,Queue,Comparator), and
  //      enqueue the merged result back to the master.
  //   4. When only one queue remains in the master, it IS the sorted result.
  //      Move its contents back into S.
  //
  // Runs in O(n log n) time: each of the log n passes processes all n items.
  // Uses only queue operations - no array indexing, no recursion.
  // =====================================================================
  public static <K> void mergeSortBottomUpQueue(Queue<K> S, Comparator<K> comp) {
    int n = S.size();
    if (n < 2) return;                                // already sorted

    // Step 1 + 2: each item in its own queue, all inside a master queue
    Queue<Queue<K>> master = new LinkedQueue<>();
    while (!S.isEmpty()) {
      Queue<K> singleton = new LinkedQueue<>();
      singleton.enqueue(S.dequeue());                 // wrap one item
      master.enqueue(singleton);                      // park it in the master
    }

    // Step 3: repeatedly merge two queues from the front of the master
    while (master.size() > 1) {
      Queue<K> Q1 = master.dequeue();                 // pull first queue
      Queue<K> Q2 = master.dequeue();                 // pull second queue
      Queue<K> merged = new LinkedQueue<>();
      merge(Q1, Q2, merged, comp);                    // textbook helper
      master.enqueue(merged);                         // put merged result back
    }

    // Step 4: the single remaining queue is sorted - pour it back into S
    Queue<K> sorted = master.dequeue();
    while (!sorted.isEmpty())
      S.enqueue(sorted.dequeue());
  }
  // =====================================================================
  // END EXERCISE 2
  // =====================================================================

public static void main(String[] args) {
    System.out.println("========================================================");
    System.out.println("  LAB 7 EXERCISE 2 - Bottom-up Merge Sort (queue of queues)");
    System.out.println("  Alex Kachur");
    System.out.println("========================================================");

    // Reusable Integer comparator
    Comparator<Integer> intComp = new Comparator<Integer>() {
      public int compare(Integer i1, Integer i2) { return i1.compareTo(i2); }
    };
    // Reusable String comparator
    Comparator<String> strComp = new Comparator<String>() {
      public int compare(String s1, String s2) { return s1.compareTo(s2); }
    };

    // --------------------------------------------------------------------
    // TEST 1: Textbook example [85, 24, 63, 45, 17, 31, 96, 50]
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 1: Textbook example (Integer) ---");
    Queue<Integer> q1 = buildIntQueue(new Integer[]{85,24,63,45,17,31,96,50});
    System.out.println("  before: " + queueString(q1));
    mergeSortBottomUpQueue(q1, intComp);
    System.out.println("  after:  " + queueString(q1));
    String expected1 = "[17, 24, 31, 45, 50, 63, 85, 96]";
    System.out.println("  " + (queueString(q1).equals(expected1) ? "PASS" : "FAIL"));

    // --------------------------------------------------------------------
    // TEST 2: Strings
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 2: Strings ---");
    Queue<String> q2 = buildStringQueue(new String[]{
        "banana","apple","cherry","date","elderberry","fig","grape"});
    System.out.println("  before: " + queueString(q2));
    mergeSortBottomUpQueue(q2, strComp);
    System.out.println("  after:  " + queueString(q2));
    String expected2 = "[apple, banana, cherry, date, elderberry, fig, grape]";
    System.out.println("  " + (queueString(q2).equals(expected2) ? "PASS" : "FAIL"));

    // --------------------------------------------------------------------
    // TEST 3: Empty queue
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 3: Empty queue ---");
    Queue<Integer> q3 = new LinkedQueue<>();
    mergeSortBottomUpQueue(q3, intComp);
    System.out.println("  result: " + queueString(q3) + "   [" +
                       (q3.size() == 0 ? "PASS" : "FAIL") + "]");

    // --------------------------------------------------------------------
    // TEST 4: Singleton queue
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 4: Single-element queue ---");
    Queue<Integer> q4 = buildIntQueue(new Integer[]{42});
    mergeSortBottomUpQueue(q4, intComp);
    System.out.println("  result: " + queueString(q4) + "   [" +
                       ("[42]".equals(queueString(q4)) ? "PASS" : "FAIL") + "]");

    // --------------------------------------------------------------------
    // TEST 5: Already sorted
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 5: Already sorted ---");
    Queue<Integer> q5 = buildIntQueue(new Integer[]{1,2,3,4,5,6,7,8});
    mergeSortBottomUpQueue(q5, intComp);
    String expected5 = "[1, 2, 3, 4, 5, 6, 7, 8]";
    System.out.println("  result: " + queueString(q5) + "   [" +
                       (queueString(q5).equals(expected5) ? "PASS" : "FAIL") + "]");

    // --------------------------------------------------------------------
    // TEST 6: Reverse-sorted
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 6: Reverse sorted ---");
    Queue<Integer> q6 = buildIntQueue(new Integer[]{9,8,7,6,5,4,3,2,1});
    mergeSortBottomUpQueue(q6, intComp);
    String expected6 = "[1, 2, 3, 4, 5, 6, 7, 8, 9]";
    System.out.println("  result: " + queueString(q6) + "   [" +
                       (queueString(q6).equals(expected6) ? "PASS" : "FAIL") + "]");

    // --------------------------------------------------------------------
    // TEST 7: Duplicates
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 7: Duplicates ---");
    Queue<Integer> q7 = buildIntQueue(new Integer[]{3,1,4,1,5,9,2,6,5,3,5});
    System.out.println("  before: " + queueString(q7));
    mergeSortBottomUpQueue(q7, intComp);
    System.out.println("  after:  " + queueString(q7));
    String expected7 = "[1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]";
    System.out.println("  " + (queueString(q7).equals(expected7) ? "PASS" : "FAIL"));

    // --------------------------------------------------------------------
    // TEST 8: Odd size (exercises uneven master-queue pairing)
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 8: Odd size (7 elements) ---");
    Queue<Integer> q8 = buildIntQueue(new Integer[]{70,30,50,10,60,20,40});
    System.out.println("  before: " + queueString(q8));
    mergeSortBottomUpQueue(q8, intComp);
    System.out.println("  after:  " + queueString(q8));
    String expected8 = "[10, 20, 30, 40, 50, 60, 70]";
    System.out.println("  " + (queueString(q8).equals(expected8) ? "PASS" : "FAIL"));

    // --------------------------------------------------------------------
    // TEST 9: Larger random-ish dataset (25 elements)
    // --------------------------------------------------------------------
    System.out.println("\n--- TEST 9: Larger dataset (25 elements) ---");
    Integer[] data9 = {23,7,91,4,56,12,88,33,67,15,2,99,41,78,29,5,
                       61,19,84,37,8,52,71,26,45};
    Queue<Integer> q9 = buildIntQueue(data9);
    System.out.println("  before: " + queueString(q9));
    mergeSortBottomUpQueue(q9, intComp);
    System.out.println("  after:  " + queueString(q9));
    // verify sorted
    boolean sorted = isSortedString(queueString(q9));
    System.out.println("  " + (sorted ? "PASS: queue is sorted" : "FAIL: not sorted"));

    System.out.println("\n========================================================");
    System.out.println("  Exercise 2 complete.");
    System.out.println("========================================================");
  }

  // ---- helpers used only by main() -----------------------------------------
  private static Queue<Integer> buildIntQueue(Integer[] arr) {
    Queue<Integer> q = new LinkedQueue<>();
    for (Integer x : arr) q.enqueue(x);
    return q;
  }

  private static Queue<String> buildStringQueue(String[] arr) {
    Queue<String> q = new LinkedQueue<>();
    for (String x : arr) q.enqueue(x);
    return q;
  }

  /** Returns a [a, b, c] string form of a queue, non-destructively. */
  private static <K> String queueString(Queue<K> q) {
    StringBuilder sb = new StringBuilder("[");
    int n = q.size();
    for (int i = 0; i < n; i++) {
      K val = q.dequeue();
      sb.append(val);
      if (i < n - 1) sb.append(", ");
      q.enqueue(val);                   // put it back to preserve the queue
    }
    sb.append("]");
    return sb.toString();
  }

  /** Parses "[a, b, c]" and verifies a <= b <= c ... */
  private static boolean isSortedString(String s) {
    String inner = s.substring(1, s.length() - 1);
    String[] parts = inner.split(", ");
    for (int i = 1; i < parts.length; i++)
      if (Integer.parseInt(parts[i-1]) > Integer.parseInt(parts[i])) return false;
    return true;
  }
}
