package com.nitya.metrics;

import java.io.*;

public class App {

    static int wordCount(String s) { return s.split(" ").length; }
    static int charCount(String S) { //function to count number of characters in a string
        return S.length();
    }
    static void waitSeconds(int n) {
        try {
            Thread.currentThread().sleep(n*1000);
        }
        catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
    static void printUsage() {
        System.out.println("Usage: java com.nitya.metrics.App <inputfilepath> <reporting choice as jmx/console/both>");
    }

    public static void main( String[] args ) {

        if (args.length != 2) {
            printUsage();
            System.exit(-1);
        }
        String fileLoc=args[0];
        String reportingChoice = args[1];
        String line;
        Class c0 = new App().getClass();
        MetricsManager metricsManager = new MetricsManager();
        metricsManager.setNamespace(c0.getCanonicalName());

        metricsManager.performRegistration("words");
        metricsManager.performRegistration("chars");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileLoc));
            metricsManager.startReports(reportingChoice);
            do {
                waitSeconds(5);
                line = reader.readLine();
                if (line == null) break;
                int x = wordCount(line);
                metricsManager.getManagedCounter("words").inc(x);
                int y = charCount(line);
                metricsManager.getManagedCounter("chars").inc(y);
            } while (line != null);
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (IllegalArgumentException ia) {
            printUsage();
            System.out.println(ia.getMessage());
        }
    }
}
