package com.alexkachur.exercise2;

/**
 * Contains the transfer(S, T) method and tests for Exercise 2.
 * Author: Alex Kachur — COMP-254 Lab Assignment 4
 */
public class Exercise2Test {

    /**
     * Transfers all elements from stack S onto stack T.
     * The element at the top of S is inserted onto T first.
     * The element at the bottom of S ends up at the top of T.
     *
     * @param S  source stack (will be empty after the call)
     * @param T  destination stack (receives all elements from S)
     * Author: Alex Kachur — original implementation for Lab Assignment 4
     */
    public static <E> void transfer(Stack<E> S, Stack<E> T) {
        while (!S.isEmpty()) {
            T.push(S.pop());  // pop from S, push onto T
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Exercise 2: transfer(S, T) Test ===");
        System.out.println();

        // --- Basic test: numbers 1, 2, 3, 4, 5 ---
        LinkedStack<Integer> S = new LinkedStack<>();
        LinkedStack<Integer> T = new LinkedStack<>();

        // Push 1..5 onto S (5 is on top)
        for (int i = 1; i <= 5; i++) S.push(i);

        System.out.println("S before transfer: " + S);  // (5, 4, 3, 2, 1)
        System.out.println("T before transfer: " + T);  // ()

        transfer(S, T);

        System.out.println("S after transfer:  " + S);  // () — should be empty
        System.out.println("T after transfer:  " + T);  // (1, 2, 3, 4, 5) — 1 on top
        System.out.println();

        // --- Verify T top is 1 (was bottom of S) ---
        System.out.println("T.top() = " + T.top() + "  (expected: 1)");
        System.out.println("S.isEmpty() = " + S.isEmpty() + "  (expected: true)");
        System.out.println();

        // --- String test ---
        LinkedStack<String> sStr = new LinkedStack<>();
        LinkedStack<String> tStr = new LinkedStack<>();
        sStr.push("Apple");
        sStr.push("Banana");
        sStr.push("Cherry");

        System.out.println("String stack S: " + sStr);  // (Cherry, Banana, Apple)
        transfer(sStr, tStr);
        System.out.println("After transfer, T: " + tStr); // (Apple, Banana, Cherry)
        System.out.println("T.top() = " + tStr.top() + "  (expected: Apple)");
        System.out.println();

        // --- Edge case: empty source stack ---
        LinkedStack<Integer> emptyS = new LinkedStack<>();
        LinkedStack<Integer> emptyT = new LinkedStack<>();
        transfer(emptyS, emptyT);
        System.out.println("Transfer from empty stack — T size: "
                           + emptyT.size() + "  (expected: 0)");
    }
}
