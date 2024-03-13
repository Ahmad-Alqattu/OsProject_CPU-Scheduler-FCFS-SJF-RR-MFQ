package com.example.osproject;

import java.util.Random;

public class Process {
int pid; // Process ID
int burstTime; // Burst Time of the process
int arrivalTime; // Arrival Time of the process
int remainingTime; // Remaining Burst Time
int waitingTime; // Waiting Time
int turnaroundTime; // Turnaround Time

public Process(int pid, int burstTime, int arrivalTime) {
this.pid = pid;
this.burstTime = burstTime;
this.arrivalTime = arrivalTime;
this.remainingTime = burstTime;
this.waitingTime = 0;
this.turnaroundTime = 0;

}

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", burstTime=" + burstTime +
                ", arrivalTime=" + arrivalTime +
                ", remainingTime=" + remainingTime +
                ", waitingTime=" + waitingTime +
                ", turnaroundTime=" + turnaroundTime +
                '}';
    }
}
