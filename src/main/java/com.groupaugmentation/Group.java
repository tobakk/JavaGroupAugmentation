package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);

    private boolean isBreederAlive;

    private BigDecimal accumulativeHelp = null;
    private BigDecimal fecundity = null;

    private BigDecimal realFecundity = null;


    private Individual breeder;

    private IndividualList helpers;


    public Group() {
        this.breeder = new Individual(Settings.INIT_ALPHA, Settings.INIT_BETA, FishType.BREEDER);
        this.isBreederAlive = true;

        helpers = new IndividualList(FishType.HELPER);

        for (int i = 0; i < Settings.INIT_NUMBER_OF_HELPERS; i++) {
            helpers.add(new Individual(Settings.INIT_ALPHA, Settings.INIT_BETA));
        }


    }

    @Override
    public void run() {


    }


}
