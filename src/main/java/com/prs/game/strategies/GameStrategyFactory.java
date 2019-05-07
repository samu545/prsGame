/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

/**
 * Interface used to gather all implemented strategies together
 */
public interface GameStrategyFactory {
    GameStrategy getStrategy();
}
