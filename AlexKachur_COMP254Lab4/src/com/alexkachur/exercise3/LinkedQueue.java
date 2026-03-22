/*
 * Base class from: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser
 * concatenate() method: Author: Alex Kachur — Lab Assignment 4
 */
package com.alexkachur.exercise3;

public class LinkedQueue<E> implements Queue<E> {

    // SinglyLinkedList is package-private visibility — accessible within same package
    SinglyLinkedList<E> list = new SinglyLinkedList<>();

    public LinkedQueue() { }

    @Override public int size()         { return list.size(); }
    @Override public boolean isEmpty()  { return list.isEmpty(); }
    @Override public void enqueue(E e)  { list.addLast(e); }    // add to back
    @Override public E first()          { return list.first(); } // peek front
    @Override public E dequeue()        { return list.removeFirst(); } // remove front

    /**
     * Appends all elements of Q2 to the end of this queue in O(1) time.
     * After this call, Q2 will be an empty queue.
     *
     * Author: Alex Kachur — original implementation for Lab Assignment 4
     */
    public void concatenate(LinkedQueue<E> Q2) {
        list.concatenate(Q2.list);  // delegate O(1) work to SinglyLinkedList
    }

    public String toString() { return list.toString(); }
}
