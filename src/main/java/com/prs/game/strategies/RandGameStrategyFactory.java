/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import org.springframework.stereotype.Component;

@Component
public class RandGameStrategyFactory implements GameStrategyFactory {

    @Override
    public GameStrategy getStrategy() {
        return new RandGameStrategy();
    }

}
