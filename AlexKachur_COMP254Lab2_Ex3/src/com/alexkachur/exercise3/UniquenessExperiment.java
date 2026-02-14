package com.alexkachur.exercise3;

/**
 * Experimental analysis to determine the largest value of n such that unique1
 * and unique2 each run in one minute (60 seconds) or less.
 *
 * Uses a "binary search" strategy as suggested in the assignment hint: Phase 1:
 * Double n until the algorithm exceeds 60 seconds (find upper bound) Phase 2:
 * Binary search between [low, high] to narrow down the maximum n
 *
 * Timing pattern follows StringExperiment.java from Lesson 4 examples: long
 * startTime = System.currentTimeMillis(); // run the algorithm long endTime =
 * System.currentTimeMillis(); long elapsed = endTime - startTime;
 *
 * Theoretical expectations (from Lesson 4 slides): - unique1 is O(n^2): slower
 * growth, smaller maximum n - unique2 is O(n log n): faster growth, much larger
 * maximum n
 *
 * @author Alex Kachur Course: COMP-254 Data Structures and Algorithms Lab
 *         Assignment 2, Exercise 3
 */
public class UniquenessExperiment {

	/** Time limit in milliseconds (60 seconds = 60,000 ms). */
	private static final long TIME_LIMIT = 60000;

	/**
	 * Generates an array of guaranteed unique integers. Uses sequential values to
	 * ensure uniqueness, which forces unique1 to check ALL pairs (worst case for
	 * timing).
	 */
	private static int[] generateUniqueArray(int n) {
		int[] data = new int[n];
		for (int i = 0; i < n; i++) {
			data[i] = i;
		}
		return data;
	}

	/**
	 * Measures the running time of unique1 for a given array size n. Follows the
	 * timing pattern from StringExperiment.java.
	 * 
	 * @return elapsed time in milliseconds
	 */
	private static long timeUnique1(int n) {
		int[] data = generateUniqueArray(n);
		long startTime = System.currentTimeMillis();
		Uniqueness.unique1(data);
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	/**
	 * Measures the running time of unique2 for a given array size n. Follows the
	 * timing pattern from StringExperiment.java. Returns Long.MAX_VALUE if the
	 * array is too large to fit in memory, which signals that n has exceeded the
	 * practical limit.
	 * 
	 * @return elapsed time in milliseconds, or Long.MAX_VALUE if out of memory
	 */
	private static long timeUnique2(int n) {
		try {
			int[] data = generateUniqueArray(n);
			long startTime = System.currentTimeMillis();
			Uniqueness.unique2(data);
			long endTime = System.currentTimeMillis();
			return endTime - startTime;
		} catch (OutOfMemoryError e) {
			System.out.println("    (Out of memory at n=" + n + ", treating as exceeded limit)");
			return Long.MAX_VALUE; // signal that this n is too large
		}
	}

	/**
	 * Finds the maximum n for unique1 that runs within the time limit. Phase 1:
	 * Doubling to find upper bound. Phase 2: Binary search to narrow down.
	 */
	private static int findMaxN_unique1() {
		System.out.println("--- Finding maximum n for unique1 (O(n^2)) ---\n");

		// Phase 1: Double n until we exceed the time limit
		System.out.println("Phase 1: Doubling n to find upper bound...");
		int n = 1000;
		long elapsed = 0;
		while (true) {
			elapsed = timeUnique1(n);
			System.out.println(String.format("  n: %9d  time: %8d ms", n, elapsed));
			if (elapsed > TIME_LIMIT)
				break;
			n *= 2; // double the problem size
		}
		int high = n;
		int low = n / 2;
		System.out.println(String.format("  Upper bound: %d, Lower bound: %d\n", high, low));

		// Phase 2: Binary search between [low, high]
		System.out.println("Phase 2: Binary search to narrow down...");
		while (high - low > 1000) {
			int mid = (low + high) / 2;
			elapsed = timeUnique1(mid);
			System.out.println(String.format("  n: %9d  time: %8d ms  %s", mid, elapsed,
					elapsed <= TIME_LIMIT ? "<= 60s" : "> 60s"));
			if (elapsed <= TIME_LIMIT) {
				low = mid;
			} else {
				high = mid;
			}
		}

		System.out.println(String.format("\n  Result: maximum n for unique1 is approximately %d\n", low));
		return low;
	}

	/**
	 * Finds the maximum n for unique2 that runs within the time limit. Phase 1:
	 * Doubling to find upper bound. Phase 2: Binary search to narrow down.
	 *
	 * Note: unique2 is so fast (O(n log n)) that it may hit Java's memory limit
	 * before hitting the 60-second time limit. In that case, the memory limit
	 * becomes the practical upper bound.
	 */
	private static int findMaxN_unique2() {
		System.out.println("--- Finding maximum n for unique2 (O(n log n)) ---\n");

		// Phase 1: Double n until we exceed time limit or run out of memory
		System.out.println("Phase 1: Doubling n to find upper bound...");
		int n = 10000;
		long elapsed = 0;
		while (true) {
			elapsed = timeUnique2(n);
			if (elapsed == Long.MAX_VALUE) {
				// Out of memory — use this n as upper bound
				System.out.println(String.format("  n: %9d  time:   OUT OF MEMORY", n));
				break;
			}
			System.out.println(String.format("  n: %9d  time: %8d ms", n, elapsed));
			if (elapsed > TIME_LIMIT)
				break;
			n *= 2; // double the problem size
		}
		int high = n;
		int low = n / 2;
		System.out.println(String.format("  Upper bound: %d, Lower bound: %d\n", high, low));

		// Phase 2: Binary search between [low, high]
		System.out.println("Phase 2: Binary search to narrow down...");
		while (high - low > 100000) {
			int mid = (low + high) / 2;
			elapsed = timeUnique2(mid);
			if (elapsed == Long.MAX_VALUE) {
				// Out of memory — this n is too large
				System.out.println(String.format("  n: %9d  time:   OUT OF MEMORY  > limit", mid));
				high = mid;
			} else {
				System.out.println(String.format("  n: %9d  time: %8d ms  %s", mid, elapsed,
						elapsed <= TIME_LIMIT ? "<= 60s" : "> 60s"));
				if (elapsed <= TIME_LIMIT) {
					low = mid;
				} else {
					high = mid;
				}
			}
		}

		System.out.println(String.format("\n  Result: maximum n for unique2 is approximately %d\n", low));
		return low;
	}

	/**
	 * Main method — runs the experimental analysis for both uniqueness algorithms.
	 */
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 2 Exercise 3: Maximum n for Uniqueness Algorithms ===");
		System.out.println("Student: Alex Kachur");
		System.out.println("Time limit: 60 seconds (60,000 milliseconds)");
		System.out.println("Strategy: Doubling to find bounds, then binary search to narrow down.\n");

		// Find max n for unique1 (O(n^2))
		int maxN1 = findMaxN_unique1();

		// Find max n for unique2 (O(n log n))
		int maxN2 = findMaxN_unique2();

		// Summary
		System.out.println("=== SUMMARY ===");
		System.out.println(String.format("unique1 (O(n^2)):     max n = approximately %,d", maxN1));
		System.out.println(String.format("unique2 (O(n log n)): max n = approximately %,d", maxN2));
		System.out.println();
		if (maxN2 > maxN1) {
			System.out.println("unique2 can handle approximately " + (maxN2 / maxN1) + "x more elements than unique1.");
		}
		System.out.println("This confirms that O(n log n) grows much slower than O(n^2),");
		System.out.println("as discussed in Lesson 4 (Goodrich, Tamassia & Goldwasser Chapter 4).");
	}

}