package Dispatcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Dispatcher {
    private Map<String, String> jobStatusMap; // Stores job IDs and their statuses
    private Queue<String> jobQueue; // Queue to manage jobs
    private int numberOfWorkers; // Assuming a fixed number of workers
    private Random random; // Random number generator
    private int roundRobinIndex = 0;

    public Dispatcher(int numberOfWorkers) {
        jobStatusMap = new HashMap<>();
        jobQueue = new LinkedList<>();
        this.numberOfWorkers = numberOfWorkers;
        this.random = new Random();
    }

    public void submitJob(String jobId, String strategy) {
        System.out.println("Submitting job: " + jobId);
        jobStatusMap.put(jobId, "RUNNING");
        jobQueue.add(jobId);
        distributeJob(strategy);
    }

    public void cancelJob(String jobId) {
        if (jobStatusMap.containsKey(jobId)) {
            System.out.println("Cancelling job: " + jobId);
            jobStatusMap.put(jobId, "CANCELLED");
        } else {
            System.out.println("Job not found: " + jobId);
        }
    }

    public String getJobStatus(String jobId) {
        return jobStatusMap.getOrDefault(jobId, "UNKNOWN");
    }

    private void distributeJob(String strategy) {
        if (strategy.equalsIgnoreCase("roundRobin")) {
            distributeRoundRobin();
        } else if (strategy.equalsIgnoreCase("random")) {
            distributeRandom();
        }
    }

    private void distributeRoundRobin() {
        if (!jobQueue.isEmpty()) {
            String jobId = jobQueue.poll();
            // Logic to assign jobId to the next available worker
            System.out.println("Distributing job " + jobId + " using Round Robin to worker " + (roundRobinIndex % numberOfWorkers));
            roundRobinIndex++;
        }
    }



    private void distributeRandom() {
        if (!jobQueue.isEmpty()) {
            String jobId = jobQueue.poll();
            // Randomly assign jobId to one of the available workers
            int workerId = random.nextInt(numberOfWorkers);
            System.out.println("Distributing job " + jobId + " using Random to worker " + workerId);
        }
    }
}