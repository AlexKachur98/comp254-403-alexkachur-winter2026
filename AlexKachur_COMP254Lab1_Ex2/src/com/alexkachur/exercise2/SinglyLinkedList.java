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
 * For: COMP-254 Lab Assignment 1, Exercise 2
 * Professor: Sohaib Mohiuddin
 */
package com.alexkachur.exercise2;

/**
 * A basic singly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SinglyLinkedList<E> implements Cloneable {
	// ---------------- nested Node class ----------------
	/**
	 * Node of a singly linked list, which stores a reference to its element and to
	 * the subsequent node in the list (or null if this is the last node).
	 */
	private static class Node<E> {

		/** The element stored at this node */
		private E element; // reference to the element stored at this node

		/** A reference to the subsequent node in the list */
		private Node<E> next; // reference to the subsequent node in the list

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

	// instance variables of the SinglyLinkedList
	/** The head node of the list */
	private Node<E> head = null; // head node of the list (or null if empty)

	/** The last node of the list */
	private Node<E> tail = null; // last node of the list (or null if empty)

	/** Number of nodes in the list */
	private int size = 0; // number of nodes in the list

	/** Constructs an initially empty list. */
	public SinglyLinkedList() {
	} // constructs an initially empty list

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
	public E first() { // returns (but does not remove) the first element
		if (isEmpty())
			return null;
		return head.getElement();
	}

	/**
	 * Returns (but does not remove) the last element of the list.
	 * 
	 * @return element at the end of the list (or null if empty)
	 */
	public E last() { // returns (but does not remove) the last element
		if (isEmpty())
			return null;
		return tail.getElement();
	}

	// update methods
	/**
	 * Adds an element to the front of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addFirst(E e) { // adds element e to the front of the list
		head = new Node<>(e, head); // create and link a new node
		if (size == 0)
			tail = head; // special case: new node becomes tail also
		size++;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e the new element to add
	 */
	public void addLast(E e) { // adds element e to the end of the list
		Node<E> newest = new Node<>(e, null); // node will eventually be the tail
		if (isEmpty())
			head = newest; // special case: previously empty list
		else
			tail.setNext(newest); // new node after existing tail
		tail = newest; // new node becomes the tail
		size++;
	}

	/**
	 * Removes and returns the first element of the list.
	 * 
	 * @return the removed element (or null if empty)
	 */
	public E removeFirst() { // removes and returns the first element
		if (isEmpty())
			return null; // nothing to remove
		E answer = head.getElement();
		head = head.getNext(); // will become null if list had only one node
		size--;
		if (size == 0)
			tail = null; // special case as list is now empty
		return answer;
	}

	// ==================== EXERCISE 2: SWAPNODES METHOD ====================
	/**
	 * Returns the node at the specified index (for testing purposes).
	 * 
	 * @param index the index of the node (0-based)
	 * @return the node at the specified index, or null if out of bounds
	 */
	public Node<E> getNodeAtIndex(int index) {
		if (index < 0 || index >= size)
			return null;
		Node<E> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Swaps two nodes in the list (not just their contents). Given references only
	 * to node1 and node2, this method swaps the actual nodes by updating the next
	 * pointers of their predecessors. Checks if node1 and node2 are the same node,
	 * handles null inputs, and properly updates head and tail references when
	 * needed.
	 *
	 * @param node1 the first node to swap
	 * @param node2 the second node to swap
	 */
	public void swapNodes(Node<E> node1, Node<E> node2) {
		// check if node1 and node2 are the same or null
		if (node1 == null || node2 == null)
			return;
		if (node1 == node2)
			return; // same node, nothing to do

		// find node1 and its predecessor by traversing
		Node<E> prev1 = null;
		Node<E> curr1 = head;
		while (curr1 != null && curr1 != node1) {
			prev1 = curr1;
			curr1 = curr1.getNext();
		}

		// find node2 and its predecessor by traversing
		Node<E> prev2 = null;
		Node<E> curr2 = head;
		while (curr2 != null && curr2 != node2) {
			prev2 = curr2;
			curr2 = curr2.getNext();
		}

		// if either node was not found, do nothing
		if (curr1 == null || curr2 == null)
			return;

		// normalize so that curr1 comes before curr2 in the list
		// check if curr2 comes before curr1 (i.e., prev1 == curr2)
		if (prev1 == curr2) {
			// swap all references so curr1 is before curr2
			Node<E> temp = prev1;
			prev1 = prev2;
			prev2 = temp;
			temp = curr1;
			curr1 = curr2;
			curr2 = temp;
		}

		// save next pointers
		Node<E> next1 = curr1.getNext();
		Node<E> next2 = curr2.getNext();

		// update head if needed
		if (prev1 == null) {
			head = curr2; // curr2 becomes new head
		} else {
			prev1.setNext(curr2);
		}

		// check if nodes are adjacent (curr1 -> curr2)
		if (next1 == curr2) {
			// adjacent case: prev1 -> curr1 -> curr2 -> next2
			// becomes: prev1 -> curr2 -> curr1 -> next2
			curr2.setNext(curr1);
			curr1.setNext(next2);
		} else {
			// non-adjacent case: prev1 -> curr1 -> next1 ... prev2 -> curr2 -> next2
			// becomes: prev1 -> curr2 -> next1 ... prev2 -> curr1 -> next2
			if (prev2 == null) {
				head = curr1; // curr1 becomes new head
			} else {
				prev2.setNext(curr1);
			}
			curr2.setNext(next1);
			curr1.setNext(next2);
		}

		// update tail if necessary
		if (tail == curr1) {
			tail = curr2;
		} else if (tail == curr2) {
			tail = curr1;
		}
	}
	// ==================== END OF EXERCISE 2 ====================

	@SuppressWarnings({ "unchecked" })
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		SinglyLinkedList other = (SinglyLinkedList) o; // use nonparameterized type
		if (size != other.size)
			return false;
		Node walkA = head; // traverse the primary list
		Node walkB = other.head; // traverse the secondary list
		while (walkA != null) {
			if (!walkA.getElement().equals(walkB.getElement()))
				return false; // mismatch
			walkA = walkA.getNext();
			walkB = walkB.getNext();
		}
		return true; // if we reach this, everything matched successfully
	}

	@SuppressWarnings({ "unchecked" })
	public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
		// always use inherited Object.clone() to create the initial copy
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
		if (size > 0) { // we need independent chain of nodes
			other.head = new Node<>(head.getElement(), null);
			Node<E> walk = head.getNext(); // walk through remainder of original list
			Node<E> otherTail = other.head; // remember most recently created node
			while (walk != null) { // make a new node storing same element
				Node<E> newest = new Node<>(walk.getElement(), null);
				otherTail.setNext(newest); // link previous node to this one
				otherTail = newest;
				walk = walk.getNext();
			}
			other.tail = otherTail; // set tail of cloned list
		}
		return other;
	}

	public int hashCode() {
		int h = 0;
		for (Node walk = head; walk != null; walk = walk.getNext()) {
			h ^= walk.getElement().hashCode(); // bitwise exclusive-or with element's code
			h = (h << 5) | (h >>> 27); // 5-bit cyclic shift of composite code
		}
		return h;
	}

	/**
	 * Produces a string representation of the contents of the list. This exists for
	 * debugging purposes only.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = head;
		while (walk != null) {
			sb.append(walk.getElement());
			if (walk != tail)
				sb.append(", ");
			walk = walk.getNext();
		}
		sb.append(")");
		return sb.toString();
	}

	// main method to test swapNodes
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 1 Exercise 2: SinglyLinkedList swapNodes ===\n");

		// Test Case 1: Swap non-adjacent middle nodes
		System.out.println("Test Case 1: Swap non-adjacent nodes (B and D)");
		SinglyLinkedList<String> list1 = new SinglyLinkedList<String>();
		list1.addLast("A");
		list1.addLast("B");
		list1.addLast("C");
		list1.addLast("D");
		list1.addLast("E");

		System.out.println("  Before: " + list1);
		Node<String> nodeB = list1.getNodeAtIndex(1); // B
		Node<String> nodeD = list1.getNodeAtIndex(3); // D
		list1.swapNodes(nodeB, nodeD);
		System.out.println("  After:  " + list1);
		System.out.println("  Expected: (A, D, C, B, E)");

		// Test Case 2: Swap adjacent nodes
		System.out.println("\nTest Case 2: Swap adjacent nodes (B and C)");
		SinglyLinkedList<String> list2 = new SinglyLinkedList<String>();
		list2.addLast("A");
		list2.addLast("B");
		list2.addLast("C");
		list2.addLast("D");

		System.out.println("  Before: " + list2);
		Node<String> nodeB2 = list2.getNodeAtIndex(1); // B
		Node<String> nodeC2 = list2.getNodeAtIndex(2); // C
		list2.swapNodes(nodeB2, nodeC2);
		System.out.println("  After:  " + list2);
		System.out.println("  Expected: (A, C, B, D)");

		// Test Case 3: Swap head with another node
		System.out.println("\nTest Case 3: Swap head (A) with middle node (C)");
		SinglyLinkedList<String> list3 = new SinglyLinkedList<String>();
		list3.addLast("A");
		list3.addLast("B");
		list3.addLast("C");
		list3.addLast("D");

		System.out.println("  Before: " + list3);
		Node<String> nodeA3 = list3.getNodeAtIndex(0); // A (head)
		Node<String> nodeC3 = list3.getNodeAtIndex(2); // C
		list3.swapNodes(nodeA3, nodeC3);
		System.out.println("  After:  " + list3);
		System.out.println("  Expected: (C, B, A, D)");

		// Test Case 4: Swap node with tail
		System.out.println("\nTest Case 4: Swap middle (B) with tail (D)");
		SinglyLinkedList<String> list4 = new SinglyLinkedList<String>();
		list4.addLast("A");
		list4.addLast("B");
		list4.addLast("C");
		list4.addLast("D");

		System.out.println("  Before: " + list4);
		Node<String> nodeB4 = list4.getNodeAtIndex(1); // B
		Node<String> nodeD4 = list4.getNodeAtIndex(3); // D (tail)
		list4.swapNodes(nodeB4, nodeD4);
		System.out.println("  After:  " + list4);
		System.out.println("  Expected: (A, D, C, B)");
		System.out.println("  tail: " + list4.last() + " (Expected: B)");

		// Test Case 5: Swap same node (should do nothing)
		System.out.println("\nTest Case 5: Swap node with itself");
		SinglyLinkedList<String> list5 = new SinglyLinkedList<String>();
		list5.addLast("A");
		list5.addLast("B");
		list5.addLast("C");

		System.out.println("  Before: " + list5);
		Node<String> nodeB5 = list5.getNodeAtIndex(1); // B
		list5.swapNodes(nodeB5, nodeB5);
		System.out.println("  After:  " + list5);
		System.out.println("  Expected: (A, B, C) - unchanged");

		// Test Case 6: Swap head and tail
		System.out.println("\nTest Case 6: Swap head (A) and tail (D)");
		SinglyLinkedList<String> list6 = new SinglyLinkedList<String>();
		list6.addLast("A");
		list6.addLast("B");
		list6.addLast("C");
		list6.addLast("D");

		System.out.println("  Before: " + list6);
		Node<String> nodeA6 = list6.getNodeAtIndex(0); // A (head)
		Node<String> nodeD6 = list6.getNodeAtIndex(3); // D (tail)
		list6.swapNodes(nodeA6, nodeD6);
		System.out.println("  After:  " + list6);
		System.out.println("  Expected: (D, B, C, A)");
		System.out.println("  head: " + list6.first() + " (Expected: D)");
		System.out.println("  tail: " + list6.last() + " (Expected: A)");

		System.out.println("\n=== All tests completed ===");
	}
}