package com.groupaugmentation.model;

import com.groupaugmentation.Settings;
import com.groupaugmentation.util.IndividualList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;


public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);


    private double accumulativeHelp = 0;
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
        this.accumulativeHelp = Stream.concat(helpers.stream(), Stream.of(breeder)).mapToDouble(Individual::getHelpLevel).sum();
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


}
