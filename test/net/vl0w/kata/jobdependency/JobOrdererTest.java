package net.vl0w.kata.jobdependency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JobOrdererTest {

	@Test
	public void noJob() {
		assertTrue(orderJobs("").isEmpty());
	}

	@Test
	public void oneJob() {
		assertEquals("a", orderJobs("a"));
	}

	@Test
	public void multipleJobs() {
		assertEquals("abc", orderJobs("a", "b", "c"));
	}

	@Test
	public void multipleJobs_SingleDependency() {
		assertEquals("cab", orderJobs("a=>c", "b", "c"));
	}

	@Test
	public void multipleJobs_MultipleDependencies() {
		assertEquals("aecbd", orderJobs("a", "b=>c", "c=>e", "d=>a", "e"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void multipleJobs_SelfReferencingDependency() {
		orderJobs("a", "b", "c=>c");
	}

	// @Test(expected = IllegalArgumentException.class)
	// public void multipleJobs_CircularDependency() {
	// orderJobs("a", "b=>c", "c=>d", "d=>b", "e");
	// }

	private String orderJobs(String... jobs) {
		return JobOrderer.orderJobs(jobs);
	}
}
