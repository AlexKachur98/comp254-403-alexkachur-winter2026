package com.alexkachur.exercise1;

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
 */

/**
 * Concrete implementation of a binary tree using a node-based, linked structure.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

  //---------------- nested Node class ----------------
  /** Nested static class for a binary tree node. */
  protected static class Node<E> implements Position<E> {
    private E element;          // an element stored at this node
    private Node<E> parent;     // a reference to the parent node (if any)
    private Node<E> left;       // a reference to the left child (if any)
    private Node<E> right;      // a reference to the right child (if any)

    /**
     * Constructs a node with the given element and neighbors.
     *
     * @param e  the element to be stored
     * @param above       reference to a parent node
     * @param leftChild   reference to a left child node
     * @param rightChild  reference to a right child node
     */
    public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
      element = e;
      parent = above;
      left = leftChild;
      right = rightChild;
    }

    //get and set methods
    //
    // accessor methods
    public E getElement() { return element; }
    public Node<E> getParent() { return parent; }
    public Node<E> getLeft() { return left; }
    public Node<E> getRight() { return right; }

    // update methods
    public void setElement(E e) { element = e; }
    public void setParent(Node<E> parentNode) { parent = parentNode; }
    public void setLeft(Node<E> leftChild) { left = leftChild; }
    public void setRight(Node<E> rightChild) { right = rightChild; }
  } //----------- end of nested Node class -----------

  /** Factory function to create a new node storing element e. */
  protected Node<E> createNode(E e, Node<E> parent,
                                  Node<E> left, Node<E> right) {
    return new Node<E>(e, parent, left, right);
  }

  // LinkedBinaryTree instance variables
  /** The root of the binary tree */
  protected Node<E> root = null;     // root of the tree

  /** The number of nodes in the binary tree */
  private int size = 0;              // number of nodes in the tree

  // constructor
  /** Construts an empty binary tree. */
  public LinkedBinaryTree() { }      // constructs an empty binary tree

  // nonpublic utility
  /**
   * Verifies that a Position belongs to the appropriate class, and is
   * not one that has been previously removed. Note that our current
   * implementation does not actually verify that the position belongs
   * to this particular list instance.
   *
   * @param p   a Position (that should belong to this tree)
   * @return    the underlying Node instance for the position
   * @throws IllegalArgumentException if an invalid position is detected
   */
  protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
    if (!(p instanceof Node))
      throw new IllegalArgumentException("Not valid position type");
    Node<E> node = (Node<E>) p;       // safe cast
    if (node.getParent() == node)     // our convention for defunct node
      throw new IllegalArgumentException("p is no longer in the tree");
    return node;
  }

  // accessor methods (not already implemented in AbstractBinaryTree)
  /**
   * Returns the number of nodes in the tree.
   * @return number of nodes in the tree
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns the root Position of the tree (or null if tree is empty).
   * @return root Position of the tree (or null if tree is empty)
   */
  @Override
  public Position<E> root() {
    return root;
  }

  /**
   * Returns the Position of p's parent (or null if p is root).
   *
   * @param p    A valid Position within the tree
   * @return Position of p's parent (or null if p is root)
   * @throws IllegalArgumentException if p is not a valid Position for this tree.
   */
  @Override
  public Position<E> parent(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getParent();
  }

  /**
   * Returns the Position of p's left child (or null if no child exists).
   *
   * @param p A valid Position within the tree
   * @return the Position of the left child (or null if no child exists)
   * @throws IllegalArgumentException if p is not a valid Position for this tree
   */
  @Override
  public Position<E> left(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getLeft();
  }

  /**
   * Returns the Position of p's right child (or null if no child exists).
   *
   * @param p A valid Position within the tree
   * @return the Position of the right child (or null if no child exists)
   * @throws IllegalArgumentException if p is not a valid Position for this tree
   */
  @Override
  public Position<E> right(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getRight();
  }

  // update methods supported by this class
  /**
   * Places element e at the root of an empty tree and returns its new Position.
   *
   * @param e   the new element
   * @return the Position of the new element
   * @throws IllegalStateException if the tree is not empty
   */
  public Position<E> addRoot(E e) throws IllegalStateException {
    if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
    root = createNode(e, null, null, null);
    size = 1;
    return root;
  }

  /**
   * Creates a new left child of Position p storing element e and returns its Position.
   *
   * @param p   the Position to the left of which the new element is inserted
   * @param e   the new element
   * @return the Position of the new element
   * @throws IllegalArgumentException if p is not a valid Position for this tree
   * @throws IllegalArgumentException if p already has a left child
   */
  public Position<E> addLeft(Position<E> p, E e)
                          throws IllegalArgumentException {
    Node<E> parent = validate(p);
    if (parent.getLeft() != null)
      throw new IllegalArgumentException("p already has a left child");
    Node<E> child = createNode(e, parent, null, null);
    parent.setLeft(child);
    size++;
    return child;
  }

  /**
   * Creates a new right child of Position p storing element e and returns its Position.
   *
   * @param p   the Position to the right of which the new element is inserted
   * @param e   the new element
   * @return the Position of the new element
   * @throws IllegalArgumentException if p is not a valid Position for this tree.
   * @throws IllegalArgumentException if p already has a right child
   */
  public Position<E> addRight(Position<E> p, E e)
                          throws IllegalArgumentException {
    Node<E> parent = validate(p);
    if (parent.getRight() != null)
      throw new IllegalArgumentException("p already has a right child");
    Node<E> child = createNode(e, parent, null, null);
    parent.setRight(child);
    size++;
    return child;
  }

  /**
   * Replaces the element at Position p with element e and returns the replaced element.
   *
   * @param p   the relevant Position
   * @param e   the new element
   * @return the replaced element
   * @throws IllegalArgumentException if p is not a valid Position for this tree.
   */
  public E set(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> node = validate(p);
    E temp = node.getElement();
    node.setElement(e);
    return temp;
  }

  /**
   * Attaches trees t1 and t2, respectively, as the left and right subtree of the
   * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
   *
   * @param p   a leaf of the tree
   * @param t1  an independent tree whose structure becomes the left child of p
   * @param t2  an independent tree whose structure becomes the right child of p
   * @throws IllegalArgumentException if p is not a valid Position for this tree
   * @throws IllegalArgumentException if p is not a leaf
   */
  public void attach(Position<E> p, LinkedBinaryTree<E> t1,
                    LinkedBinaryTree<E> t2) throws IllegalArgumentException {
    Node<E> node = validate(p);
    if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
    size += t1.size() + t2.size();
    if (!t1.isEmpty()) {                  // attach t1 as left subtree of node
      t1.root.setParent(node); //the node becomes parent of the root of left subtree
      node.setLeft(t1.root);
      t1.root = null;
      t1.size = 0;
    }
    if (!t2.isEmpty()) {                  // attach t2 as right subtree of node
      t2.root.setParent(node);
      node.setRight(t2.root);
      t2.root = null;
      t2.size = 0;
    }
  }

  /**
   * Removes the node at Position p and replaces it with its child, if any.
   *
   * @param p   the relevant Position
   * @return element that was removed
   * @throws IllegalArgumentException if p is not a valid Position for this tree.
   * @throws IllegalArgumentException if p has two children.
   */
  public E remove(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    if (numChildren(p) == 2)
      throw new IllegalArgumentException("p has two children");
    Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight() );
    if (child != null)
      child.setParent(node.getParent());  // child's grandparent becomes its parent
    if (node == root)
      root = child;                       // child becomes root
    else {
      Node<E> parent = node.getParent();
      if (node == parent.getLeft())
        parent.setLeft(child);
      else
        parent.setRight(child);
    }
    size--;
    E temp = node.getElement();
    node.setElement(null);                // help garbage collection
    node.setLeft(null);
    node.setRight(null);
    node.setParent(node);                 // our convention for defunct node
    return temp;
  }
  
  
  
  


  // =====================================================================
  // EXERCISE 1: inorderNext(p) - Alex Kachur
  // Returns the position visited after p in an inorder traversal of T,
  // or null if p is the last node visited.
  // Algorithm based on textbook's inorder traversal (Ch. 8)
  // Worst-case running time: O(h), where h is the height of the tree
  // =====================================================================

  /**
   * Returns the position visited after p in an inorder traversal of T,
   * or null if p is the last node visited.
   *
   * @param p a valid Position within the tree
   * @return the position visited after p in inorder, or null if p is last
   * @throws IllegalArgumentException if p is not a valid Position
   */
  public Position<E> inorderNext(Position<E> p) throws IllegalArgumentException {
    validate(p);  // ensure p is a valid position in this tree

    // Case 1: p has a right child
    // The next inorder node is the leftmost node in p's right subtree
    if (right(p) != null) {
      Position<E> walk = right(p);
      while (left(walk) != null) {
        walk = left(walk);           // keep going left
      }
      return walk;                   // this is the inorder successor
    }

    // Case 2: p has no right child
    // Walk up: the next inorder node is the first ancestor
    // for which p is in the left subtree
    Position<E> walk = p;
    Position<E> ancestor = parent(walk);
    // keep going up while we are the RIGHT child of our parent
    while (ancestor != null && walk == right(ancestor)) {
      walk = ancestor;
      ancestor = parent(ancestor);
    }
    // ancestor is null if p was the last node in inorder (rightmost node)
    return ancestor;
  }


  public static void main(String[] args) {
    System.out.println("=== COMP-254 Lab Assignment 5 Exercise 1 ===");
    System.out.println("Student: Alex Kachur");
    System.out.println("inorderNext(p) - Binary Tree Inorder Successor");
    System.out.println();

    // ---------------------------------------------------------------
    // Build the arithmetic expression tree: ((2 x (a - 1)) + (3 x b))
    // This is the same tree from the textbook/lecture slides (Ch. 8)
    //
    //            +
    //           / \
    //          x    x
    //         / \  / \
    //        2   - 3   b
    //           / \
    //          a   1
    //
    // Inorder traversal: 2, x, a, -, 1, +, 3, x, b
    // ---------------------------------------------------------------
    LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
    Position<String> root = tree.addRoot("+");

    Position<String> xLeft = tree.addLeft(root, "x");
    Position<String> xRight = tree.addRight(root, "x");

    Position<String> two = tree.addLeft(xLeft, "2");
    Position<String> minus = tree.addRight(xLeft, "-");

    Position<String> three = tree.addLeft(xRight, "3");
    Position<String> b = tree.addRight(xRight, "b");

    Position<String> a = tree.addLeft(minus, "a");
    Position<String> one = tree.addRight(minus, "1");

    // Print the full inorder traversal for reference
    System.out.println("Full inorder traversal:");
    System.out.print("  ");
    for (Position<String> pos : tree.inorder()) {
      System.out.print(pos.getElement() + " ");
    }
    System.out.println();
    System.out.println();

    // ---------------------------------------------------------------
    // Test inorderNext for every position in the tree
    // Expected: 2->x, x->a, a->-, -->1, 1->+, +->3, 3->x, x->b, b->null
    // ---------------------------------------------------------------
    System.out.println("Testing inorderNext(p) for each position:");
    System.out.println("------------------------------------------");

    @SuppressWarnings("unchecked")
    Position<String>[] positions = new Position[]{two, xLeft, a, minus, one, root, three, xRight, b};
    String[] expectedNext =       {"x", "a", "-", "1", "+", "3", "x", "b", "null"};

    boolean allPassed = true;
    for (int i = 0; i < positions.length; i++) {
      Position<String> current = positions[i];
      Position<String> next = tree.inorderNext(current);
      String nextStr = (next == null) ? "null" : next.getElement().toString();
      String status = nextStr.equals(expectedNext[i]) ? "PASS" : "FAIL";
      if (!nextStr.equals(expectedNext[i])) allPassed = false;

      System.out.println("  inorderNext(" + current.getElement() + ") = " + nextStr
          + "  [expected: " + expectedNext[i] + "]  " + status);
    }

    System.out.println();
    System.out.println("------------------------------------------");
    System.out.println(allPassed ? "All tests PASSED!" : "Some tests FAILED!");
    System.out.println();

    // ---------------------------------------------------------------
    // Edge case: Single-node tree
    // ---------------------------------------------------------------
    System.out.println("Edge case: Single-node tree");
    LinkedBinaryTree<String> singleTree = new LinkedBinaryTree<>();
    Position<String> singleRoot = singleTree.addRoot("R");
    Position<String> singleNext = singleTree.inorderNext(singleRoot);
    System.out.println("  inorderNext(R) = " + (singleNext == null ? "null" : singleNext.getElement())
        + "  [expected: null]  " + (singleNext == null ? "PASS" : "FAIL"));
    System.out.println();

    // ---------------------------------------------------------------
    // Edge case: Left-only chain (A -> B -> C, all left children)
    // Inorder: C, B, A
    // ---------------------------------------------------------------
    System.out.println("Edge case: Left-only chain (A->B->C all left children)");
    LinkedBinaryTree<String> leftChain = new LinkedBinaryTree<>();
    Position<String> lcA = leftChain.addRoot("A");
    Position<String> lcB = leftChain.addLeft(lcA, "B");
    Position<String> lcC = leftChain.addLeft(lcB, "C");

    System.out.print("  Inorder: ");
    for (Position<String> pos : leftChain.inorder())
      System.out.print(pos.getElement() + " ");
    System.out.println();

    Position<String> nextC = leftChain.inorderNext(lcC);
    Position<String> nextB = leftChain.inorderNext(lcB);
    Position<String> nextA = leftChain.inorderNext(lcA);
    System.out.println("  inorderNext(C) = " + (nextC == null ? "null" : nextC.getElement())
        + "  [expected: B]  " + ("B".equals(nextC == null ? "null" : nextC.getElement()) ? "PASS" : "FAIL"));
    System.out.println("  inorderNext(B) = " + (nextB == null ? "null" : nextB.getElement())
        + "  [expected: A]  " + ("A".equals(nextB == null ? "null" : nextB.getElement()) ? "PASS" : "FAIL"));
    System.out.println("  inorderNext(A) = " + (nextA == null ? "null" : nextA.getElement())
        + "  [expected: null]  " + (nextA == null ? "PASS" : "FAIL"));

    System.out.println();
    System.out.println("Worst-case running time: O(h), where h is the height of the tree.");
  }

} //----------- end of LinkedBinaryTree class -----------
