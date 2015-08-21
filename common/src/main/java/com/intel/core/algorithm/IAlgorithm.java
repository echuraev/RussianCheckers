package com.intel.core.algorithm;

import com.intel.core.rules.Move;
import com.intel.core.rules.Player;

public interface IAlgorithm {
    public void getAlgorithm(Player player);
    public AlgorithmType getAlgorithmType();
    public Move getOpponentMove();
}
