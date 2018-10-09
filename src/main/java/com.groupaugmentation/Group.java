package com.groupaugmentation;

import com.groupaugmentation.util.IndividualList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);

    private boolean isBreederAlive;

    private BigDecimal accumulativeHelp;
    private BigDecimal fecundity;

    private BigDecimal realFecundity;


    private Individual breeder;

    private IndividualList helpers;


    @Override
    public void run() {


    }


}
