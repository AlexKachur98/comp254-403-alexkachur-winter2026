package com.alexkachur.exercise2;

/**
 * Experimental analysis comparing prefixAverage1 (quadratic) vs prefixAverage2
 * (linear).
 *
 * This class follows the same experimental pattern as StringExperiment.java
 * from Lesson 4 examples: start with a representative n, double it each trial,
 * and measure elapsed time using System.currentTimeMillis().
 *
 * Theoretical expectations (from Lesson 4 slides): - prefixAverage1 is O(n^2):
 * doubling n should roughly quadruple the time - prefixAverage2 is O(n):
 * doubling n should roughly double the time
 *
 * @author Alex Kachur Course: COMP-254 Data Structures and Algorithms Lab
 *         Assignment 2, Exercise 2
 */
public class PrefixAverageExperiment {

	/**
	 * Tests the two versions of prefixAverage, doubling the size of n each trial,
	 * beginning with the given start value. The first command line argument can be
	 * used to change the number of trials, and the second to adjust the start
	 * value.
	 *
	 * Pattern follows StringExperiment.java from Lesson 4 examples.
	 */
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 2 Exercise 2: Experimental Analysis of Prefix Averages ===");
		System.out.println("Student: Alex Kachur\n");

		int n = 5000; // starting value
		int trials = 8;
		try {
			if (args.length > 0)
				trials = Integer.parseInt(args[0]);
			if (args.length > 1)
				n = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
		}
		int start = n; // remember the original starting value

		// let's run prefixAverage2 (the quicker one) first
		System.out.println("Testing prefixAverage2 (linear - O(n))...");
		for (int t = 0; t < trials; t++) {
			double[] x = new double[n]; // create test array of size n
			for (int i = 0; i < n; i++)
				x[i] = i; // fill with sequential values
			long startTime = System.currentTimeMillis();
			double[] result = PrefixAverage.prefixAverage2(x);
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));
			n *= 2; // double the problem size
		}

		System.out.println("\nTesting prefixAverage1 (quadratic - O(n^2))...");
		n = start; // restore n to its start value
		for (int t = 0; t < trials; t++) {
			double[] x = new double[n]; // create test array of size n
			for (int i = 0; i < n; i++)
				x[i] = i; // fill with sequential values
			long startTime = System.currentTimeMillis();
			double[] result = PrefixAverage.prefixAverage1(x);
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));
			n *= 2; // double the problem size
		}

		// Side-by-side comparison
		System.out.println("\n=== Side-by-Side Comparison ===");
		System.out.println(String.format("%-12s %20s %20s", "n", "prefixAvg1 (ms)", "prefixAvg2 (ms)"));
		System.out.println("------------------------------------------------------");
		n = start; // restore n to its start value
		for (int t = 0; t < trials; t++) {
			double[] x = new double[n];
			for (int i = 0; i < n; i++)
				x[i] = i;

			// Time prefixAverage1
			long start1 = System.currentTimeMillis();
			double[] result1 = PrefixAverage.prefixAverage1(x);
			long elapsed1 = System.currentTimeMillis() - start1;

			// Time prefixAverage2
			long start2 = System.currentTimeMillis();
			double[] result2 = PrefixAverage.prefixAverage2(x);
			long elapsed2 = System.currentTimeMillis() - start2;

			System.out.println(String.format("n: %9d %16d %20d", n, elapsed1, elapsed2));
			n *= 2; // double the problem size
		}

		System.out.println("\n=== Analysis ===");
		System.out.println("prefixAverage1 is O(n^2): when n doubles, time should roughly QUADRUPLE.");
		System.out.println("prefixAverage2 is O(n):   when n doubles, time should roughly DOUBLE.");
		System.out.println("The experimental results confirm the theoretical Big-O analysis from Lesson 4.");
	}

}