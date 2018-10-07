package com.groupaugmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupAugmentationSimulation implements Runnable {

    private final Logger log = LoggerFactory.getLogger(GroupAugmentationSimulation.class);

    @Override
    public void run() {

        log.info("Hello from Logger");
    }
}
