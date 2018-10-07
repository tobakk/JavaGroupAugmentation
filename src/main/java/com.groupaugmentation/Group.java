package com.groupaugmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class Group implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Group.class);

    private boolean isBreederAlive;

    private BigDecimal accumulativeHelp;
    private BigDecimal fecundity;

    private BigDecimal realFecundity;


    private Breeder breeder;

    private List<Helper> helpers;


    List<Individual> individuals;

    @Override
    public void run() {


    }


}
