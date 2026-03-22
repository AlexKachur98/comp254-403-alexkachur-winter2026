package com.alexkachur.exercise3;

/**
 * Test class for concatenate() on LinkedQueue.
 * Author: Alex Kachur — COMP-254 Lab Assignment 4
 */
public class Exercise3Test {

    public static void main(String[] args) {

        System.out.println("=== Exercise 3: concatenate(LinkedQueue Q2) Test ===");
        System.out.println();

        // --- Basic test ---
        LinkedQueue<String> Q1 = new LinkedQueue<>();
        LinkedQueue<String> Q2 = new LinkedQueue<>();

        Q1.enqueue("A");
        Q1.enqueue("B");
        Q1.enqueue("C");

        Q2.enqueue("D");
        Q2.enqueue("E");

        System.out.println("Q1 before: " + Q1);     // (A, B, C)
        System.out.println("Q2 before: " + Q2);     // (D, E)
        System.out.println("Q1 size: " + Q1.size());
        System.out.println("Q2 size: " + Q2.size());
        System.out.println();

        Q1.concatenate(Q2);

        System.out.println("Q1 after concatenate: " + Q1); // (A, B, C, D, E)
        System.out.println("Q2 after concatenate: " + Q2); // () — must be empty
        System.out.println("Q1.size() = " + Q1.size() + "  (expected: 5)");
        System.out.println("Q2.isEmpty() = " + Q2.isEmpty() + "  (expected: true)");
        System.out.println();

        // --- Verify front and dequeue order is preserved ---
        System.out.println("Dequeuing Q1 in order:");
        while (!Q1.isEmpty()) {
            System.out.print(Q1.dequeue() + " ");  // should print A B C D E
        }
        System.out.println();
        System.out.println("(expected: A B C D E)");
        System.out.println();

        // --- Edge case: concatenate empty queue onto non-empty ---
        LinkedQueue<Integer> qa = new LinkedQueue<>();
        LinkedQueue<Integer> emptyQ = new LinkedQueue<>();
        qa.enqueue(1); qa.enqueue(2);
        qa.concatenate(emptyQ);
        System.out.println("Concat with empty Q2: " + qa + "  (expected: (1, 2))");
        System.out.println();

        // --- Edge case: concatenate onto empty Q1 ---
        LinkedQueue<Integer> emptyQ1 = new LinkedQueue<>();
        LinkedQueue<Integer> qb = new LinkedQueue<>();
        qb.enqueue(10); qb.enqueue(20);
        emptyQ1.concatenate(qb);
        System.out.println("Concat onto empty Q1: " + emptyQ1 + "  (expected: (10, 20))");
        System.out.println("qb.isEmpty() = " + qb.isEmpty() + "  (expected: true)");
        System.out.println();

        // --- Integer test ---
        LinkedQueue<Integer> q3 = new LinkedQueue<>();
        LinkedQueue<Integer> q4 = new LinkedQueue<>();
        for (int i = 1; i <= 3; i++) q3.enqueue(i);
        for (int i = 4; i <= 6; i++) q4.enqueue(i);
        q3.concatenate(q4);
        System.out.println("Integer concat result: " + q3); // (1, 2, 3, 4, 5, 6)
    }
}
