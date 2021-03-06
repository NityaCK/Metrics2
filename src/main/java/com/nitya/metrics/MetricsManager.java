package com.nitya.metrics;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

public class MetricsManager {

    static final MetricRegistry metrics = new MetricRegistry();
    String namespace;

    void startReports(String choice) {
        boolean start=false;
        if (choice.equals("console") || choice.equals("both")){
            startConsoleReporter();
            start=true;
        }
        if (choice.equals("jmx") || choice.equals ("both")) {
            startJmxReporter();
            start=true;
        }
        if (!start) {
            throw new IllegalArgumentException("Invalid 2nd argument");
        }
    }

    void startConsoleReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(2, TimeUnit.SECONDS);
    }

    void startJmxReporter() {
        JmxReporter jreporter = JmxReporter.forRegistry(metrics).build();
        jreporter.start();
    }

    void setNamespace(String namespace) {
        this.namespace=namespace;
    }

    Counter getManagedCounter(String metricName) {
        SortedMap<String, Counter> tempMap = metrics.getCounters();
        return tempMap.get(namespace+"."+metricName);
    }

    Counter performRegistration(String metricName) {
        return metrics.counter(name(namespace, metricName));
    }
}


