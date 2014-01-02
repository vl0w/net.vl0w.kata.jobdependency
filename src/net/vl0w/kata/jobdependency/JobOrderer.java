package net.vl0w.kata.jobdependency;

import java.util.ArrayList;
import java.util.List;

public class JobOrderer {

	private static final String DEPENDENCY_MARKER = "=>";

	public static String orderJobs(String... jobDescriptions) {
		StringBuilder orderedJobs = new StringBuilder();

		List<String> jobs = getJobs(jobDescriptions);
		List<String> jobDependencies = getJobDependencies(jobDescriptions);

		for (String job : jobs) {
			buildOrderedJobWithDependencies(orderedJobs, job, jobDependencies);
		}

		return orderedJobs.toString();
	}

	private static String buildOrderedJobWithDependencies(
			StringBuilder orderedJobs, String job, List<String> jobDependencies) {
		if (!orderedJobs.toString().contains(job)) {
			if (hasDependantJob(job, jobDependencies)) {
				String dependantJob = getDependantJob(job, jobDependencies);
				buildOrderedJobWithDependencies(orderedJobs, dependantJob,
						jobDependencies);
			}
			orderedJobs.append(job);
		}
		return orderedJobs.toString();
	}

	private static List<String> getJobs(String... jobDescriptions) {
		List<String> jobs = new ArrayList<>();
		for (String jobDescription : jobDescriptions) {
			String job = jobDescription;

			if (jobDescription.contains(DEPENDENCY_MARKER)) {
				job = jobDescription.substring(0,
						jobDescription.indexOf(DEPENDENCY_MARKER));
			}

			jobs.add(job);
		}

		return jobs;
	}

	private static List<String> getJobDependencies(String... jobDescriptions) {
		List<String> jobDependencies = new ArrayList<>();
		for (String jobDescription : jobDescriptions) {
			if (jobDescription.contains(DEPENDENCY_MARKER)) {
				String dependingJob = jobDescription.substring(0,
						jobDescription.indexOf(DEPENDENCY_MARKER));
				String job = jobDescription.substring(jobDescription
						.indexOf(DEPENDENCY_MARKER)
						+ DEPENDENCY_MARKER.length());
				jobDependencies.add(dependingJob + job);

			}
		}

		return jobDependencies;
	}

	private static boolean hasDependantJob(String job,
			List<String> jobDependencies) {
		return getDependantJob(job, jobDependencies) != null;
	}

	private static String getDependantJob(String job,
			List<String> jobDependencies) {
		for (String jobDependency : jobDependencies) {
			if (jobDependency.startsWith(job)) {
				return jobDependency.substring(job.length());
			}
		}
		return null;
	}
}
