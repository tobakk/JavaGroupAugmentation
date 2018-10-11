package com.groupaugmentation.util;

import com.groupaugmentation.model.FishType;
import com.groupaugmentation.model.Individual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class IndividualList extends ArrayList<Individual> {

    private Logger log = LoggerFactory.getLogger(IndividualList.class);

    private final FishType fishType;

    public IndividualList(FishType fishType) {
        this.fishType = fishType;
    }

    @Override
    public boolean add(Individual individual) {

        if (individual.getFishType() != this.fishType) {
            log.error("You cannot add a non " + this.fishType + " Individual to the IndividualList for " + this.fishType);
            return false;
        } else {
            return super.add(individual);
        }
    }
}
