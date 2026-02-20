/**
 * COMP-254 Data Structures and Algorithms
 * Lab Assignment 3 - Exercise 1
 * 
 * Recursive algorithm to compute the product of two positive integers,
 * m and n, using only addition and subtraction.
 * 
 * Based on the linearSum technique from Week 5 examples.
 * 
 * @author Alex Kachur
 */
package com.alexkachur.exercise1;

public class RecursiveProduct {

	/**
	 * Computes the product of two positive integers using only addition and
	 * subtraction. Follows the linearSum pattern from ArraySum.java: - Base case:
	 * when the counter reaches 0, return 0 - Recursive case: add m one more time
	 * and count down n by 1
	 *
	 * The idea: m * n = m + m + m + ... (n times) So: m * n = m + m * (n - 1)
	 *
	 * @param m the first positive integer (the number being added)
	 * @param n the second positive integer (the counter)
	 * @return the product m * n
	 */
	public static int product(int m, int n) {
		if (n == 0) // base case
			return 0;
		else // recursive case
			return product(m, n - 1) + m; // add m one more time
	}

	/** Tests the recursive product method with various inputs. */
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 3 Exercise 1: Recursive Product ===");
		System.out.println();

		// Test Case 1: Basic multiplication
		int m1 = 3, n1 = 5;
		System.out.println("Test 1: product(" + m1 + ", " + n1 + ") = " + product(m1, n1));
		System.out.println("  Expected: " + (m1 * n1));
		System.out.println();

		// Test Case 2: Multiplying by 1
		int m2 = 7, n2 = 1;
		System.out.println("Test 2: product(" + m2 + ", " + n2 + ") = " + product(m2, n2));
		System.out.println("  Expected: " + (m2 * n2));
		System.out.println();

		// Test Case 3: Multiplying by 0
		int m3 = 9, n3 = 0;
		System.out.println("Test 3: product(" + m3 + ", " + n3 + ") = " + product(m3, n3));
		System.out.println("  Expected: " + (m3 * n3));
		System.out.println();

		// Test Case 4: Larger numbers
		int m4 = 12, n4 = 8;
		System.out.println("Test 4: product(" + m4 + ", " + n4 + ") = " + product(m4, n4));
		System.out.println("  Expected: " + (m4 * n4));
		System.out.println();

		// Test Case 5: Both operands equal
		int m5 = 6, n5 = 6;
		System.out.println("Test 5: product(" + m5 + ", " + n5 + ") = " + product(m5, n5));
		System.out.println("  Expected: " + (m5 * n5));
		System.out.println();

		System.out.println("=== All tests completed ===");
	}
}