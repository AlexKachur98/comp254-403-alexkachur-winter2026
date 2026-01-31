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
 * 
 * Modified by: Alex Kachur
 * For: COMP-254 Lab Assignment 1, Exercise 3
 * Professor: Sohaib Mohiuddin
 */
package com.alexkachur.exercise3;

/**
 * An implementation of a circularly linked list.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class CircularlyLinkedList<E> implements Cloneable {
	// ---------------- nested Node class ----------------
	/**
	 * Singly linked node, which stores a reference to its element and to the
	 * subsequent node in the list.
	 */
	private static class Node<E> {

		/** The element stored at this node */
		private E element;

		/** A reference to the subsequent node in the list */
		private Node<E> next;

		/**
		 * Creates a node with the given element and next node.
		 *
		 * @param e the element to be stored
		 * @param n reference to a node that should follow the new node
		 */
		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}

		// Accessor methods
		/**
		 * Returns the element stored at the node.
		 * 
		 * @return the element stored at the node
		 */
		public E getElement() {
			return element;
		}

		/**
		 * Returns the node that follows this one (or null if no such node).
		 * 
		 * @return the following node
		 */
		public Node<E> getNext() {
			return next;
		}

		// Modifier methods
		/**
		 * Sets the node's next reference to point to Node n.
		 * 
		 * @param n the node that should follow this one
		 */
		public void setNext(Node<E> n) {
			next = n;
		}
	} // ----------- end of nested Node class -----------

	// instance variables of the CircularlyLinkedList
	/** The designated cursor of the list */
	private Node<E> tail = null;

	/** Number of nodes in the list */
	private int size = 0;

	/** Constructs an initially empty list. */
	public CircularlyLinkedList() {
	}

	// access methods
	/**
	 * Returns the number of elements in the linked list.
	 * 
	 * @return number of elements in the linked list
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests whether the linked list is empty.
	 * 
	 * @return true if the linked list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns (but does not remove) the first element of the list
	 * 
	 * @return element at the front of the list (or null if empty)
	 */
	public E first() {
		if (isEmpty())
			return null;
		return tail.getNext().getElement();
	}

	/**
	 * Returns (but does not remove) the last element of the list
	 * 
	 * @return element at the back of the list (or null if empty)
	 */
	public E last() {
		if (isEmpty())
			return null;
		return tail.getElement();
	}

	// update methods
	/**
	 * Rotate the first element to the back of the list.
	 */
	public void rotate() {
		if (tail != null)
			tail = tail.getNext();
	}

	/**
	 * Adds an element to the front of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addFirst(E e) {
		if (size == 0) {
			tail = new Node<>(e, null);
			tail.setNext(tail);
		} else {
			Node<E> newest = new Node<>(e, tail.getNext());
			tail.setNext(newest);
		}
		size++;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addLast(E e) {
		addFirst(e);
		tail = tail.getNext();
	}

	/**
	 * Removes and returns the first element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeFirst() {
		if (isEmpty())
			return null;
		Node<E> head = tail.getNext();
		if (head == tail)
			tail = null;
		else
			tail.setNext(head.getNext());
		size--;
		return head.getElement();
	}

	// ==================== EXERCISE 3: NEW METHOD ====================
	/**
	 * Creates and returns a deep copy of this circularly linked list. The cloned
	 * list contains new nodes with the same elements as the original, properly
	 * linked in a circular fashion.
	 * 
	 * Implementation follows the pattern from Code Fragment 3.21 in the textbook
	 * (SinglyLinkedList.clone), adapted for circular linking.
	 * 
	 * @return a clone of this circularly linked list
	 * @throws CloneNotSupportedException if cloning is not supported
	 */
	@SuppressWarnings({ "unchecked" })
	public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {
		// Always use inherited Object.clone() to create the initial copy
		CircularlyLinkedList<E> other = (CircularlyLinkedList<E>) super.clone();

		if (size > 0) {
			// We need an independent chain of nodes
			// In a circular list, head = tail.getNext()
			Node<E> originalHead = tail.getNext();

			// Create the first node of the cloned list
			Node<E> otherHead = new Node<>(originalHead.getElement(), null);
			Node<E> otherTail = otherHead; // Will track the last node we create

			// Walk through the remainder of the original list
			Node<E> walk = originalHead.getNext();

			// Continue until we complete the circle (back to originalHead)
			while (walk != originalHead) {
				// Create a new node storing the same element
				Node<E> newest = new Node<>(walk.getElement(), null);
				// Link the previous node to this one
				otherTail.setNext(newest);
				// Update otherTail to the newest node
				otherTail = newest;
				// Move to the next node in the original list
				walk = walk.getNext();
			}

			// CRITICAL: Make the cloned list circular
			// The last node must point back to the first node
			otherTail.setNext(otherHead);

			// Set the cloned list's tail reference
			other.tail = otherTail;
		}

		return other;
	}
	// ==================== END OF EXERCISE 3 ====================

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		if (tail == null)
			return "()";
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = tail;
		do {
			walk = walk.getNext();
			sb.append(walk.getElement());
			if (walk != tail)
				sb.append(", ");
		} while (walk != tail);
		sb.append(")");
		return sb.toString();
	}

	// main method for testing
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 1 Exercise 3: CircularlyLinkedList clone ===\n");

		// Test Case 1: Clone a list with multiple elements
		System.out.println("Test Case 1: Clone a list with multiple elements");
		try {
			CircularlyLinkedList<String> original = new CircularlyLinkedList<>();
			original.addLast("LAX");
			original.addLast("MSP");
			original.addLast("ATL");
			original.addLast("BOS");

			System.out.println("  Original: " + original);

			CircularlyLinkedList<String> cloned = original.clone();

			System.out.println("  Cloned:   " + cloned);
			System.out.println("  Expected: (LAX, MSP, ATL, BOS)");

			// Verify they are independent by modifying the original
			System.out.println("\n  Modifying original (removing first element)...");
			original.removeFirst();

			System.out.println("  Original after modification: " + original);
			System.out.println("  Cloned (should be unchanged): " + cloned);
			System.out.println("  Expected original: (MSP, ATL, BOS)");
			System.out.println("  Expected cloned: (LAX, MSP, ATL, BOS)");

		} catch (CloneNotSupportedException e) {
			System.out.println("  ERROR: Clone not supported!");
		}

		// Test Case 2: Verify circular property of cloned list
		System.out.println("\nTest Case 2: Verify circular property (rotate cloned list)");
		try {
			CircularlyLinkedList<String> original2 = new CircularlyLinkedList<>();
			original2.addLast("A");
			original2.addLast("B");
			original2.addLast("C");

			CircularlyLinkedList<String> cloned2 = original2.clone();

			System.out.println("  Cloned before rotate: " + cloned2);

			cloned2.rotate();
			System.out.println("  Cloned after 1 rotate: " + cloned2);
			System.out.println("  Expected: (B, C, A)");

			cloned2.rotate();
			System.out.println("  Cloned after 2 rotates: " + cloned2);
			System.out.println("  Expected: (C, A, B)");

			cloned2.rotate();
			System.out.println("  Cloned after 3 rotates: " + cloned2);
			System.out.println("  Expected: (A, B, C) - back to original order");

		} catch (CloneNotSupportedException e) {
			System.out.println("  ERROR: Clone not supported!");
		}

		// Test Case 3: Clone an empty list
		System.out.println("\nTest Case 3: Clone an empty list");
		try {
			CircularlyLinkedList<String> emptyOriginal = new CircularlyLinkedList<>();

			System.out.println("  Original (empty): " + emptyOriginal);

			CircularlyLinkedList<String> emptyCloned = emptyOriginal.clone();

			System.out.println("  Cloned: " + emptyCloned);
			System.out.println("  Expected: ()");
			System.out.println("  Cloned size: " + emptyCloned.size() + " (Expected: 0)");
			System.out.println("  Cloned isEmpty: " + emptyCloned.isEmpty() + " (Expected: true)");

		} catch (CloneNotSupportedException e) {
			System.out.println("  ERROR: Clone not supported!");
		}

		// Test Case 4: Clone a single-element list
		System.out.println("\nTest Case 4: Clone a single-element list");
		try {
			CircularlyLinkedList<String> singleOriginal = new CircularlyLinkedList<>();
			singleOriginal.addFirst("ONLY");

			System.out.println("  Original: " + singleOriginal);

			CircularlyLinkedList<String> singleCloned = singleOriginal.clone();

			System.out.println("  Cloned: " + singleCloned);
			System.out.println("  Expected: (ONLY)");

			// Verify circular property on single element
			singleCloned.rotate();
			System.out.println("  After rotate: " + singleCloned);
			System.out.println("  Expected: (ONLY) - should remain same");

		} catch (CloneNotSupportedException e) {
			System.out.println("  ERROR: Clone not supported!");
		}

		// Test Case 5: Verify first() and last() on cloned list
		System.out.println("\nTest Case 5: Verify first() and last() on cloned list");
		try {
			CircularlyLinkedList<String> original5 = new CircularlyLinkedList<>();
			original5.addLast("FIRST");
			original5.addLast("MIDDLE");
			original5.addLast("LAST");

			CircularlyLinkedList<String> cloned5 = original5.clone();

			System.out.println("  Cloned list: " + cloned5);
			System.out.println("  first(): " + cloned5.first() + " (Expected: FIRST)");
			System.out.println("  last():  " + cloned5.last() + " (Expected: LAST)");

		} catch (CloneNotSupportedException e) {
			System.out.println("  ERROR: Clone not supported!");
		}

		System.out.println("\n=== All tests completed ===");
	}
}