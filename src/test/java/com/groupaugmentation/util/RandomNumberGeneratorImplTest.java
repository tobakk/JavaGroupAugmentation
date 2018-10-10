package com.groupaugmentation.util;

import com.groupaugmentation.Settings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomNumberGeneratorImplTest {
    private RandomNumberGenerator generator = RandomNumberGenerator.getInstance();

    //reset the RandomNumberGenerator before every test
    @Before
    public void setupForTest() {
        generator.reset();
        generator = RandomNumberGenerator.getInstance();
    }

    /*
    Test if same seed return same values
     */
    @Test
    public void seedTest() {


        generateNumbers(generator);
        var expectedUniform = generator.getNextRealUniform();
        var expectedGaussianAlpha = generator.getNextGaussianAlpha();
        var expectedGaussianBeta = generator.getNextGaussianBeta();
        var expectedGaussianDrift = generator.getNextGaussianDrift();
        var expectedPoisson = generator.getNextPoisson();

        generator.reset();
        generator = RandomNumberGenerator.getInstance();
        generateNumbers(generator);


        assertEquals(expectedUniform, generator.getNextRealUniform(), 0);
        assertEquals(expectedGaussianAlpha, generator.getNextGaussianAlpha(), 0);
        assertEquals(expectedGaussianBeta, generator.getNextGaussianBeta(), 0);
        assertEquals(expectedGaussianDrift, generator.getNextGaussianDrift(), 0);
        assertEquals(expectedPoisson, generator.getNextPoisson(), 0);

    }

    /*
    test if method generates numbers only within the range
     */
    @Test
    public void driftUpperLowerBoundTest() {
        for (int i = 0; i < 1000; i++) {
            var result = generator.getNextInitDriftUniform();
            assertTrue(result >= 0 && result <= Settings.MAX_INIT_DRIFT);
        }

    }

    private void generateNumbers(RandomNumberGenerator generator) {
        for (int i = 0; i < 200; i++) {
            generator.getNextRealUniform();
            generator.getNextGaussianAlpha();
            generator.getNextGaussianBeta();
            generator.getNextGaussianDrift();
            generator.getNextPoisson();

        }
    }


    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.getNextGaussianAlpha());
        }
    }
}
