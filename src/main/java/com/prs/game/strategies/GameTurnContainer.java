/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import com.prs.game.GameMoveEnum;

/**
 * Container to store game turn result,
 * There are player throw, computer throw,
 * and predicted throw by particular strategy.
 * strategyPredicted - used to calculate prediction error of
 * particular strategy, computer throw - result which possible chosen
 * by other strategy.
 * <p>
 * error - error of this turn.
 */

public class GameTurnContainer {

    public final double WIN = 0.0;
    public final double DRAW = 0.5;
    public final double LOOSE = 1.0;


    private final GameMoveEnum player;
    private final GameMoveEnum computer;
    private final GameMoveEnum strategyPredicted;
    private final double error;

    public GameTurnContainer(GameMoveEnum player, GameMoveEnum computer, GameMoveEnum strategyPredicted) {
        this.player = player;
        this.computer = computer;
        this.strategyPredicted = strategyPredicted;
        double error = LOOSE;
        /*
         * Error calculation between player throw
         * and computer prediction,
         * If computer predict players throw - WIN
         *   for example: player PAPER and computer predict PAPER, than
         *   computer choose SCISSORS and win.
         */
        switch (player) {
            case PAPER:
                switch (strategyPredicted) {
                    case PAPER:
                        error = WIN;
                        break;
                    case ROCK:
                        error = DRAW;
                        break;
                    case SCISSORS:
                        error = LOOSE;
                        break;
                }
                break;
            case ROCK:
                switch (strategyPredicted) {
                    case PAPER:
                        error = LOOSE;
                        break;
                    case ROCK:
                        error = WIN;
                        break;
                    case SCISSORS:
                        error = DRAW;
                        break;
                }
                break;
            case SCISSORS:
                switch (strategyPredicted) {
                    case PAPER:
                        error = DRAW;
                        break;
                    case ROCK:
                        error = LOOSE;
                        break;
                    case SCISSORS:
                        error = WIN;
                        break;
                }
                break;
        }
        this.error = error;
    }

    public GameMoveEnum getPlayer() {
        return player;
    }

    public double getError() {
        return error;
    }

    @Override
    public String toString() {
        return String.format("GameTurnContainer [player=%s, predicted=%s, error=%s, computer=%s]", player, strategyPredicted, error, computer);
    }
}
