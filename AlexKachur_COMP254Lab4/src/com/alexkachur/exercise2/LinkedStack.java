/*
 * From: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser — textbook-provided implementation
 */
package com.alexkachur.exercise2;

public class LinkedStack<E> implements Stack<E> {

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();

    public LinkedStack() { }

    @Override public int size()         { return list.size(); }
    @Override public boolean isEmpty()  { return list.isEmpty(); }
    @Override public void push(E e)     { list.addFirst(e); }  // push to front
    @Override public E top()            { return list.first(); }
    @Override public E pop()            { return list.removeFirst(); }

    public String toString()            { return list.toString(); }
}
