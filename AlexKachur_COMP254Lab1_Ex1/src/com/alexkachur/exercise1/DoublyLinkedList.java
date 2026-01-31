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
 * For: COMP-254 Lab Assignment 1, Exercise 1
 * Professor: Sohaib Mohiuddin
 */
package com.alexkachur.exercise1;

/**
 * A basic doubly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class DoublyLinkedList<E> {

	// ---------------- nested Node class ----------------
	/**
	 * Node of a doubly linked list, which stores a reference to its element and to
	 * both the previous and next node in the list.
	 */
	private static class Node<E> {

		/** The element stored at this node */
		private E element;

		/** A reference to the preceding node in the list */
		private Node<E> prev;

		/** A reference to the subsequent node in the list */
		private Node<E> next;

		/**
		 * Creates a node with the given element and next node.
		 *
		 * @param e the element to be stored
		 * @param p reference to a node that should precede the new node
		 * @param n reference to a node that should follow the new node
		 */
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}

		// public accessor methods
		/**
		 * Returns the element stored at the node.
		 * 
		 * @return the element stored at the node
		 */
		public E getElement() {
			return element;
		}

		/**
		 * Returns the node that precedes this one (or null if no such node).
		 * 
		 * @return the preceding node
		 */
		public Node<E> getPrev() {
			return prev;
		}

		/**
		 * Returns the node that follows this one (or null if no such node).
		 * 
		 * @return the following node
		 */
		public Node<E> getNext() {
			return next;
		}

		// Update methods
		/**
		 * Sets the node's previous reference to point to Node n.
		 * 
		 * @param p the node that should precede this one
		 */
		public void setPrev(Node<E> p) {
			prev = p;
		}

		/**
		 * Sets the node's next reference to point to Node n.
		 * 
		 * @param n the node that should follow this one
		 */
		public void setNext(Node<E> n) {
			next = n;
		}

	} // ----------- end of nested Node class -----------

	// instance variables of the DoublyLinkedList
	/** Sentinel node at the beginning of the list */
	private Node<E> header;

	/** Sentinel node at the end of the list */
	private Node<E> trailer;

	/** Number of elements in the list (not including sentinels) */
	private int size = 0;

	/** Constructs a new empty list. */
	public DoublyLinkedList() {
		header = new Node<>(null, null, null);
		trailer = new Node<>(null, header, null);
		header.setNext(trailer);
	}

	// public accessor methods
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
	 * Returns (but does not remove) the first element of the list.
	 * 
	 * @return element at the front of the list (or null if empty)
	 */
	public E first() {
		if (isEmpty())
			return null;
		return header.getNext().getElement();
	}

	/**
	 * Returns (but does not remove) the last element of the list.
	 * 
	 * @return element at the end of the list (or null if empty)
	 */
	public E last() {
		if (isEmpty())
			return null;
		return trailer.getPrev().getElement();
	}

	// public update methods
	/**
	 * Adds an element to the front of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addFirst(E e) {
		addBetween(e, header, header.getNext());
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addLast(E e) {
		addBetween(e, trailer.getPrev(), trailer);
	}

	/**
	 * Removes and returns the first element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeFirst() {
		if (isEmpty())
			return null;
		return remove(header.getNext());
	}

	/**
	 * Removes and returns the last element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeLast() {
		if (isEmpty())
			return null;
		return remove(trailer.getPrev());
	}

	// private update methods
	/**
	 * Adds an element to the linked list in between the given nodes. The given
	 * predecessor and successor should be neighboring each other prior to the call.
	 *
	 * @param predecessor node just before the location where the new element is
	 *                    inserted
	 * @param successor   node just after the location where the new element is
	 *                    inserted
	 */
	private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
	}

	/**
	 * Removes the given node from the list and returns its element.
	 * 
	 * @param node the node to be removed (must not be a sentinel)
	 */
	private E remove(Node<E> node) {
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		return node.getElement();
	}

	// ==================== EXERCISE 1: NEW METHOD ====================
	/**
	 * Concatenates another doubly linked list M to the end of this list. The list M
	 * will be empty after this operation. This method connects the end of this list
	 * (L) to the beginning of M, resulting in a single combined list L'.
	 * 
	 * @param M the doubly linked list to concatenate to the end of this list
	 */
	public void concatenate(DoublyLinkedList<E> M) {
		// Edge case: if M is empty, nothing to do
		if (M.isEmpty()) {
			return;
		}

		// Edge case: if this list (L) is empty, take all of M's elements
		if (this.isEmpty()) {
			// Get M's first and last real nodes
			Node<E> mFirstNode = M.header.getNext();
			Node<E> mLastNode = M.trailer.getPrev();

			// Connect this list's header to M's first real node
			this.header.setNext(mFirstNode);
			mFirstNode.setPrev(this.header);

			// Connect M's last real node to this list's trailer
			mLastNode.setNext(this.trailer);
			this.trailer.setPrev(mLastNode);

			// Update size
			this.size = M.size;
		} else {
			// Normal case: both lists have elements
			// Find connection points:
			// - L's last real node (before L's trailer)
			// - M's first real node (after M's header)
			Node<E> lLastNode = this.trailer.getPrev();
			Node<E> mFirstNode = M.header.getNext();
			Node<E> mLastNode = M.trailer.getPrev();

			// Connect L's last node to M's first node
			lLastNode.setNext(mFirstNode);
			mFirstNode.setPrev(lLastNode);

			// Connect M's last node to L's trailer
			mLastNode.setNext(this.trailer);
			this.trailer.setPrev(mLastNode);

			// Update size
			this.size += M.size;
		}

		// Clean up M: make it an empty list
		M.header.setNext(M.trailer);
		M.trailer.setPrev(M.header);
		M.size = 0;
	}
	// ==================== END OF EXERCISE 1 ====================

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = header.getNext();
		while (walk != trailer) {
			sb.append(walk.getElement());
			walk = walk.getNext();
			if (walk != trailer)
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}

	// main method for testing
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 1 Exercise 1: DoublyLinkedList Concatenation ===\n");

		// Test Case 1: Basic concatenation
		System.out.println("Test Case 1: Basic concatenation");
		DoublyLinkedList<String> L = new DoublyLinkedList<>();
		L.addLast("A");
		L.addLast("B");

		DoublyLinkedList<String> M = new DoublyLinkedList<>();
		M.addLast("X");
		M.addLast("Y");
		M.addLast("Z");

		System.out.println("Before concatenation:");
		System.out.println("  L: " + L + " (size: " + L.size() + ")");
		System.out.println("  M: " + M + " (size: " + M.size() + ")");

		L.concatenate(M);

		System.out.println("After L.concatenate(M):");
		System.out.println("  L: " + L + " (size: " + L.size() + ")");
		System.out.println("  M: " + M + " (size: " + M.size() + ")");
		System.out.println("  Expected L: (A, B, X, Y, Z) (size: 5)");
		System.out.println("  Expected M: () (size: 0)");

		// Test Case 2: Concatenate with empty M
		System.out.println("\nTest Case 2: Concatenate with empty M");
		DoublyLinkedList<String> L2 = new DoublyLinkedList<>();
		L2.addLast("P");
		L2.addLast("Q");
		DoublyLinkedList<String> M2 = new DoublyLinkedList<>();

		System.out.println("Before concatenation:");
		System.out.println("  L2: " + L2);
		System.out.println("  M2: " + M2 + " (empty)");

		L2.concatenate(M2);

		System.out.println("After L2.concatenate(M2):");
		System.out.println("  L2: " + L2);
		System.out.println("  Expected: (P, Q)");

		// Test Case 3: Empty L concatenate non-empty M
		System.out.println("\nTest Case 3: Empty L concatenate non-empty M");
		DoublyLinkedList<String> L3 = new DoublyLinkedList<>();
		DoublyLinkedList<String> M3 = new DoublyLinkedList<>();
		M3.addLast("R");
		M3.addLast("S");

		System.out.println("Before concatenation:");
		System.out.println("  L3: " + L3 + " (empty)");
		System.out.println("  M3: " + M3);

		L3.concatenate(M3);

		System.out.println("After L3.concatenate(M3):");
		System.out.println("  L3: " + L3);
		System.out.println("  Expected: (R, S)");

		// Test Case 4: Verify bidirectional traversal still works
		System.out.println("\nTest Case 4: Verify bidirectional traversal");
		System.out.println("  L.first(): " + L.first() + " (Expected: A)");
		System.out.println("  L.last(): " + L.last() + " (Expected: Z)");

		System.out.println("\n=== All tests completed ===");
	}
}