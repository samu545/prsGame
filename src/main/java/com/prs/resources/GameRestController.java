/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.resources;

import com.prs.game.GameMoveEnum;
import com.prs.game.GameService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/game")
public class GameRestController {

    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.POST)
    public GameMoveEnum turn(@NotNull @PathVariable Long playerId,
                             @NotNull @RequestBody GameMoveEnum playerThrow) {
        return gameService.makeTurn(playerThrow, playerId);
    }

}
