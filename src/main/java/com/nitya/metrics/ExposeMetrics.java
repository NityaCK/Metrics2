package com.nitya.metrics;

import java.util.concurrent.TimeUnit;
import com.codahale.metrics.*;
import static com.codahale.metrics.MetricRegistry.name;

public class ExposeMetrics {
    static final MetricRegistry metrics = new MetricRegistry();
    static final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
    private static final Counter words = metrics.counter(name("com.nitya.metrics2", "words"));
    private static final Counter chars = metrics.counter(name("com.nitya.metrics2", "chars"));

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait1Second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

}