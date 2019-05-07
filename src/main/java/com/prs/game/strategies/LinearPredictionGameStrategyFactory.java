/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import org.springframework.stereotype.Component;

@Component
public class LinearPredictionGameStrategyFactory implements GameStrategyFactory {

    @Override
    public GameStrategy getStrategy() {
        return new LinearPredictionGameStrategy();
    }

}
