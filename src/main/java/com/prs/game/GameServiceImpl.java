/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game;

import com.prs.game.strategies.GameStrategy;
import com.prs.game.strategies.GameStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * GameService implementation
 * Player game sessions cleanups by ScheduledThread from old player sessions
 */
@Service
@Scope("singleton")
public class GameServiceImpl implements GameService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long EXPIRE_TIME = 1 * 3600 * 1000; //1 hour

    private final List<GameStrategyFactory> factories; //List of available Strategy Factories

    private final ConcurrentHashMap<Long, GameStrategyContainer> playerGameSessions;

    private final ScheduledExecutorService executor;

    @Autowired
    public GameServiceImpl(List<GameStrategyFactory> factories) {
        this.playerGameSessions = new ConcurrentHashMap<>();
        this.factories = factories;
        this.executor = Executors.newScheduledThreadPool(1);
    }

    @PostConstruct
    public void initCleanUp() {
        this.executor.scheduleWithFixedDelay(() -> {
            List<Long> expiredSessions = new ArrayList<>();
            long now = System.currentTimeMillis();
            playerGameSessions.forEach((key, value) -> {
                if (value != null && (now - value.getLastUsed()) >= EXPIRE_TIME) {
                    expiredSessions.add(key);
                }
            });
            expiredSessions.forEach(key -> playerGameSessions.remove(key));
        }, 1, 1, TimeUnit.HOURS);
    }

    @PreDestroy
    public void stopCleanUp() {
        this.executor.shutdownNow();
    }

    @Override
    public GameMoveEnum makeTurn(GameMoveEnum playerThrow, long playerId) {
        GameStrategyContainer container = playerGameSessions.computeIfAbsent(playerId, key -> new GameStrategyContainer(playerId, factories));

        GameMoveEnum computerThrow = GameMoveEnum.PAPER;
        if (container != null) {
            synchronized (container) {
                computerThrow = container.getComputerThrow();
                container.logPlayerTurn(playerThrow, computerThrow);
            }
        }
        logger.debug("Player {} throw {}, computer throw {}", playerId, playerThrow, computerThrow);
        return computerThrow;
    }

    public static class GameStrategyContainer {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        private final long playerId;

        private long lastUsed;

        private final List<GameStrategy> strategies;

        public GameStrategyContainer(long playerId, List<GameStrategyFactory> factories) {
            lastUsed = System.currentTimeMillis();
            strategies = new ArrayList<>();
            this.playerId = playerId;
            factories.forEach(factory -> {
                strategies.add(factory.getStrategy());
            });
        }

        public GameMoveEnum getComputerThrow() {
            GameMoveEnum playerPrediction = GameMoveEnum.PAPER;
            Optional<GameStrategy> playerBestStrategy = strategies.stream()
                    .sorted(Comparator.comparingDouble(GameStrategy::getAveragError)).findFirst();
            if (playerBestStrategy.isPresent()) {
                playerPrediction = playerBestStrategy.get().getPlayerPrediction();
                logger.trace("Player {}, Best {} predicts player throw {}", playerId, playerBestStrategy.get().getStrategyName(), playerPrediction);
            }
            if (logger.isTraceEnabled()) {
                strategies.forEach(s -> {
                    logger.trace("Player {}: {} predicts {}", playerId, s.getStrategyName(), s.getPlayerPrediction());
                });
            }
            switch (playerPrediction) {
                case PAPER:
                    return GameMoveEnum.SCISSORS;
                case ROCK:
                    return GameMoveEnum.PAPER;
                case SCISSORS:
                    return GameMoveEnum.ROCK;
                default:
                    return GameMoveEnum.PAPER;
            }
        }

        public void logPlayerTurn(GameMoveEnum playerThrow, GameMoveEnum computerThrow) {
            lastUsed = System.currentTimeMillis();
            strategies.forEach(gameStrategy -> {
                gameStrategy.logPlayerTurn(playerThrow, computerThrow);
                if (logger.isTraceEnabled()) {
                    logger.trace("Player {}, {} calculated error {}", playerId, gameStrategy.getStrategyName(), gameStrategy.getAveragError());
                }
            });
        }

        public long getLastUsed() {
            return lastUsed;
        }
    }
}
