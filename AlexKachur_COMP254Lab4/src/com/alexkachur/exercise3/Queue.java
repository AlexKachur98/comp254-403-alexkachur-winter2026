/*
 * From: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser — textbook-provided interface
 */
package com.alexkachur.exercise3;

public interface Queue<E> {
    int size();
    boolean isEmpty();
    void enqueue(E e);
    E first();
    E dequeue();
}
