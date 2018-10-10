package com.groupaugmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        long timestamp = System.currentTimeMillis();
        GroupAugmentationSimulation simulation = new GroupAugmentationSimulation();
        simulation.run();

        var executionTime = System.currentTimeMillis() - timestamp;
        log.info("Execution took: " + executionTime + " milliseconds");

    }
}
