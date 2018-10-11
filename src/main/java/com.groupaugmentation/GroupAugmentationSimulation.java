package com.groupaugmentation;

import com.groupaugmentation.model.FishType;
import com.groupaugmentation.model.Group;
import com.groupaugmentation.model.Individual;
import com.groupaugmentation.util.IndividualList;
import com.groupaugmentation.util.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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


        for (generation = 1; generation < Settings.NUMBER_OF_GENERATIONS; generation++) {
            deaths = 0;
            log.debug("Generation " + generation + " starting now!");
            Collections.shuffle(groupList, RandomNumberGenerator.getInstance().getRandomNumberGenerator());


            this.dispersal();
            this.help();
            this.survival();
            this.reassignFloaters();
            this.reproduction();
            log.debug("There are " + deaths + " dead fish in Generation  " + generation);
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
        this.survivalForFloaters();
        this.survivalForNonFloaters();
        log.trace("survival() end");
    }

    private void survivalForNonFloaters() {
        var rng = RandomNumberGenerator.getInstance();


        groupList.forEach(group -> {
            var toRemove = new IndividualList(FishType.HELPER);
            var groupSize = group.getHelpers().size() + 1;
            Stream.concat(group.getHelpers().stream(), Stream.of(group.getBreeder())).forEach(individual -> {
                var formulaSurvival = Settings.PREDATION /
                        (1 + Math.pow(Math.E,
                                -Settings.XSR * individual.getAge() + Settings.XSH * individual.getHelpLevel() - Settings.XSN * groupSize
                        ));
                if (rng.getNextRealUniform() > formulaSurvival) {
                    log.trace("Individual is dead now: " + individual);

                    if (individual.getFishType() == FishType.BREEDER) {
                        group.setBreeder(null);

                        List<Individual> sample = new IndividualList(FishType.FLOATER);


                        var sampleSize = RandomNumberGenerator.getInstance().getNextPoissonAverageFloater();
                        log.trace("generate new Breeder with sampleSize: " + sampleSize);
                        if (sampleSize >= floaters.size()) {
                            floaters.forEach(floater -> sample.add(floater));
                        } else {
                            while (sample.size() < sampleSize) {
                                var floater = floaters.get((int) (RandomNumberGenerator.getInstance().getNextRealUniform() * floaters.size()));

                                if (!sample.contains(floater)) {
                                    sample.add(floater);
                                }
                            }

                            log.trace("Generated new Sample: " + sample.toString());

                        }
                        group.setBreeder(this.findNewBreeder(sample, group));
                    } else if (individual.getFishType() == FishType.HELPER) {
                        toRemove.add(individual);
                    } else {
                        log.error("something went wrong in survival");
                    }

                    deaths++;
                } else {
                    individual.increaseAge();
                }
            });
            //remove dead helpers
            for (Individual individual : toRemove) {
                group.getHelpers().remove(individual);
            }
        });

    }

    private void survivalForFloaters() {
        var rng = RandomNumberGenerator.getInstance();

        var toRemove = new IndividualList(FishType.FLOATER);

        floaters.forEach(floater -> {
            var formulaSurvivalFloater = Settings.PREDATION / (1 + Math.pow(Math.E, -Settings.XSR * floater.getAge() - 1));

            if (rng.getNextRealUniform() > formulaSurvivalFloater) {
                log.trace("Floater is dead now: " + floater);
                toRemove.add(floater);
                deaths++;
            } else {
                floater.increaseAge();
            }

        });
        //remove dead floaters
        for (Individual individual : toRemove) {
            floaters.remove(individual);
        }
    }

    public Individual findNewBreeder(Collection<Individual> sample, Group group) {


        var helpers = group.getHelpers();
        final var rng = RandomNumberGenerator.getInstance();

        //this calculates the cumulative age
        int cumulativeAge = Stream.concat(sample.stream(), helpers.stream()).mapToInt(Individual::getAge).sum();
        log.trace("Cumulative age is: " + cumulativeAge);

        List<Individual> candidates = Stream.concat(sample.stream(), helpers.stream())
                .collect(Collectors.toList());

        Individual choosenOne = null;

        while (choosenOne == null) {
            Collections.shuffle(candidates, rng.getRandomNumberGenerator());

            for (Individual candidate : candidates) {
                double probability = (double) candidate.getAge() / (double) cumulativeAge;

                double draw = rng.getNextRealUniform();

                if (draw < probability) {
                    log.trace("Found a new Breeder candidate!");

                    if (candidate.getFishType() == FishType.HELPER) {
                        helpers.remove(candidate);
                    } else if (candidate.getFishType() == FishType.FLOATER) {
                        //remove floater
                        this.floaters.remove(candidate);
                    }
                    choosenOne = candidate;

                    choosenOne.setFishType(FishType.BREEDER);
                    return choosenOne;

                }


            }


        }
        log.error("this should never be reached");
        return null;
    }

    public void reassignFloaters() {

        var rng = RandomNumberGenerator.getInstance();

        var toRemove = new ArrayList<Individual>();

        floaters.forEach(floater -> {
            toRemove.add(floater);
            floater.setFishType(FishType.HELPER);

            double index = rng.getNextRealUniform() * (double) Settings.MAX_COLONIES;

            var randomGroup = groupList.get((int) index);

            randomGroup.getHelpers().add(floater);
        });

        toRemove.forEach(i -> floaters.remove(i));
    }

    public void reproduction() {
        groupList.forEach(group -> {
            group.reproduce();
        });

    }


}
