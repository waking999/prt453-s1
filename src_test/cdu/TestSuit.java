package cdu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cdu.algorithm.AlgorithmGreedyTest;
import cdu.algorithm.AlgorithmHillClimbingTest;
import cdu.algorithm.AlgorithmStochasticLocalSearchGreedyTest;
import cdu.algorithm.AlgorithmStochasticLocalSearchOwnTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlgorithmGreedyTest.class,
        AlgorithmHillClimbingTest.class,
        AlgorithmStochasticLocalSearchOwnTest.class,
        AlgorithmStochasticLocalSearchGreedyTest.class
})
public class TestSuit {

}

