package com.groupaugmentation.util;

import com.groupaugmentation.Settings;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import java.math.BigDecimal;
import java.util.Random;


public class RandomNumberGenerator {


    private static RandomNumberGenerator instance;

    private RandomGenerator rnd;

    private GaussianRandomGenerator gaussianRandomGenerator;

    private UniformRealDistribution uniformRealDistribution;

    private UniformIntegerDistribution uniformIntegerDistribution;

    private UniformRealDistribution uniformDoubleDistribution;


    private RandomNumberGenerator() {


        Random random = new Random(3);


        //initialize Uniform Random Number Generator
        uniformRealDistribution = new UniformRealDistribution(0, 1);
        uniformRealDistribution.reseedRandomGenerator(Settings.RNG_SEED);

        //initialize Uniform Random Number Generator
        uniformIntegerDistribution = new UniformIntegerDistribution(Settings.UNIFORM_REAL_LOWER_BOUND, Settings.UNIFORM_REAL_UPPER_BOUND);
        uniformIntegerDistribution.reseedRandomGenerator(Settings.RNG_SEED + 1);


        //initialize Uniform Random Number Generator
        uniformDoubleDistribution = new UniformRealDistribution(Settings.UNIFORM_REAL_LOWER_BOUND, Settings.UNIFORM_REAL_UPPER_BOUND);
        uniformDoubleDistribution.reseedRandomGenerator(Settings.RNG_SEED + 2);


    }

    public double getNextRealUniform() {
        return uniformRealDistribution.sample();
    }


    public BigDecimal getNextInitDriftNormal() {
        //TODO implement
        return BigDecimal.ONE;
    }


    public BigDecimal getNextGaussianAlpha() {
        //TODO implement
        return BigDecimal.ONE;
    }

    public BigDecimal getNextGaussianBeta() {
        //TODO implement
        return BigDecimal.ONE;
    }


    public BigDecimal getNextGaussianDrift() {
        //TODO implement
        return BigDecimal.ONE;
    }

    public static synchronized RandomNumberGenerator getInstance() {
        if (instance == null) {
            instance = new RandomNumberGenerator();
        }
        return instance;
    }


}
