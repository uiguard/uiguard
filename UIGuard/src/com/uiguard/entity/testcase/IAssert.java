package com.uiguard.entity.testcase;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface IAssert {

	/**
	 * Asserts that a condition is true. If it isn't,
	 * an AssertionError, with the given message, is thrown.
	 * @param condition the condition to evaluate
	 * @param message the assertion error message
	 */
	public abstract void assertTrue(boolean condition, String message);

	/**
	 * Asserts that a condition is true. If it isn't,
	 * an AssertionError is thrown.
	 * @param condition the condition to evaluate
	 */
	public abstract void assertTrue(boolean condition);

	/**
	 * Asserts that a condition is false. If it isn't,
	 * an AssertionError, with the given message, is thrown.
	 * @param condition the condition to evaluate
	 * @param message the assertion error message
	 */
	public abstract void assertFalse(boolean condition, String message);

	/**
	 * Asserts that a condition is false. If it isn't,
	 * an AssertionError is thrown.
	 * @param condition the condition to evaluate
	 */
	public abstract void assertFalse(boolean condition);

	/**
	 * Fails a test with the given message and wrapping the original exception.
	 *
	 * @param message the assertion error message
	 * @param realCause the original exception
	 */
	public abstract void fail(String message, Throwable realCause);

	/**
	 * Fails a test with the given message.
	 * @param message the assertion error message
	 */
	public abstract void fail(String message);

	/**
	 * Fails a test with no message.
	 */
	public abstract void fail();

	/**
	 * Asserts that two objects are equal. If they are not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(Object actual, Object expected,
			String message);

	/**
	 * Asserts that two objects are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(Object actual, Object expected);

	/**
	 * Asserts that two Strings are equal. If they are not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(String actual, String expected,
			String message);

	/**
	 * Asserts that two Strings are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(String actual, String expected);

	/**
	 * Asserts that two doubles are equal concerning a delta.  If they are not,
	 * an AssertionError, with the given message, is thrown.  If the expected
	 * value is infinity then the delta value is ignored.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param delta the absolute tolerable difference between the actual and expected values
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(double actual, double expected,
			double delta, String message);

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not,
	 * an AssertionError is thrown. If the expected value is infinity then the
	 * delta value is ignored.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param delta the absolute tolerable difference between the actual and expected values
	 */
	public abstract void assertEquals(double actual, double expected,
			double delta);

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not,
	 * an AssertionError, with the given message, is thrown.  If the expected
	 * value is infinity then the delta value is ignored.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param delta the absolute tolerable difference between the actual and expected values
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(float actual, float expected,
			float delta, String message);

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not,
	 * an AssertionError is thrown. If the expected
	 * value is infinity then the delta value is ignored.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param delta the absolute tolerable difference between the actual and expected values
	 */
	public abstract void assertEquals(float actual, float expected, float delta);

	/**
	 * Asserts that two longs are equal. If they are not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(long actual, long expected, String message);

	/**
	 * Asserts that two longs are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(long actual, long expected);

	/**
	 * Asserts that two booleans are equal. If they are not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(boolean actual, boolean expected,
			String message);

	/**
	 * Asserts that two booleans are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(boolean actual, boolean expected);

	/**
	 * Asserts that two bytes are equal. If they are not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(byte actual, byte expected, String message);

	/**
	 * Asserts that two bytes are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(byte actual, byte expected);

	/**
	 * Asserts that two chars are equal. If they are not,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(char actual, char expected, String message);

	/**
	 * Asserts that two chars are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(char actual, char expected);

	/**
	 * Asserts that two shorts are equal. If they are not,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(short actual, short expected,
			String message);

	/**
	 * Asserts that two shorts are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(short actual, short expected);

	/**
	 * Asserts that two ints are equal. If they are not,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(int actual, int expected, String message);

	/**
	 * Asserts that two ints are equal. If they are not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(int actual, int expected);

	/**
	 * Asserts that an object isn't null. If it is,
	 * an AssertionError is thrown.
	 * @param object the assertion object
	 */
	public abstract void assertNotNull(Object object);

	/**
	 * Asserts that an object isn't null. If it is,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param object the assertion object
	 * @param message the assertion error message
	 */
	public abstract void assertNotNull(Object object, String message);

	/**
	 * Asserts that an object is null. If it is not,
	 * an AssertionError, with the given message, is thrown.
	 * @param object the assertion object
	 */
	public abstract void assertNull(Object object);

	/**
	 * Asserts that an object is null. If it is not,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param object the assertion object
	 * @param message the assertion error message
	 */
	public abstract void assertNull(Object object, String message);

	/**
	 * Asserts that two objects refer to the same object. If they do not,
	 * an AssertionFailedError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertSame(Object actual, Object expected,
			String message);

	/**
	 * Asserts that two objects refer to the same object. If they do not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertSame(Object actual, Object expected);

	/**
	 * Asserts that two objects do not refer to the same objects. If they do,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertNotSame(Object actual, Object expected,
			String message);

	/**
	 * Asserts that two objects do not refer to the same object. If they do,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertNotSame(Object actual, Object expected);

	/**
	 * Asserts that two collections contain the same elements in the same order. If they do not,
	 * an AssertionError is thrown.
	 *
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(Collection<?> actual,
			Collection<?> expected);

	/**
	 * Asserts that two collections contain the same elements in the same order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(Collection<?> actual,
			Collection<?> expected, String message);

	/** Asserts that two iterators return the same elements in the same order. If they do not,
	 * an AssertionError is thrown.
	 * Please note that this assert iterates over the elements and modifies the state of the iterators.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(Iterator<?> actual, Iterator<?> expected);

	/** Asserts that two iterators return the same elements in the same order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 * Please note that this assert iterates over the elements and modifies the state of the iterators.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(Iterator<?> actual, Iterator<?> expected,
			String message);

	/** Asserts that two iterables return iterators with the same elements in the same order. If they do not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(Iterable<?> actual, Iterable<?> expected);

	/** Asserts that two iterables return iterators with the same elements in the same order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(Iterable<?> actual, Iterable<?> expected,
			String message);

	/**
	 * Asserts that two arrays contain the same elements in the same order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(Object[] actual, Object[] expected,
			String message);

	/**
	 * Asserts that two arrays contain the same elements in no particular order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEqualsNoOrder(Object[] actual,
			Object[] expected, String message);

	/**
	 * Asserts that two arrays contain the same elements in the same order. If they do not,
	 * an AssertionError is thrown.
	 *
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(Object[] actual, Object[] expected);

	/**
	 * Asserts that two arrays contain the same elements in no particular order. If they do not,
	 * an AssertionError is thrown.
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEqualsNoOrder(Object[] actual, Object[] expected);

	/**
	 * Asserts that two arrays contain the same elements in the same order. If they do not,
	 * an AssertionError is thrown.
	 *
	 * @param actual the actual value
	 * @param expected the expected value
	 */
	public abstract void assertEquals(final byte[] actual, final byte[] expected);

	/**
	 * Asserts that two arrays contain the same elements in the same order. If they do not,
	 * an AssertionError, with the given message, is thrown.
	 *
	 * @param actual the actual value
	 * @param expected the expected value
	 * @param message the assertion error message
	 */
	public abstract void assertEquals(final byte[] actual,
			final byte[] expected, final String message);

	/**
	 * Asserts that two sets are equal.
	 */
	public abstract void assertEquals(Set<?> actual, Set<?> expected);

	/**
	 * Assert set equals
	 */
	public abstract void assertEquals(Set<?> actual, Set<?> expected,
			String message);

	/**
	 * Asserts that two maps are equal.
	 */
	public abstract void assertEquals(Map<?, ?> actual, Map<?, ?> expected);

	public abstract void assertNotEquals(Object actual1, Object actual2,
			String message);

	public abstract void assertNotEquals(Object actual1, Object actual2);

	public abstract void assertNotEquals(float actual1, float actual2,
			float delta, String message);

	public abstract void assertNotEquals(float actual1, float actual2,
			float delta);

	public abstract void assertNotEquals(double actual1, double actual2,
			double delta, String message);

	public abstract void assertNotEquals(double actual1, double actual2,
			double delta);

}