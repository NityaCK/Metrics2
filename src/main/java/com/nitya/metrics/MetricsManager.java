package com.nitya.metrics;

import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

public class MetricsManager {

    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter jreporter = JmxReporter.forRegistry(metrics).build();
    String namespace;

    void startReports() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
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


