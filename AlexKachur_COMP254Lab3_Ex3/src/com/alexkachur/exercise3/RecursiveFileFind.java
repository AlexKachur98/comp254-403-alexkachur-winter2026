/**
 * COMP-254 Data Structures and Algorithms
 * Lab Assignment 3 - Exercise 3
 * 
 * Recursive method to find all entries in a file system with a given filename.
 * Modeled on the diskUsage method from DiskSpace.java (Code Fragment 5.5) from Week 5 examples.
 * 
 * Uses java.io.File class as shown in the textbook's DiskSpace example.
 * 
 * @author Alex Kachur
 */
package com.alexkachur.exercise3;

import java.io.File;

public class RecursiveFileFind {

	/**
	 * Recursively searches the file system rooted at the given path for all entries
	 * matching the given filename.
	 *
	 * Follows the diskUsage pattern from Code Fragment 5.5: Start with the root
	 * entry, If it is a directory, iterate through its children. Recursively
	 * search each child, The base case is when the entry is a file (not a directory)
	 *
	 * @param path the root path to start searching from
	 * @param filename the filename to search for
	 */
	public static void find(String path, String filename) {
		File root = new File(path); // compose full path

		if (!root.exists()) { // validate the path
			System.out.println("  Path does not exist: " + path);
			return;
		}

		// check if the current entry's name matches the target filename
		if (root.getName().equals(filename)) {
			System.out.println("  Found: " + root.getAbsolutePath());
		}

		// if this entry is a directory, recursively search its children
		if (root.isDirectory()) { // and if this is a directory,
			String[] children = root.list(); // then for each child
			if (children != null) {
				for (String childname : children) { // iterate through children
					find(path + File.separator + childname, filename); // recurse on child
				}
			}
		}
		// base case: if root is a file (not a directory), we simply return
	}

	/** Tests the find method with real file system paths. */
	public static void main(String[] args) {
		System.out.println("=== COMP-254 Lab 3 Exercise 3: Recursive File Find ===");
		System.out.println();

		// Test Case 1: Search for this source file in the current project directory
		String projectPath = System.getProperty("user.dir");
		String targetFile = "RecursiveFileFind.java";

		System.out.println("Test 1: Searching for \"" + targetFile + "\"");
		System.out.println("  Starting from: " + projectPath);
		System.out.println("  Results:");
		find(projectPath, targetFile);
		System.out.println();

		// Test Case 2: Search the Eclipse workspace for another Lab 3 file
		// (goes one level up from the project to search sibling projects too)
		File workspace = new File(projectPath).getParentFile();
		String workspacePath = workspace.getAbsolutePath();
		String targetFile2 = "RecursiveProduct.java";

		System.out.println("Test 2: Searching for \"" + targetFile2 + "\"");
		System.out.println("  Starting from: " + workspacePath);
		System.out.println("  Results:");
		find(workspacePath, targetFile2);
		System.out.println();

		// Test Case 3: Search for a file that does not exist
		String targetFile3 = "nonexistentfile.xyz";

		System.out.println("Test 3: Searching for \"" + targetFile3 + "\" (should find nothing)");
		System.out.println("  Starting from: " + projectPath);
		System.out.println("  Results:");
		find(projectPath, targetFile3);
		System.out.println("  (No results - file not found)");
		System.out.println();

		// Test Case 4: Search in a non-existent path
		String badPath = "/this/path/does/not/exist";

		System.out.println("Test 4: Searching in non-existent path");
		System.out.println("  Starting from: " + badPath);
		System.out.println("  Results:");
		find(badPath, "anyfile.txt");
		System.out.println();

		System.out.println("=== All tests completed ===");
	}
}