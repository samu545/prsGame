/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import com.prs.game.GameMoveEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomGameStrategyTest {

    private GameStrategy strategy;

    @Before
    public void init() {
        strategy = (new RandGameStrategyFactory()).getStrategy();
    }

    @Test
    public void testOnlyPaperPrediction() {

    }

    @Test
    public void testGetPlayerPrediction() {
        strategy.logPlayerTurn(GameMoveEnum.PAPER, GameMoveEnum.PAPER);
        strategy.logPlayerTurn(GameMoveEnum.PAPER, GameMoveEnum.PAPER);
        strategy.logPlayerTurn(GameMoveEnum.PAPER, GameMoveEnum.PAPER);

        GameMoveEnum predict = strategy.getPlayerPrediction();
        // since Random difficult to predict the player prediction.
        //assertEquals(GameMoveEnum.ROCK, predict);
    }
}
