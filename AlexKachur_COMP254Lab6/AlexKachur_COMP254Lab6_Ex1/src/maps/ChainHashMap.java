package maps;

import java.util.ArrayList;

/**
 * Map implementation using hash table with separate chaining.
 *
 * MODIFIED for Lab 6 Exercise 1: added constructor accepting a custom max load factor.
 *
 * Original: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * Modified by: Alex Kachur
 */
public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {
  private UnsortedTableMap<K,V>[] table;   // initialized within createTable

  // provide same constructors as base class
  public ChainHashMap() { super(); }
  public ChainHashMap(int cap) { super(cap); }
  public ChainHashMap(int cap, int p) { super(cap, p); }

  /** Creates a hash table with given capacity, prime factor, and max load factor. */
  public ChainHashMap(int cap, int p, double maxLoad) { super(cap, p, maxLoad); }

  @Override
  @SuppressWarnings({"unchecked"})
  protected void createTable() {
    table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
  }

  @Override
  protected V bucketGet(int h, K k) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null) return null;
    return bucket.get(k);
  }

  @Override
  protected V bucketPut(int h, K k, V v) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null)
      bucket = table[h] = new UnsortedTableMap<>();
    int oldSize = bucket.size();
    V answer = bucket.put(k,v);
    n += (bucket.size() - oldSize);
    return answer;
  }

  @Override
  protected V bucketRemove(int h, K k) {
    UnsortedTableMap<K,V> bucket = table[h];
    if (bucket == null) return null;
    int oldSize = bucket.size();
    V answer = bucket.remove(k);
    n -= (oldSize - bucket.size());
    return answer;
  }

  @Override
  public Iterable<Entry<K,V>> entrySet() {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    for (int h=0; h < capacity; h++)
      if (table[h] != null)
        for (Entry<K,V> entry : table[h].entrySet())
          buffer.add(entry);
    return buffer;
  }
}
