package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GroupAugmentationSimulation implements Runnable {

    public GroupAugmentationSimulation() {

        groupList = new ArrayList<>();
    }

    private final Logger log = LoggerFactory.getLogger(GroupAugmentationSimulation.class);


    private final static int NUMBER_OF_GENERATIONS = 100;

    private int generation = 0;

    private List<Group> groupList;

    private IndividualList floaters;

    @Override
    public void run() {
        log.info("Starting simulation..");


        //TODO implement initial stuff

        for (generation = 1; generation < NUMBER_OF_GENERATIONS; generation++) {
            for (Group group : groupList) {
                group.run();
            }

        }


    }
}
