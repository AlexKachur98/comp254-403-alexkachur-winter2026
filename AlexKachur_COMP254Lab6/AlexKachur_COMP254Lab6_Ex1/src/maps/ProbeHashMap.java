package maps;

import java.util.ArrayList;

/**
 * Map implementation using hash table with linear probing (open addressing).
 *
 * Written for Lab 6 Exercise 1, following the textbook Chapter 10 pattern
 * (Goodrich, Tamassia, Goldwasser — Data Structures and Algorithms in Java, 6th ed.).
 *
 * Author: Alex Kachur
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
  private MapEntry<K,V>[] table;                  // fixed-capacity array
  private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); // tombstone marker

  // provide same constructors as base class
  public ProbeHashMap() { super(); }
  public ProbeHashMap(int cap) { super(cap); }
  public ProbeHashMap(int cap, int p) { super(cap, p); }

  /** Creates a hash table with given capacity, prime factor, and max load factor. */
  public ProbeHashMap(int cap, int p, double maxLoad) { super(cap, p, maxLoad); }

  /** Creates an empty table having length equal to current capacity. */
  @Override
  @SuppressWarnings({"unchecked"})
  protected void createTable() {
    table = (MapEntry<K,V>[]) new MapEntry[capacity];
  }

  /** Returns true if location is either empty or the "defunct" tombstone. */
  private boolean isAvailable(int j) {
    return (table[j] == null || table[j] == DEFUNCT);
  }

  /**
   * Searches for an entry with key equal to k in bucket with hash value h,
   * returning the index of such an entry if found, or else the index of an
   * available slot (negative by convention, encoded as -(a+1)).
   */
  private int findSlot(int h, K k) {
    int avail = -1;                               // no slot available yet
    int j = h;                                    // index while scanning table
    do {
      if (isAvailable(j)) {                       // may be either empty or defunct
        if (avail == -1) avail = j;               // remember first available slot
        if (table[j] == null) break;              // if empty, search fails immediately
      } else if (table[j].getKey().equals(k))
        return j;                                 // successful match
      j = (j+1) % capacity;                       // keep probing (cyclically)
    } while (j != h);
    return -(avail + 1);                          // search has failed
  }

  @Override
  protected V bucketGet(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) return null;                       // no match found
    return table[j].getValue();
  }

  @Override
  protected V bucketPut(int h, K k, V v) {
    int j = findSlot(h, k);
    if (j >= 0)                                   // this key has an existing entry
      return table[j].setValue(v);
    table[-(j+1)] = new MapEntry<>(k, v);         // convert to proper index
    n++;
    return null;
  }

  @Override
  protected V bucketRemove(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) return null;                       // nothing to remove
    V answer = table[j].getValue();
    table[j] = DEFUNCT;                           // mark this slot as deactivated
    n--;
    return answer;
  }

  @Override
  public Iterable<Entry<K,V>> entrySet() {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    for (int h = 0; h < capacity; h++)
      if (!isAvailable(h)) buffer.add(table[h]);
    return buffer;
  }
}
