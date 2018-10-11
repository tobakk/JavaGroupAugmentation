package com.groupaugmentation.model;

import com.groupaugmentation.Settings;
import com.groupaugmentation.util.IndividualList;
import com.groupaugmentation.util.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Stream;


public class Group {

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

    private void calculateFecundity() {
        final var rng = RandomNumberGenerator.getInstance();
        fecundity = Settings.K0 + this.accumulativeHelp;
        realFecundity = rng.getNextPoisson(fecundity);
    }


    public void reproduce() {
        this.calculateFecundity();
        for (int i = 0; i < realFecundity; i++) {
            Individual offspring = new Individual(this.breeder);
            this.helpers.add(offspring);
        }
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

    @Override
    public String toString() {
        return "Group{" +
                "accumulativeHelp=" + accumulativeHelp +
                ", fecundity=" + fecundity +
                ", realFecundity=" + realFecundity +
                ", breeder=" + breeder +
                ", helpers=" + helpers +
                '}';
    }
}
