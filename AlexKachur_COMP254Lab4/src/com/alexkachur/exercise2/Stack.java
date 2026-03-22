/*
 * From: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser — textbook-provided interface
 */
package com.alexkachur.exercise2;

public interface Stack<E> {
    int size();
    boolean isEmpty();
    void push(E e);
    E top();
    E pop();
}
