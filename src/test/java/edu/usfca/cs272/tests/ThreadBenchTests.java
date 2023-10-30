package edu.usfca.cs272.tests;

import static edu.usfca.cs272.tests.ProjectFlag.PARTIAL;
import static edu.usfca.cs272.tests.ProjectFlag.QUERY;
import static edu.usfca.cs272.tests.ProjectFlag.TEXT;
import static edu.usfca.cs272.tests.ProjectFlag.THREADS;
import static edu.usfca.cs272.tests.ProjectPath.QUERY_COMPLEX;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * A test suite for project v3.1 and v3.x.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class ThreadBenchTests extends ProjectBenchmarks {
	/**
	 * Tests the output consistency of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class ConsistencyTests {
		/**
		 * See the JUnit output for test details.
		 */
		@RepeatedTest(3)
		@Order(1)
		@Tag("test-v3.1")
		@Tag("test-v3.2")
		@Tag("test-v3.x")
		public void testCount() {
			new ThreadBuildTests().new ComplexTests().testTextCounts(BENCH_WORKERS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@RepeatedTest(3)
		@Order(2)
		@Tag("test-v3.1")
		@Tag("test-v3.2")
		@Tag("test-v3.x")
		public void testIndex() {
			new ThreadBuildTests().new ComplexTests().testTextIndex(BENCH_WORKERS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@RepeatedTest(3)
		@Order(3)
		@Tag("test-v3.2")
		@Tag("test-v3.x")
		public void testExact() {
			new ThreadSearchTests().new ExactTests().testTextComplex(BENCH_WORKERS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@RepeatedTest(3)
		@Order(4)
		@Tag("test-v3.2")
		@Tag("test-v3.x")
		public void testPartial() {
			new ThreadSearchTests().new PartialTests().testTextComplex(BENCH_WORKERS);
		}
	}

	/**
	 * Tests the output runtime of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class SlowRuntimeTests {
		/** The target speedup to pass these tests. */
		public double target;

		/**
		 * Sets up the tests before running.
		 */
		@BeforeEach
		public void setup() {
			target = ProjectBenchmarks.SLOW_SPEEDUP;
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(1)
		@Tag("test-v3.1")
		public void testIndexOneMany() {
			String[] args1 = { TEXT.flag, ProjectPath.TEXT.text, THREADS.flag, String.valueOf(1) };
			String[] args2 = { TEXT.flag, ProjectPath.TEXT.text, THREADS.flag, BENCH_WORKERS.text };

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Build", "1 Worker", args1, BENCH_WORKERS.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_WORKERS.num, result, target, "1 worker");
				assertTrue(result >= target, debug);
			});
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(2)
		@Tag("test-v3.1")
		public void testIndexSingleMulti() {
			String[] args1 = { TEXT.flag, ProjectPath.TEXT.text };
			String[] args2 = { TEXT.flag, ProjectPath.TEXT.text, THREADS.flag, BENCH_MULTI.text };

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Build", "Single", args1, BENCH_MULTI.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_MULTI.num, result, target, "single-threading");
				assertTrue(result >= target, debug);
			});
		}


		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(3)
		@Tag("test-v3.1")
		public void testSearchOneMany() {
			String[] args1 = {
					TEXT.flag, ProjectPath.TEXT.text, QUERY.flag, QUERY_COMPLEX.text,
					PARTIAL.flag, THREADS.flag, String.valueOf(1)
			};

			String[] args2 = {
					TEXT.flag, ProjectPath.TEXT.text, QUERY.flag, QUERY_COMPLEX.text,
					PARTIAL.flag, THREADS.flag, BENCH_WORKERS.text
			};

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Search", "1 Worker", args1, BENCH_WORKERS.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_WORKERS.num, result, target, "1 worker");
				assertTrue(result >= target, debug);
			});
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(4)
		@Tag("test-v3.1")
		public void testSearchSingleMulti() {
			String[] args1 = { TEXT.flag, ProjectPath.TEXT.text, QUERY.flag, QUERY_COMPLEX.text, PARTIAL.flag };

			String[] args2 = {
					TEXT.flag, ProjectPath.TEXT.text, QUERY.flag, QUERY_COMPLEX.text,
					PARTIAL.flag, THREADS.flag, BENCH_MULTI.text
			};

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Search", "Single", args1, BENCH_MULTI.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_MULTI.num, result, target, "single-threading");
				assertTrue(result >= target, debug);
			});
		}
	}

	/**
	 * Tests the output runtime of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@Order(3)
	@Tag("test-v3.2")
	@TestMethodOrder(OrderAnnotation.class)
	public class GoodRuntimeTests extends SlowRuntimeTests {
		/**
		 * Sets up the tests before running.
		 */
		@Override
		@BeforeEach
		public void setup() {
			super.target = ProjectBenchmarks.GOOD_SPEEDUP;
		}

		@Test
		@Order(1)
		@Override
		public void testIndexOneMany() {
			super.testIndexOneMany();
		}

		@Test
		@Order(2)
		@Override
		public void testIndexSingleMulti() {
			super.testIndexSingleMulti();
		}

		@Test
		@Order(3)
		@Override
		public void testSearchOneMany() {
			super.testSearchOneMany();
		}

		@Test
		@Order(4)
		@Override
		public void testSearchSingleMulti() {
			super.testSearchSingleMulti();
		}
	}

	/**
	 * Tests the output runtime of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@Order(4)
	@Tag("test-v3.x")
	@TestMethodOrder(OrderAnnotation.class)
	public class FastRuntimeTests extends SlowRuntimeTests {
		/**
		 * Sets up the tests before running.
		 */
		@Override
		@BeforeEach
		public void setup() {
			super.target = ProjectBenchmarks.FAST_SPEEDUP;
		}

		@Test
		@Order(1)
		@Override
		public void testIndexOneMany() {
			super.testIndexOneMany();
		}

		@Test
		@Order(2)
		@Tag("past-v4")
		@Tag("past-v5")
		@Override
		public void testIndexSingleMulti() {
			super.testIndexSingleMulti();
		}

		@Test
		@Order(3)
		@Override
		public void testSearchOneMany() {
			super.testSearchOneMany();
		}

		@Test
		@Order(4)
		@Tag("past-v4")
		@Tag("past-v5")
		@Override
		public void testSearchSingleMulti() {
			super.testSearchSingleMulti();
		}
	}

	/** Format string used for debug output. */
	public static final String format = "%d workers has a %.2fx speedup (less than the %.1fx required) compared to %s.";
}
