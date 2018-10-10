package com.groupaugmentation;


public interface Settings {

    int MAX_COLONIES = 2;
    int NUMBER_OF_GENERATIONS = 100;


    //Random number generation
    int RNG_SEED = 1;
    int UNIFORM_REAL_UPPER_BOUND = 1;
    int UNIFORM_REAL_LOWER_BOUND = 0;

    double SD_INIT_DRIFT = 100;
    double MAX_INIT_DRIFT = 100;

    double AVERAGE_FLOATER_SAMPLE = 50; ///average number of floaters sampled from the total ///Check first if there are enough floaters, take a proportion instead??



    //    //Fix values
// double m         = 0.8;       // predation pressure
////const double Pd        = 0.5;       // propensity to disperse
//
//// Modifiers (I might need to make them evolve too)
////const double X0r    = 1; // inflexion point in the level of help formula for the influence of age
////const double X0n    = 1; // inflexion point in the level of help formula for the influence of group size
//const double K0     = 1; // min fecundity, fecundity when no help provided.
//const double Xsh    = 1; // cost of help in survival
//const double Xsr    = 1; // benefit of age in survival
//const double Xss    = 1; // benefit of group size in survival
//
//
//
//Genetic values
    double INIT_ALPHA = 0;    // starting value of alpha (in gen 0)
    double MUTATION_ALPHA = 0.1;    // mutation rate in alpha for level of help
    double STEP_ALPHA = 0.1;     // mutation step size in alpha for level of help
    double INIT_BETA = 0;     // starting value of beta (in gen 0)
    double MUTATION_BETA = 0.1;    // mutation rate in beta for the propensity to disperse
    double STEP_BETA = 0.1;     // mutation step size in beta for the propensity to disperse
    double MUTATION_DRIFT = 0.1;    // mutation rate in the neutral genetic value to track level of relatedness
    double STEP_DRIFT = 0.1;     // mutation step size in the neutral genetic value to track level of relatedness

    //const int minsurv     = 50;     // min number of individual that survive
//
//
//
//const double
//    enum classes {breeder, helper, floater};
//
//const int maxcolon     = 50;     // max number of groups or colonies --> breeding spots. Whole population size = maxcolon * (numhelp + 1)
    int INIT_NUMBER_OF_HELPERS = 2;       //initial number of helpers per group when initializing group
//
//const int NumGen       = 1000;   // number of generations
//const int numrep       = 20;     // number of replicates
//const int skip         = 50;     // interval between print-outs


}
