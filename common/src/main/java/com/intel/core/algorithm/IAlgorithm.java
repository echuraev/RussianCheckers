package com.intel.core.algorithm;

import com.intel.core.rules.Move;
import com.intel.core.rules.Player;

public interface IAlgorithm {
    void getAlgorithm(Player player);
    AlgorithmType getAlgorithmType();
    Move getOpponentMove();
}
