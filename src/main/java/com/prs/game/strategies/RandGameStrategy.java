/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import com.prs.game.GameMoveEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Simple Random Game throw generator
 */
public class RandGameStrategy extends AbstractGameStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Random rnd;

    public RandGameStrategy() {
        rnd = new Random();
        logger.info("Random Game Strategy initialized");
    }

    @Override
    public GameMoveEnum getPlayerPrediction() {
        predicted = GameMoveEnum.fromValue(rnd.nextInt(20) / 10);
        return predicted;
    }

    @Override
    public String getStrategyName() {
        return "Random Strategy";
    }
}
