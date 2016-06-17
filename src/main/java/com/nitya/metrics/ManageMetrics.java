package com.nitya.metrics;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

public class ManageMetrics {
/* class to instantiate + initialize + register + report all metrics -> only counters right now*/
    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter jreporter = JmxReporter.forRegistry(metrics).build();

    static void startReports() {
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
    static Counter getManagedCounter(String namespace, String metricName) {
        return getManagedCounter(namespace+"."+metricName);
    }/*overloaded function to include retrieval names that include namespaces*/

    
    //next step for following 2 functions > take type of metric also (use generic method to register)
    static Counter performRegistration(String namespace, String metricName) {
        return metrics.counter(name(namespace,metricName));
    }
    static Counter performRegistration(String metricName) {
        return metrics.counter(name(metricName));
    } /*overloaded function to register Metric objects without namespaces.
    first parameter in name() is optional, only provides further levels of organization*/
}


