package com.example.osproject;
import java.util.*;

public class SJF {
    public static void schedule(List<Process> processes) {
        // Create a priority queue to hold the processes with the highest priority to the shortest remaining time
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));

        int currentTime = 0;

        while (!processes.isEmpty() || !priorityQueue.isEmpty()) {
            // Add all processes that have arrived by the current time to the priority queue
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (process.arrivalTime <= currentTime) {
                    priorityQueue.add(process);
                    iterator.remove();
                }
            }

            // If there are processes in the priority queue, schedule the next one
            if (!priorityQueue.isEmpty()) {
                Process shortestJob = priorityQueue.poll();
                shortestJob.remainingTime--;
                currentTime++;

                // If the process has finished executing, calculate its turnaround time and waiting time
                if (shortestJob.remainingTime == 0) {
                    int tt = shortestJob.turnaroundTime = currentTime - shortestJob.arrivalTime;
                    shortestJob.waitingTime = tt - shortestJob.burstTime;
                } else {
                    // If the process has not finished executing, add it back to the priority queue
                    priorityQueue.add(shortestJob);
                }
            } else {
                // If there are no processes in the priority queue, time++ until process arrive
                currentTime++;
            }
        }
    }
}
