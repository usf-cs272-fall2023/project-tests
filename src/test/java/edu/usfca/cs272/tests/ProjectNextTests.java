package edu.usfca.cs272.tests;

import static edu.usfca.cs272.tests.ProjectFlag.INDEX;
import static edu.usfca.cs272.tests.ProjectFlag.QUERY;
import static edu.usfca.cs272.tests.ProjectFlag.RESULTS;
import static edu.usfca.cs272.tests.ProjectFlag.TEXT;
import static edu.usfca.cs272.tests.ProjectPath.HELLO;
import static edu.usfca.cs272.tests.ProjectPath.QUERY_SIMPLE;
import static edu.usfca.cs272.tests.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.tests.ProjectTests.testNoExceptions;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests that next project code is not in the current project. This class should
 * not be run directly; it is run by GitHub Actions only.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class ProjectNextTests {
	/** Message output when a test fails. */
	public static final String debug = "You must place functionality for the next project in a separate branch. It should not be in the current version of your project in the main branch!";

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next-v1.0")
	public void testIndexOutput() throws IOException {
		String[] args = { TEXT.flag, HELLO.text, QUERY.flag, QUERY_SIMPLE.text, INDEX.flag };
		Files.deleteIfExists(INDEX.path);
		testNoExceptions(args, SHORT_TIMEOUT);
		Assertions.assertFalse(Files.exists(INDEX.path), debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	public void testSearchOutput() throws IOException {
		String[] args = { TEXT.flag, HELLO.text, QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
		Files.deleteIfExists(RESULTS.path);
		testNoExceptions(args, SHORT_TIMEOUT);
		Assertions.assertFalse(Files.exists(RESULTS.path), debug);
	}
}
