package com.example.osproject;

import java.util.*;
import java.util.*;

public class RoundRobin {
    public static void schedule(List<Process> processes, int quantum) {
        int currentTime = 0;
        LinkedList<Process> queue = new LinkedList<>();

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        Process flag = null;  // This flag is used to hold the process that is not finsh execution

        // Continue until all processes have been scheduled
        while (!processes.isEmpty() || !queue.isEmpty()) {
            // Add all processes that have arrived by the current time to the queue
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (process.arrivalTime <= currentTime) {
                    queue.addLast(process);
                    iterator.remove();
                }
            }

            // If a process not finish execution in the last cycle, add it back to the queue
            if (flag != null) {
                queue.addLast(flag);
            }

            // If there are processes in the queue, schedule the next one
            if (!queue.isEmpty()) {
                Process currentProcess = queue.removeFirst();
                int executionTime = Math.min(currentProcess.remainingTime, quantum);
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                // If the process has finished executing, calculate its turnaround time and waiting time
                if (currentProcess.remainingTime == 0) {
                    flag = null;
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                } else {
                    // If the process has not finished executing, set it as the flag
                    flag = currentProcess;
                }
            } else {
                // If there are no processes in the queue, time++ until process arrive
                currentTime++;
            }
        }
    }
}

