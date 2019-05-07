/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game;

public interface GameService {

    /**
     * GameService turn method which log player turn and return computer turn
     *
     * @param playerThrow
     * @param playerId
     * @return computer Throw
     */
    GameMoveEnum makeTurn(GameMoveEnum playerThrow, long playerId);
}
