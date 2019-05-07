/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import com.prs.game.GameMoveEnum;


public interface GameStrategy {

    /**
     * Strategy Name
     *
     * @return constant strategy name
     */
    String getStrategyName();

    /**
     * Strategy predict error
     * Calculates average error for last N (4) turn
     * There are 3 different errors: 0.0 if computer win,
     * 0.5 if was Draw on turn and 1.0 if loose.
     *
     * @return average predict error
     */
    double getAveragError();

    /**
     * Return Player throw prediction
     *
     * @return player throw prediction
     */
    GameMoveEnum getPlayerPrediction();

    /**
     * Log game turn in history, GameService inform strategy of player throw
     * and computer throw chosen from one of strategy. Strategy logs own prediction too
     * inside this method.
     *
     * @param playerThrow
     * @param computerThrow
     */
    void logPlayerTurn(GameMoveEnum playerThrow, GameMoveEnum computerThrow);
}