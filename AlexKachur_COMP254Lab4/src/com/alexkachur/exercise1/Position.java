/*
 * From: Data Structures and Algorithms in Java, 6th Edition
 * Goodrich, Tamassia, Goldwasser — textbook-provided interface
 */
package com.alexkachur.exercise1;

public interface Position<E> {
    E getElement() throws IllegalStateException;
}
