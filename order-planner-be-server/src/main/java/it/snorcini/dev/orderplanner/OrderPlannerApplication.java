package it.snorcini.dev.orderplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main executable class.
 */
@SpringBootApplication
public class OrderPlannerApplication {

    /**
     * Application starter.
     *
     * @param args application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(OrderPlannerApplication.class, args);
    }
}
