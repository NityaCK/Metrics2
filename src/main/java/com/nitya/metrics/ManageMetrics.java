package com.nitya.metrics;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

public class ManageMetrics
/* class to instantiate + initialize + register + report all metrics -> only counters right now*/
{
    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter jreporter = JmxReporter.forRegistry(metrics).build();

    private static final Counter words = metrics.counter(name("com.nitya.metrics", "words"));
    private static final Counter chars = metrics.counter(name("chars")); //first parameter is optional, only provides further levels of organization


    static void startReports()
    {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        jreporter.start();
    }
    static Counter getManagedCounter(String metricName) {
        SortedMap<String, Counter> tempMap = metrics.getCounters();
        return tempMap.get(metricName);
    }
    static void performRegistration(String namespace, String metricName) { //next step > take type of metric also
        metrics.counter(name(namespace,metricName));
    }


}


