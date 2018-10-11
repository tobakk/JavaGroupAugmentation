package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import com.groupaugmentation.util.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GroupAugmentationSimulation implements Runnable {

    public GroupAugmentationSimulation() {

        groupList = new ArrayList<>();
        floaters = new IndividualList(FishType.FLOATER);

    }

    private final Logger log = LoggerFactory.getLogger(GroupAugmentationSimulation.class);


    private int generation = 0;

    private List<Group> groupList;

    private IndividualList floaters;

    private int deaths = 0;


    @Override
    public void run() {
        log.info("Starting simulation..");
        this.initGroup();

        //TODO implement initial stuff

        for (generation = 1; generation < Settings.NUMBER_OF_GENERATIONS; generation++) {
            log.debug("Generation " + generation + " starting now!");


            this.dispersal();
            this.survival();
            this.help();
            for (Group group : groupList) {

                group.run();
            }


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

    public void help() {
        this.groupList.forEach(group -> group.calculateCumulativeHelp());
    }

    public void survival() {
        log.trace("survival() start");

        var rng = RandomNumberGenerator.getInstance();

        floaters.forEach(floater -> {
            var formulaSurvivalFloater = Settings.PREDATION / (1 + Math.pow(Math.E, -Settings.XSR * floater.getAge() - 1));

            if (rng.getNextRealUniform() > formulaSurvivalFloater) {
                log.trace("Floater is dead now: " + floater);
                floaters.remove(floater);
                deaths++;
            } else {
                floater.increaseAge();
            }

        });

        groupList.forEach(group -> {
            var groupSize = group.getHelpers().size() + 1;
            Stream.concat(group.getHelpers().stream(), Stream.of(group.getBreeder())).forEach(individual -> {
                var formulaSurvival = Settings.PREDATION /
                        (1 + Math.pow(Math.E,
                                -Settings.XSR * individual.getAge() - Settings.XSH * individual.getHelpLevel() - Settings.XSN * groupSize
                        ));
                if (rng.getNextRealUniform() > formulaSurvival) {
                    log.trace("Individual is dead now: " + individual);

                    if (individual.getFishType() == FishType.BREEDER) {
                        group.setBreeder(null);
                        group.setBreederAlive(false);
                    } else if (individual.getFishType() == FishType.HELPER) {
                        group.getHelpers().remove(individual);
                    } else {
                        log.error("something went wrong in survival");
                    }

                    deaths++;
                } else {
                    individual.increaseAge();
                }

            });


        });


        log.trace("survival() end");
    }
}
