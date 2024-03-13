package com.example.osproject;

import java.util.*;

import java.util.*;

public class MultilevelFeedbackQueue {
    public void schedule(List<Process> processes, int quantumQ1, int quantumQ2) {
        // queue for each level
        LinkedList<Process> queueQ1 = new LinkedList<>();
        LinkedList<Process> queueQ2 = new LinkedList<>();
        LinkedList<Process> queueQ3 = new LinkedList<>();

        //sort processes by arrival time initially
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;

        // Continue until all processes have been scheduled
        while (!processes.isEmpty() || !queueQ1.isEmpty() || !queueQ2.isEmpty() || !queueQ3.isEmpty()) {
            // Add all processes that have arrived by the current time to the first queue
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                Process process = processes.remove(0);
                queueQ1.addLast(process);
            }

            // If there are processes in the first queue, schedule the next one
            if (!queueQ1.isEmpty()) {
                Process currentProcess = queueQ1.removeFirst();
                int executionTime = Math.min(currentProcess.remainingTime, quantumQ1);
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                // If the process has not finished executing, move it to the second queue
                if (currentProcess.remainingTime > 0) {
                    queueQ2.addLast(currentProcess);
                } else {
                    // If the process has finished executing, calculate its turnaround time and waiting time
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                }
            } else if (!queueQ2.isEmpty()) {
                // If there are no processes in the first queue, schedule from the second queue
                Process currentProcess = queueQ2.removeFirst();
                int executionTime = Math.min(currentProcess.remainingTime, quantumQ2);
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                // If the process has not finished executing, move it to the third queue
                if (currentProcess.remainingTime > 0) {
                    queueQ3.addLast(currentProcess);
                } else {
                    // If the process has finished executing, calculate its turnaround time and waiting time
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                }
            } else if (!queueQ3.isEmpty()) {
                // If there are no processes in the first and second queues, schedule from the third queue FCFS
                Process currentProcess = queueQ3.removeFirst();
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
            } else {
                // If there are no processes in any queue, increment time
                currentTime++;
            }
        }
    }
}
