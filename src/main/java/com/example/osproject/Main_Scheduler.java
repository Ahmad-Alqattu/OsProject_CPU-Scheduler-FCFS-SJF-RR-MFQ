/*
* Ahmad Luay Al-qatow
*  1193000
*
* `
* */

package com.example.osproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main_Scheduler {
    public static void main(String[] args) {
        int[] iterations = {100, 1000, 10000, 100000};
        System.out.println("Ahmad Luay Al-qatow Project \n ID: 1193000");
        runSimulation("FCFS", iterations, 8, 5, 100, 20,20,false);
        runSimulation("SJF", iterations, 8, 5, 100, 20,20,false);
        runSimulation("RR", iterations, 8, 5, 100, 20,20,false);
        runSimulation("MFQ", iterations, 8, 5, 100, 20,20,false);
    }

    public static List<Process> generateProcesses(int count, int firstRange, int secondRange,int arrival,boolean _random) {
        List<Process> processes = new ArrayList<>();
        Random random = new Random();
        int arrivalTime = 0;
        for (int i = 0; i < count; i++) {

            int burstTime = random.nextInt(secondRange-firstRange+1) + firstRange; // Random burst time between 5 and 100
            processes.add(new Process(i, burstTime, arrivalTime));
            arrivalTime+= _random?random.nextInt(arrival) :  20;

        }
        return processes;
    }

    static ArrayList<double[]> runSimulation(String algorithm, int[] iterations, int Processes, int firstRange, int secondRange, int rrNum,int arrival,boolean random) {
        double[] resultawt = new double[4];
        double[] resultatt = new double[4];

        int j = 0;
        for (int iteration : iterations) {

            double avgAtt = 0;
            double avgAwt = 0;

            for (int i = 0; i < iteration; i++) {
                List<Process> processes = generateProcesses(Processes, firstRange, secondRange,arrival,random);

                switch (algorithm) {
                    case "FCFS":
                        FCFS fcfs = new FCFS();
                        fcfs.schedule(new ArrayList<>(processes));
                        avgAtt += AverageTT(processes);
                        avgAwt += AverageWT(processes);
                        break;
                    case "SJF":
                        SJF sjf = new SJF();
                        sjf.schedule(new ArrayList<>(processes));
                        avgAtt += AverageTT(processes);
                        avgAwt += AverageWT(processes);
                        break;
                    case "RR":
                        RoundRobin rr = new RoundRobin();
                        rr.schedule(new ArrayList<>(processes), rrNum);
                        avgAtt += AverageTT(processes);
                        avgAwt += AverageWT(processes);
                        break;
                    case "MFQ":
                        MultilevelFeedbackQueue mfq = new MultilevelFeedbackQueue();
                        mfq.schedule(new ArrayList<>(processes), 10, 50);
                        avgAtt += AverageTT(processes);
                        avgAwt += AverageWT(processes);
                        break;
                }
            }

            avgAtt /= iteration;
            avgAwt /= iteration;
            resultatt[j] = avgAtt;
            resultawt[j] = avgAwt;

            j++;

        }
        ArrayList<double[]> list = new ArrayList<double[]>();
        printArraysRowWise(algorithm, iterations, resultatt, resultawt);
        list.add(resultatt);
        list.add(resultawt);

        return list;
    }

    public static void printArraysRowWise(String alg, int[] array1, double[] array2, double[] array3) {

        // Assuming all arrays have the same length
        int length = array1.length;
        System.out.println("\n      ########### " + alg + " ###########");
        System.out.print("     ");

        // Iterate over each index
        for (int i = 0; i < length; i++) {
            System.out.printf("%-10d", array1[i]);
        }
        System.out.println();
        System.out.print("ATT  ");

        for (int i = 0; i < length; i++) {
            System.out.printf("%-10.2f", array2[i]);
        }
        System.out.println(); // Move to the next row
        System.out.print("AWT  ");

        for (int i = 0; i < length; i++) {
            System.out.printf("%-10.5f", array3[i]);
        }
        System.out.println();
    }
    public static double AverageTT(List<Process> processes) {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
        }
        return (double) totalTurnaroundTime / processes.size();
    }

    public static double AverageWT(List<Process> processes) {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.waitingTime ;
        }
        return (double) totalWaitingTime / processes.size();
    }
}
