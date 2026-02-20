/**
 * COMP-254 Data Structures and Algorithms
 * Lab Assignment 3 - Exercise 2
 * 
 * Recursive method to determine if a string is a palindrome.
 * Uses the technique of comparing first and last characters
 * and recursing on the substring in between.
 * 
 * Based on the reverseArray pattern from Week 5 examples
 * 
 * @author Alex Kachur
 */
package com.alexkachur.exercise2;

import java.util.Scanner;

public class RecursivePalindrome {

	/**
	 * Determines if a string is a palindrome using recursion. Follows the
	 * reverseArray pattern (two-pointer recursion): - Base case: if the string has
	 * 0 or 1 characters, it is a palindrome - Recursive case: check if the first
	 * and last characters match, then recurse on the substring between them
	 *
	 * Handles both odd-length strings (e.g., "racecar") and even-length strings
	 * (e.g., "abba") correctly.
	 *
	 * @param s the string to check
	 * @return true if s is a palindrome, false otherwise
	 */
	public static boolean isPalindrome(String s) {
		if (s.length() <= 1) // base case: 0 or 1 char
			return true;
		else {
			// check first and last characters
			if (s.charAt(0) != s.charAt(s.length() - 1))
				return false; // mismatch found
			// recurse on the substring between first and last
			return isPalindrome(s.substring(1, s.length() - 1));
		}
	}

	/** Tests the palindrome method with hardcoded cases and user input. */
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 3 Exercise 2: Recursive Palindrome ===");
		System.out.println();

		// Hardcoded test cases
		String[] testCases = { "racecar", "gohangasalamiimalasagnahog", "hello", "abba", "a", "" };

		for (String test : testCases) {
			System.out.println("  isPalindrome(\"" + test + "\") = " + isPalindrome(test));
		}
		System.out.println();

		// User input section
		Scanner scanner = new Scanner(System.in);
		String input = "";

		System.out.println("Enter strings to check (type 'quit' to exit):");

		while (true) {
			System.out.print("  Enter a string: ");
			input = scanner.nextLine();

			if (input.equalsIgnoreCase("quit"))
				break;

			boolean result = isPalindrome(input);
			System.out.println("  \"" + input + "\" is" + (result ? "" : " NOT") + " a palindrome.");
			System.out.println();
		}

		scanner.close();
		System.out.println();
		System.out.println("=== Program ended ===");
	}
}