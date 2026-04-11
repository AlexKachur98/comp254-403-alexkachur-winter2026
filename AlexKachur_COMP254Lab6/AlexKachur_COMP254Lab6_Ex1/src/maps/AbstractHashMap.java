package maps;

import java.util.ArrayList;
import java.util.Random;

/**
 * An abstract base class supporting Map implementations that use hash
 * tables with MAD compression.
 *
 * MODIFIED for Lab 6 Exercise 1: the maximum load factor is now configurable
 * (via constructor or setMaxLoad). Default remains 0.5 to match the textbook.
 *
 * Original: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * Modified by: Alex Kachur
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
  protected int n = 0;                 // number of entries in the dictionary
  protected int capacity;              // length of the table
  private int prime;                   // prime factor
  private long scale, shift;           // the shift and scaling factors
  protected double maxLoad = 0.5;      // maximum load factor (configurable, default 0.5)

  /** Creates a hash table with the given capacity, prime factor, and max load factor. */
  public AbstractHashMap(int cap, int p, double maxLoad) {
    prime = p;
    capacity = cap;
    this.maxLoad = maxLoad;
    Random rand = new Random();
    scale = rand.nextInt(prime-1) + 1;
    shift = rand.nextInt(prime);
    createTable();
  }

  /** Creates a hash table with the given capacity and prime factor (default maxLoad 0.5). */
  public AbstractHashMap(int cap, int p) { this(cap, p, 0.5); }

  /** Creates a hash table with given capacity and prime factor 109345121. */
  public AbstractHashMap(int cap) { this(cap, 109345121); }

  /** Creates a hash table with capacity 17 and prime factor 109345121. */
  public AbstractHashMap() { this(17); }

  /** Allows the user to change the max load factor after construction. */
  public void setMaxLoad(double maxLoad) { this.maxLoad = maxLoad; }

  /** Returns the current max load factor. */
  public double getMaxLoad() { return maxLoad; }

  // public methods
  @Override
  public int size() { return n; }

  @Override
  public V get(K key) { return bucketGet(hashValue(key), key); }

  @Override
  public V remove(K key) { return bucketRemove(hashValue(key), key); }

  @Override
  public V put(K key, V value) {
    V answer = bucketPut(hashValue(key), key, value);
    if ((double) n / capacity > maxLoad)    // keep load factor <= maxLoad (configurable)
      resize(2 * capacity - 1);             // (or find a nearby prime)
    return answer;
  }

  // private utilities
  /** Hash function applying MAD method to default hash code. */
  private int hashValue(K key) {
    return (int) ((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
  }

  /** Updates the size of the hash table and rehashes all entries. */
  private void resize(int newCap) {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
    for (Entry<K,V> e : entrySet())
      buffer.add(e);
    capacity = newCap;
    createTable();                     // based on updated capacity
    n = 0;                             // will be recomputed while reinserting entries
    for (Entry<K,V> e : buffer)
      put(e.getKey(), e.getValue());
  }

  // protected abstract methods to be implemented by subclasses
  protected abstract void createTable();
  protected abstract V bucketGet(int h, K k);
  protected abstract V bucketPut(int h, K k, V v);
  protected abstract V bucketRemove(int h, K k);
}
