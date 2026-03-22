package com.alexkachur.exercise1;

/**
 * Test class for the indexOf(p) method added to LinkedPositionalList. Author:
 * Alex Kachur — COMP-254 Lab Assignment 4
 */
public class Exercise1Test {

	public static void main(String[] args) {

		LinkedPositionalList<String> list = new LinkedPositionalList<>();

		// --- Build the list ---
		Position<String> pA = list.addLast("Alpha");
		Position<String> pB = list.addLast("Beta");
		Position<String> pC = list.addLast("Gamma");
		Position<String> pD = list.addLast("Delta");
		Position<String> pE = list.addLast("Epsilon");

		System.out.println("=== Exercise 1: indexOf(p) Test ===");
		System.out.println("List: " + list);
		System.out.println();

		// --- Normal cases ---
		System.out.println("indexOf(Alpha)   expected 0, got: " + list.indexOf(pA));
		System.out.println("indexOf(Beta)    expected 1, got: " + list.indexOf(pB));
		System.out.println("indexOf(Gamma)   expected 2, got: " + list.indexOf(pC));
		System.out.println("indexOf(Delta)   expected 3, got: " + list.indexOf(pD));
		System.out.println("indexOf(Epsilon) expected 4, got: " + list.indexOf(pE));
		System.out.println();

		// --- Edge case: single-element list ---
		LinkedPositionalList<Integer> single = new LinkedPositionalList<>();
		Position<Integer> p1 = single.addFirst(99);
		System.out.println("Single element list:");
		System.out.println("indexOf(99) expected 0, got: " + single.indexOf(p1));
		System.out.println();

		// --- Edge case: position after removal ---
		// Remove Beta, so Gamma should now be at index 1
		list.remove(pB);
		System.out.println("After removing Beta: " + list);
		System.out.println("indexOf(Gamma) expected 1, got: " + list.indexOf(pC));
		System.out.println("indexOf(Alpha) expected 0, got: " + list.indexOf(pA));
		System.out.println();

		// --- Integer list test ---
		LinkedPositionalList<Integer> nums = new LinkedPositionalList<>();
		Position<Integer> n10 = nums.addLast(10);
		Position<Integer> n20 = nums.addLast(20);
		Position<Integer> n30 = nums.addLast(30);
		System.out.println("Integer list: " + nums);
		System.out.println("indexOf(10) expected 0, got: " + nums.indexOf(n10));
		System.out.println("indexOf(20) expected 1, got: " + nums.indexOf(n20));
		System.out.println("indexOf(30) expected 2, got: " + nums.indexOf(n30));
	}
}
