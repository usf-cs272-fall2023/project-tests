package edu.usfca.cs272.tests;

import static edu.usfca.cs272.tests.ProjectFlag.INDEX;
import static edu.usfca.cs272.tests.ProjectFlag.QUERY;
import static edu.usfca.cs272.tests.ProjectFlag.RESULTS;
import static edu.usfca.cs272.tests.ProjectFlag.TEXT;
import static edu.usfca.cs272.tests.ProjectFlag.THREADS;
import static edu.usfca.cs272.tests.ProjectPath.GUTEN;
import static edu.usfca.cs272.tests.ProjectPath.HELLO;
import static edu.usfca.cs272.tests.ProjectPath.QUERY_SIMPLE;
import static edu.usfca.cs272.tests.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.tests.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.tests.ProjectTests.testMultithreaded;
import static edu.usfca.cs272.tests.ProjectTests.testNoExceptions;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

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

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws Exception if an error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	@Tag("next-v2.0")
	@Tag("next-v2.1")
	@Tag("next-v2.x")
	public void testThread() throws Exception {
		String[] args = { TEXT.flag, GUTEN.text, THREADS.flag, "2" };

		try {
			// should fail and throw an error
			testMultithreaded(() -> testNoExceptions(args, LONG_TIMEOUT));
		}
		catch (AssertionFailedError | MultipleFailuresError e) {
			Assertions.assertNotNull(e);
			return;
		}

		// should return before here
		fail(debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws Exception if an error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	@Tag("next-v2.0")
	@Tag("next-v2.1")
	@Tag("next-v2.x")
	@Tag("next-v3.0")
	@Tag("next-v3.1")
	@Tag("next-v3.2")
	@Tag("next-v3.x")
	public void testHtmlPage() throws Exception {
		URL base = new URL(CrawlPageTests.GITHUB);
		URL url = new URL(base, "input/simple/hello.html");

		String[] args = {
				ProjectFlag.HTML.flag, url.toString(),
				ProjectFlag.INDEX.flag
		};

		Path actual = ProjectFlag.INDEX.path;
		Path expected = ProjectPath.EXPECTED.resolve("crawl").resolve("simple").resolve("hello-index.json");

		try {
			// should fail and throw an error
			ProjectTests.checkOutput(args, Map.of(actual, expected));
		}
		catch (AssertionFailedError | MultipleFailuresError e) {
			Assertions.assertNotNull(e);
			return;
		}

		// should return before here
		fail(debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws Exception if an error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	@Tag("next-v2.0")
	@Tag("next-v2.1")
	@Tag("next-v2.x")
	@Tag("next-v3.0")
	@Tag("next-v3.1")
	@Tag("next-v3.2")
	@Tag("next-v3.x")
	@Tag("next-v4.0")
	public void testHtmlSite() throws Exception {
		URL base = new URL(CrawlPageTests.GITHUB);
		URL url = new URL(base, "input/simple/");

		String[] args = {
				ProjectFlag.HTML.flag, url.toString(),
				ProjectFlag.MAX.flag, Integer.toString(15),
				ProjectFlag.COUNTS.flag
		};

		Path actual = ProjectFlag.COUNTS.path;
		Path expected = ProjectPath.EXPECTED.resolve("crawl").resolve("simple").resolve("simple-15-counts.json");

		try {
			// should fail and throw an error
			ProjectTests.checkOutput(args, Map.of(actual, expected));
		}
		catch (AssertionFailedError | MultipleFailuresError e) {
			Assertions.assertNotNull(e);
			return;
		}

		// should return before here
		fail(debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws Exception if an error occurs
	 */
	@Test
	@Tag("next-v4.1")
	@Tag("next-v4.x")
	@Tag("next-v5.0")
	@Tag("next-v5.1")
	@Tag("test-v5.0")
	@Tag("test-v5.1")
	public void pass() throws Exception {
		// No next tests for these releases!
	}
}
