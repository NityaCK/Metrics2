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

    public static void main( String[] args ) {
        String line;
        //String filename=args[0];

        String fileLoc="/home/nitya/text files/test2.txt";
        Class c0 = new App().getClass();
        MetricsManager metricsManager = new MetricsManager();
        metricsManager.setNamespace(c0.getCanonicalName());

        metricsManager.performRegistration("words");
        metricsManager.performRegistration("chars");
        metricsManager.startReports();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileLoc));
            do {
                //waitSeconds(5);
                waitSeconds(2);
                line = reader.readLine();
                if (line == null) break;
                int x = wordCount(line);
                metricsManager.getManagedCounter("words").inc(x);
                int y = charCount(line);
                metricsManager.getManagedCounter("chars").inc(y);
            } while (line != null);
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
