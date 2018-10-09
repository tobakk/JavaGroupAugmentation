package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import com.groupaugmentation.util.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GroupAugmentationSimulation implements Runnable {

    public GroupAugmentationSimulation() {

        groupList = new ArrayList<>();
        floaters = new IndividualList(FishType.FLOATER);

    }

    private final Logger log = LoggerFactory.getLogger(GroupAugmentationSimulation.class);


    private int generation = 0;

    private List<Group> groupList;

    private IndividualList floaters;


    @Override
    public void run() {
        log.info("Starting simulation..");
        this.initGroup();

        //TODO implement initial stuff

        for (generation = 1; generation < Settings.NUMBER_OF_GENERATIONS; generation++) {
            for (Group group : groupList) {
                group.run();
            }
            this.dispersal();

        }


    }

    private void initGroup() {

        for (int i = 0; i < Settings.MAX_COLONIES; i++) {
            groupList.add(new Group());
        }

    }


    private void dispersal() {
        log.trace("dispersal() start");
        groupList.forEach(group -> log.trace("Helper Size: " + group.getHelpers().size()));
        log.trace("Floater Size: " + floaters.size());
        List<Individual> toRemove = new ArrayList<>();

        groupList.forEach(group -> {
            group.getHelpers().forEach(individual -> {

                        var rnd = RandomNumberGenerator.getInstance().getNextRealUniform();

                        var dispersalFormula = 1 / (1 + Math.pow(Math.E, individual.getDispersal() * -1));

                        if (rnd < dispersalFormula) {
                            individual.setFishType(FishType.FLOATER);
                            this.floaters.add(individual);
                            toRemove.add(individual);

                            log.trace("Individual is a Floater now: " + individual);
                        }

                        individual.getDispersal();
                    }
            );
            toRemove.forEach(individual -> group.getHelpers().remove(individual));
        });

        groupList.forEach(group -> log.trace("Helper Size: " + group.getHelpers().size()));
        log.trace("Floater Size: " + floaters.size());
        log.trace("dispersal() end");
    }
}
