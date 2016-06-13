package com.nitya.metrics;

import java.io.*;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

/*public class App   //USING COUNTER
{
    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
    private static final Counter words = metrics.counter(name(App.class, "words"));
    static int wordCount(String s)
    {
        char ch[]= new char[s.length()];
        int c=0;
        for(int i=0;i<s.length();i++)
        {
            ch[i]= s.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
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
    static void wait1Second()
    {
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {}
    }

    public static void main( String[] args ) {
        String line = null;
        int res = 0;

        startReport();
        reporter.start();

        //String filename=args[0];
        String filename="test2.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            do {
                    wait1Second();
                line = reader.readLine();
                if (line == null) break;
                int x = wordCount(line);
                res = res + x;
                words.inc(x);
                for(int i=0;i<15;i++)
                    wait1Second();
            } while (line != null);
        } catch (FileNotFoundException f) {
            System.out.println("sorry file not found");
        } catch (IOException io) {
            System.out.println("io exception");
        }

        //System.out.println(res);
        for(int i=0;i<6;i++)
            wait1Second();
    }
}*/



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

    static void wait1Second()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
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

        for(int i=0;i<30;i++) wait1Second();
        wait1Second();
    }
}*/

public class App {    //USING GAUGE (multiple values)

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

}