package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);

    private boolean isBreederAlive;

    private double accumulativeHelp = -1;
    private double fecundity = -1;

    private int realFecundity = -1;


    private Individual breeder;

    private IndividualList helpers;


    public Group() {
        log.trace("Group initializing");
        log.trace("Generate a new Breeder");
        this.breeder = new Individual(Settings.INIT_ALPHA, Settings.INIT_BETA, FishType.BREEDER);
        this.isBreederAlive = true;
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

//

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
}
