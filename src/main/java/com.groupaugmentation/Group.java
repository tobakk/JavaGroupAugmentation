package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import com.groupaugmentation.util.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);


    private double accumulativeHelp = -1;
    private double fecundity = -1;

    private int realFecundity = -1;


    private Individual breeder;

    private IndividualList helpers;


    public Group() {
        log.trace("Group initializing");
        log.trace("Generate a new Breeder");
        this.breeder = new Individual(Settings.INIT_ALPHA, Settings.INIT_BETA, FishType.BREEDER);
        log.trace("add Helpers");
        helpers = new IndividualList(FishType.HELPER);

        for (int i = 0; i < Settings.INIT_NUMBER_OF_HELPERS; i++) {
            helpers.add(new Individual(Settings.INIT_ALPHA, Settings.INIT_BETA));
        }
    }

    @Override
    public void run() {

        breeder.run();
        helpers.forEach(individual -> individual.run());


        //TODO implement realfecundity
        //TODO implement offspring function
        for (int i = 0; i < 2; i++) {
            Individual offspring = new Individual(breeder);
            helpers.add(offspring);
        }
    }


    public void calculateCumulativeHelp() {
        log.trace("calculating cumulative help");
        //this line below does this baically ;)
        //for(Individual individual: helpers){
        //this.accumulativeHelp = this.accumulativeHelp.add(individual.getHelpLevel());
        //}
        //

        //calculate cumulative help
        helpers.forEach(individual -> this.accumulativeHelp += individual.getHelpLevel());
        this.accumulativeHelp += breeder.getHelpLevel();
        log.trace("Cumulative Help is: " + this.accumulativeHelp);
    }

    public IndividualList getHelpers() {
        return helpers;
    }

    public Individual getBreeder() {
        return breeder;
    }


    //TODO maybe delete?
    public void setBreeder(Individual breeder) {
        this.breeder = breeder;
    }


    public void setNewBreeder(Collection<Individual> sample) {
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
                double probability = candidate.getAge() / cumulativeAge;

                double draw = rng.getNextRealUniform();

                if (draw < probability) {
                    log.trace("Found a new Breeder candidate!");

                    if (candidate.getFishType() == FishType.HELPER) {
                        this.helpers.remove(candidate);
                    } else if (candidate.getFishType() == FishType.FLOATER) {
                        //remove floater
                    }


                }


            }


        }

    }
}
