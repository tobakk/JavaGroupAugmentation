package com.groupaugmentation.util;

import com.groupaugmentation.Settings;

import java.util.Random;


public class RandomNumberGenerator {

    private static RandomNumberGenerator instance;

    private Random randomNumberGenerator;

    private RandomNumberGenerator() {
        randomNumberGenerator = new Random(Settings.RNG_SEED);

    }

    public double getNextRealUniform() {
        return randomNumberGenerator.nextDouble();
    }


    public double getNextInitDriftUniform() {
        return randomNumberGenerator.nextDouble() * Settings.MAX_INIT_DRIFT;
    }

    //TODO ask supervisor if uniform or normal
//    public double getNextInitDriftNormal() {
//        return randomNumberGenerator.nextGaussian() * Settings.SD_INIT_DRIFT;
//    }


    public double getNextGaussianAlpha() {
        return randomNumberGenerator.nextGaussian() * Settings.STEP_ALPHA;
    }

    public double getNextGaussianBeta() {
        return randomNumberGenerator.nextGaussian() * Settings.STEP_BETA;
    }


    public double getNextGaussianDrift() {
        return randomNumberGenerator.nextGaussian() * Settings.STEP_DRIFT;
    }

    public int getNextPoisson() {
        double lambda = Settings.AVERAGE_FLOATER_SAMPLE;

        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= randomNumberGenerator.nextDouble();
        } while (p > L);

        return k - 1;
    }

    public void reset() {
        instance = new RandomNumberGenerator();

    }


    public static synchronized RandomNumberGenerator getInstance() {
        if (instance == null) {
            instance = new RandomNumberGenerator();
        }
        return instance;
    }

    public Random getRandomNumberGenerator() {
        return randomNumberGenerator;
    }
}
