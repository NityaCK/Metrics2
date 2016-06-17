package com.nitya.metrics;

import java.io.*;

public class App {
    /* the app only modifies the value of the metric ; all other metric-related function happens via an object of ManageMetrics*/
    static final String NAMESPACE = "com.nitya.metrics";

    static int wordCount(String s) { //function to count number of words in a string
        char ch[] = new char[s.length()];
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            ch[i] = s.charAt(i);
            if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                c++;
        }
        return c;
    }
    static int charCount(String S) { //function to count number of characters in a string
        return S.length();
    }

    static void waitSeconds(int n) { //function to slow down the change in the values of the counters so that updation is obvious & trackable
        try {
            Thread.currentThread().sleep(n*1000);
        }
        catch (InterruptedException ie) {
            System.out.println("Interrupted exception");
        }
    }

    public static void main( String[] args ) {
        String line;
        //String filename=args[0];
        String filename="test2.txt";

        ManageMetrics.performRegistration(NAMESPACE, "words");
        ManageMetrics.performRegistration("chars");
        ManageMetrics.startReports();
        //System.out.println("TEST PRINT > " + App.class); > gives the string 'com.nitya.metrics.App'

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            do {
                waitSeconds(5);
                line = reader.readLine();
                if (line == null) break;
                int x = wordCount(line);
                ManageMetrics.getManagedCounter(NAMESPACE, "words").inc(x);
                int y = charCount(line);
                ManageMetrics.getManagedCounter("chars").inc(y);
            } while (line != null);
        } catch (FileNotFoundException f) {
            System.out.println("sorry file not found");
        } catch (IOException io) {
            System.out.println("io exception");
        }
    }
}



/*public class App {    //USING GAUGE (single value)

    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();

    private static Integer wordCount(String s)
    {
        char ch[] = new char[s.length()];
        int c = 0;
        for (int i = 0; i < s.length(); i++)
        {
            ch[i] = s.charAt(i);
            if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                c++;
        }
        return c;
    }

    static void startReport()
    {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void waitSeconds(int n)
    {
        try
        {
            Thread.currentThread().sleep(n*1000);
        } catch (InterruptedException e) { }
    }

    public static int countTheWords()
    {
        //String filename=args[0];
        String filename = "test2.txt";
        int res = 0;
        String line = null;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            do {
                line = reader.readLine();
                if (line == null) break;
                int x = wordCount(line);
                res = res + x;
            } while (line != null);
        } catch (FileNotFoundException f) {
            System.out.println("sorry file not found");
        } catch (IOException io) {
            System.out.println("io exception");
        }
        return res;
    }

    public static void main(String[] args) {
        startReport();
        reporter.start();
        System.out.println("Started log");

        metrics.register(name(App.class, "com.nitya.metrics"), new Gauge<Integer>() {
            public Integer getValue() {
                int x=countTheWords();
                return x;
            }
        });

        waitSeconds(5);
    }
}*/

/*public class App {    //USING GAUGE (multiple values)

    private static int res;

    public static void main(String[] args) throws InterruptedException
    {

        MetricRegistry metrics = new MetricRegistry();

        metrics.register(name(App.class, "wordcount"), new Gauge<Integer>() {
            public Integer getValue()
            {
                int x=res;
                return x;
            }
        });

        startReport(metrics);
        System.out.println("Started log");

        ownMethod();

    }

    private static Integer lineWordCount(String s)
    {
        char ch[] = new char[s.length()];
        int c = 0;
        for (int i = 0; i < s.length(); i++)
        {
            ch[i] = s.charAt(i);
            if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                c++;
        }
        return c;
    }

    private static void ownMethod() throws InterruptedException
    {
        String filename = "test2.txt";
        res = 0;
        String line = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            do {
                line = reader.readLine();
                if (line == null) break;
                int x = lineWordCount(line);
                res = res + x;
                waitSeconds(5);
            } while (line != null);
        } catch (FileNotFoundException f) {
            System.out.println("sorry file not found");
        } catch (IOException io) {
            System.out.println("io exception");
        }

    }

    static void startReport(MetricRegistry metrics)
    {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        JmxReporter jreporter = JmxReporter.forRegistry(metrics).build();
        jreporter.start();
    }

    static void waitSeconds(int n)
    {
        try
        {
            Thread.currentThread().sleep(n*1000);
        } catch (InterruptedException e) { }
    }

}*/