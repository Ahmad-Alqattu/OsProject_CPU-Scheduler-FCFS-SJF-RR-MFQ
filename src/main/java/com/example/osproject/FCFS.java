package com.example.osproject;

import java.util.List;

public class FCFS {
    public void schedule(List<Process> processes) {
        int currentTime = 0;

        // Iterate through each process in the list
        for (Process process : processes) {
            // Check if the process has arrived before the current time
            if (currentTime > process.arrivalTime)
                process.waitingTime = currentTime - process.arrivalTime;
            else {
                // If the process arrives later
                process.waitingTime = 0;
                currentTime = process.arrivalTime;
            }

            // Update current time by the burst time of the current process
            currentTime += process.burstTime;

            // Calculate turnaround time for the current process
            process.turnaroundTime = process.waitingTime + process.burstTime;
        }
    }
}
