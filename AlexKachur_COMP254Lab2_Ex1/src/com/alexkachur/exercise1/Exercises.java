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

// Modified by: Alex Kachur — Added Big-O analysis comments for Lab Assignment 2

package com.alexkachur.exercise1;

/**
 * Code for end-of-chapter exercises on asymptotics.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
class Exercises {

	//
	// ===================== example1: Big-O Analysis =====================
	//
	// Big-O: O(n)
	//
	// There is a single for loop that iterates from j = 0 to n-1,
	// running exactly n times. Each iteration does a constant amount
	// of work (one array index + one addition + one assignment).
	// Total primitive operations: 5n + 4.
	// Dropping constants and lower-order terms: O(n).
	//
	/** Returns the sum of the integers in given array. */
	public static int example1(int[] arr) {
		int n = arr.length, total = 0; // 2 ops
		for (int j = 0; j < n; j++) // loop from 0 to n-1: runs n times
			total += arr[j]; // 3 ops per iteration (index + add + assign)
		return total; // 1 op
	}

	//
	// ===================== example2: Big-O Analysis =====================
	//
	// Big-O: O(n)
	//
	// There is a single for loop that iterates from j = 0 with increment 2,
	// so it runs approximately n/2 times. Each iteration does constant work.
	// Total primitive operations: approximately 5(n/2) + 4 = 5n/2 + 4.
	// Since n/2 is still proportional to n (constant factor 1/2 is dropped),
	// the Big-O is O(n). It is NOT O(n/2) — Big-O drops constant factors.
	//
	/** Returns the sum of the integers with even index in given array. */
	public static int example2(int[] arr) {
		int n = arr.length, total = 0; // 2 ops
		for (int j = 0; j < n; j += 2) // note the increment of 2: runs n/2 times
			total += arr[j]; // 3 ops per iteration
		return total; // 1 op
	}

	//
	// ===================== example3: Big-O Analysis =====================
	//
	// Big-O: O(n^2)
	//
	// There are two nested for loops. The outer loop runs n times (j from
	// 0 to n-1). For each value of j, the inner loop runs (j+1) times
	// (k from 0 to j). The total inner loop iterations are:
	// 1 + 2 + 3 + ... + n = n(n+1)/2
	// This is a polynomial of degree 2 (the dominant term is n^2/2).
	// Dropping constants: O(n^2).
	//
	/** Returns the sum of the prefix sums of given array. */
	public static int example3(int[] arr) {
		int n = arr.length, total = 0; // 2 ops
		for (int j = 0; j < n; j++) // outer loop from 0 to n-1
			for (int k = 0; k <= j; k++) // inner loop from 0 to j: total = n(n+1)/2
				total += arr[j]; // constant work
		return total; // 1 op
	}

	//
	// ===================== example4: Big-O Analysis =====================
	//
	// Big-O: O(n)
	//
	// There is a single for loop that runs n times (j from 0 to n-1).
	// Each iteration does constant work (two additions and two assignments).
	// There are NO nested loops. This method computes the same result as
	// example3 but uses a running prefix sum to avoid recalculating, 
	// the same optimization as prefixAverage2 vs prefixAverage1.
	// Total primitive operations: 5n + 4. Dropping constants: O(n).
	//
	/** Returns the sum of the prefix sums of given array. */
	public static int example4(int[] arr) {
		int n = arr.length, prefix = 0, total = 0; // 3 ops
		for (int j = 0; j < n; j++) { // loop from 0 to n-1: runs n times
			prefix += arr[j]; // constant work per iteration
			total += prefix; // constant work per iteration
		}
		return total; // 1 op
	}

	//
	// ===================== example5: Big-O Analysis =====================
	//
	// Big-O: O(n^3)
	//
	// There are three levels of loops. The outer loop (i) runs n times.
	// For each outer iteration, the middle loop (j) runs n times, and the
	// inner loop (k) runs from 0 to j. The middle + inner combined do:
	// 1 + 2 + 3 + ... + n = n(n+1)/2 iterations (same as example3).
	// Since this n(n+1)/2 work happens for EACH of the n outer iterations:
	// Total = n * n(n+1)/2 = n^2(n+1)/2 = (n^3 + n^2)/2
	// This is a polynomial of degree 3. Dropping constants: O(n^3).
	//
	/**
	 * Returns the number of times second array stores sum of prefix sums from
	 * first.
	 */
	public static int example5(int[] first, int[] second) { // assume equal-length arrays
		int n = first.length, count = 0; // 2 ops
		for (int i = 0; i < n; i++) { // outer loop from 0 to n-1: n iterations
			int total = 0; // 1 op per outer iteration
			for (int j = 0; j < n; j++) // middle loop from 0 to n-1
				for (int k = 0; k <= j; k++) // inner loop from 0 to j
					total += first[k]; // constant work
			if (second[i] == total)
				count++; // constant work per outer iteration
		}
		return count; // 1 op
	}

	//
	// ===================== SUMMARY TABLE =====================
	//
	// Method   | Loop Structure                       | Total Iterations   | Big-O
	// ---------|--------------------------------------|--------------------|---------
	// example1 | Single loop (0 to n-1)               | n                  | O(n)
	// example2 | Single loop (0 to n-1, step 2)       | n/2                | O(n)
	// example3 | Nested: j(0->n-1), k(0->j)           | n(n+1)/2           | O(n^2)
	// example4 | Single loop with running sum         | n                  | O(n)
	// example5 | Triple: i(0->n-1), j(0->n-1), k(0->j)| n * n(n+1)/2       | O(n^3)
	//

	// ==================== main method ====================
	// Written by: Alex Kachur — demonstrates and verifies each method
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 2 Exercise 1: Big-O Analysis of Exercises.java ===");
		System.out.println("Student: Alex Kachur\n");

		int[] arr = { 1, 2, 3, 4, 5 };
		int n = arr.length;
		System.out.println("Test array: {1, 2, 3, 4, 5}, n = " + n + "\n");

		System.out.println("example1 result: " + example1(arr));
		System.out.println("  Sum of all elements: 1+2+3+4+5 = 15");
		System.out.println("  Big-O: O(n) — single loop, n iterations\n");

		System.out.println("example2 result: " + example2(arr));
		System.out.println("  Sum of even-indexed: arr[0]+arr[2]+arr[4] = 1+3+5 = 9");
		System.out.println("  Big-O: O(n) — single loop, n/2 iterations (constant factor dropped)\n");

		System.out.println("example3 result: " + example3(arr));
		System.out.println("  Sum of prefix sums (nested loops)");
		System.out.println("  Big-O: O(n^2) — nested loops, 1+2+...+n = n(n+1)/2 iterations\n");

		System.out.println("example4 result: " + example4(arr));
		System.out.println("  Sum of prefix sums (running sum — same result as example3)");
		System.out.println("  Big-O: O(n) — single loop with running prefix variable\n");

		System.out.println("example5 result: " + example5(arr, arr));
		System.out.println("  Count of matches between second[] and prefix-sum-of-sums");
		System.out.println("  Big-O: O(n^3) — three nested loops\n");

		System.out.println("=== Summary ===");
		System.out.println("example1: O(n)   | example2: O(n)   | example3: O(n^2)");
		System.out.println("example4: O(n)   | example5: O(n^3)");
	}

}