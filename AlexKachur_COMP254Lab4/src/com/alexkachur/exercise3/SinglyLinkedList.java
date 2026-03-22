/*
 * Base class from: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser
 * concatenate() method: Author: Alex Kachur — Lab Assignment 4
 */
package com.alexkachur.exercise3;

public class SinglyLinkedList<E> {

    //---------------- nested Node class ----------------
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }
        public E getElement() { return element; }
        public Node<E> getNext() { return next; }
        public void setNext(Node<E> n) { next = n; }
    } //----------- end of nested Node class -----------

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    public SinglyLinkedList() { }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public E first() {
        if (isEmpty()) return null;
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);
        if (size == 0) tail = head;
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) head = newest;
        else tail.setNext(newest);
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0) tail = null;
        return answer;
    }

    /**
     * Concatenates all elements of 'other' to the end of this list in O(1) time.
     * After this call, 'other' will be an empty list.
     *
     * Strategy: Link this list's tail node directly to other's head node.
     * No loop needed — just pointer manipulation.
     *
     * Author: Alex Kachur — original implementation for Lab Assignment 4
     */
    public void concatenate(SinglyLinkedList<E> other) {
        if (!other.isEmpty()) {
            // only do work if other is non-empty
            if (isEmpty()) {
                head = other.head;           // this list was empty; borrow other's head
            } else {
                tail.setNext(other.head);    // link our tail → other's head (the key step!)
            }
            tail = other.tail;              // update our tail pointer to other's tail
            size += other.size;             // update total size
            // Clear the other list to make it empty (as required by the assignment)
            other.head = null;
            other.tail = null;
            other.size = 0;
        }
        // If other is already empty, nothing to do — this list is unchanged
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = head;
        while (walk != null) {
            sb.append(walk.getElement());
            if (walk.getNext() != null) sb.append(", ");
            walk = walk.getNext();
        }
        sb.append(")");
        return sb.toString();
    }
}
