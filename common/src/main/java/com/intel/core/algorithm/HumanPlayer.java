package com.intel.core.algorithm;

import com.intel.core.rules.Move;
import com.intel.core.rules.Player;

public class HumanPlayer implements IAlgorithm {
    public HumanPlayer() {}

    @Override
    public void getAlgorithm(Player player) {}

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.HUMAN;
    }

    @Override
    public Move getOpponentMove() {
        return null;
    }
}
