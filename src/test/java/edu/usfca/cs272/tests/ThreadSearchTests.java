package edu.usfca.cs272.tests;

import static edu.usfca.cs272.tests.ProjectFlag.COUNTS;
import static edu.usfca.cs272.tests.ProjectFlag.INDEX;
import static edu.usfca.cs272.tests.ProjectFlag.PARTIAL;
import static edu.usfca.cs272.tests.ProjectFlag.QUERY;
import static edu.usfca.cs272.tests.ProjectFlag.RESULTS;
import static edu.usfca.cs272.tests.ProjectFlag.TEXT;
import static edu.usfca.cs272.tests.ProjectFlag.THREADS;
import static edu.usfca.cs272.tests.ProjectPath.ACTUAL;
import static edu.usfca.cs272.tests.ProjectPath.EXPECTED;
import static edu.usfca.cs272.tests.ProjectPath.HELLO;
import static edu.usfca.cs272.tests.ProjectPath.QUERY_SIMPLE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.usfca.cs272.tests.ThreadBuildTests.Threads;

/**
 * A test suite for project v3.1.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class ThreadSearchTests extends ProjectTests {
	/**
	 * Tests that threads are being used for this project. These tests are slow and
	 * should only be run when needed. The tests are also imperfect and may not
	 * reliably pass unless on the GitHub Actions environment.
	 */
	@Nested
	@Order(1)
	@Tag("test-v3.1")
	@TestMethodOrder(OrderAnnotation.class)
	public class ApproachTests {
		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(1)
		public void testExact() {
			Runnable test = () -> {
				new ExactTests().testTextComplex(Threads.TWO);
				System.out.println("Random: " + Math.random());
			};

			ProjectTests.testMultithreaded(test);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(2)
		public void testPartial() {
			Runnable test = () -> {
				new PartialTests().testTextComplex(Threads.TWO);
				System.out.println("Random: " + Math.random());
			};

			ProjectTests.testMultithreaded(test);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(2)
	@Tag("test-v3.1")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExactTests {
		/** The default search mode for this nested class. */
		public boolean partial;

		/** The search flag to add to the command-line arguments. */
		public String searchFlag;

		/**
		 * Sets up the tests before running.
		 */
		@BeforeEach
		public void setup() {
			partial = false;
			searchFlag = partial ? PARTIAL.flag : "";
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(1)
		@EnumSource()
		public void testSimple(Threads threads) {
			testSearch(partial, "simple", ProjectPath.SIMPLE, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(2)
		@EnumSource()
		public void testStems(Threads threads) {
			testSearch(partial, "words", ProjectPath.STEMS, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(3)
		@EnumSource()
		public void testRfcs(Threads threads) {
			testSearch(partial, "letters", ProjectPath.RFCS, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(4)
		@EnumSource()
		public void testGutenGreat(Threads threads) {
			testSearch(partial, "complex", ProjectPath.GUTEN_GREAT, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(5)
		@EnumSource()
		public void testGuten(Threads threads) {
			testSearch(partial, "complex", ProjectPath.GUTEN, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(6)
		@EnumSource()
		public void testTextRespect(Threads threads) {
			testSearch(partial, "respect", ProjectPath.TEXT, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(7)
		@EnumSource()
		public void testTextComplex(Threads threads) {
			testSearch(partial, "complex", ProjectPath.TEXT, threads);
		}

		/**
		 * Free up memory after running --- useful for following tests.
		 */
		@AfterAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(3)
	@Tag("test-v3.1")
	@TestMethodOrder(OrderAnnotation.class)
	public class PartialTests extends ExactTests {
		/**
		 * Sets up the tests before running.
		 */
		@Override
		@BeforeEach
		public void setup() {
			super.partial = true;
			super.searchFlag = partial ? PARTIAL.flag : "";
		}
	}

	/**
	 * Tests the index exception handling of this project.
	 */
	@Nested
	@Order(4)
	@Tag("test-v3.1")
	@Tag("test-v3.2")
	@Tag("test-v3.x")
	@Tag("past-v4")
	@Tag("past-v5")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {
		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(1)
		public void testNegativeThreads() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, "-4", QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(2)
		public void testZeroThreads() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, "0", QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(3)
		public void testFractionThreads() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, "3.14", QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(4)
		public void testWordThreads() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, "fox", QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(5)
		public void testDefaultThreads() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(6)
		public void testNoOutput() throws Exception {
			String[] args = { TEXT.flag, HELLO.text, THREADS.flag, QUERY.flag, QUERY_SIMPLE.text };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(7)
		public void testOnlyThreadsAndPartial() throws Exception {
			String[] args = { THREADS.flag, PARTIAL.flag };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws Exception if exception occurs
		 */
		@Test
		@Order(8)
		public void testOnlyThreadsAndOutput() throws Exception {
			String[] args = { THREADS.flag, RESULTS.flag };
			Files.deleteIfExists(RESULTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(RESULTS.path), RESULTS.value);
		}
	}

	/**
	 * All-in-one tests of this project functionality.
	 */
	@Nested
	@Order(5)
	@Tag("past-v4")
	@Tag("past-v5")
	@TestMethodOrder(OrderAnnotation.class)
	public class ComboTests {
		/**
		 * See the JUnit output for test details.
		 *
		 * @param partial whether partial or exact search is enabled
		 */
		@ParameterizedTest
		@ValueSource(booleans = { false, true })
		@Order(1)
		public void testCountsIndexResults(boolean partial) {
			ProjectPath input = ProjectPath.TEXT;
			Threads threads = Threads.FOUR;

			String query = "complex";
			String type = partial ? "partial" : "exact";

			String indexName = String.format("index-%s", input.id);
			String countsName = String.format("counts-%s", input.id);
			String resultsName = String.format("%s-%s-%s", type, query, input.id);

			Path indexActual = ACTUAL.resolve(indexName + "-" + threads.text + ".json");
			Path countsActual = ACTUAL.resolve(countsName + "-" + threads.text + ".json");
			Path resultsActual = ACTUAL.resolve(resultsName + "-" + threads.text + ".json");

			String[] args = {
					TEXT.flag, input.text,
					INDEX.flag, indexActual.toString(),
					COUNTS.flag, countsActual.toString(),
					QUERY.flag, ProjectPath.QUERY.resolve(query + ".txt").toString(),
					partial ? PARTIAL.flag : "",
					RESULTS.flag, resultsActual.toString(),
					THREADS.flag, threads.text
			};

			Map<Path, Path> files = Map.of(
					countsActual, EXPECTED.resolve("counts").resolve(countsName + ".json"),
					indexActual, EXPECTED.resolve("index").resolve(indexName + ".json"),
					resultsActual, EXPECTED.resolve(type).resolve(resultsName + ".json")
			);

			Executable test = () -> ProjectTests.checkOutput(args, files);
			Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, test);
		}
	}

	/**
	 * Generates the arguments to use for this test case. Designed to be used
	 * inside a JUnit test.
	 * @param partial whether to perform exact or partial search
	 * @param query the query file to use for search
	 * @param input the input path to use
	 * @param threads the threads
	 */
	public static void testSearch(boolean partial, String query, ProjectPath input, ThreadBuildTests.Threads threads) {
		String type = partial ? "partial" : "exact";
		String single = String.format("%s-%s-%s.json", type, query, input.id);
		String threaded = String.format("%s-%s-%s-%s.json", type, query, input.id, threads.text);

		Path actual = ACTUAL.resolve(threaded).normalize();
		Path expected = EXPECTED.resolve(type).resolve(single).normalize();
		Path queries = ProjectPath.QUERY.resolve(query + ".txt").normalize();

		String[] args = {
				TEXT.flag, input.text, QUERY.flag, queries.toString(),
				RESULTS.flag, actual.toString(), partial ? PARTIAL.flag : "",
				THREADS.flag, threads.text
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}
}
