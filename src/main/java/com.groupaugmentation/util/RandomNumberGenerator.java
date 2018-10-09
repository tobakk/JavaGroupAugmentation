package com.groupaugmentation.util;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.apache.commons.math3.random.UniformRandomGenerator;

import java.util.Random;

public class RandomNumberGenerator {


    private static RandomNumberGenerator instance;


    private RandomNumberGenerator() {
        PoissonDistribution poissonDistribution;

        UniformRandomGenerator uniform;


    }


    public static synchronized RandomNumberGenerator getInstance() {
        if (instance == null) {
            instance = new RandomNumberGenerator();
        }
        return instance;
    }


}
