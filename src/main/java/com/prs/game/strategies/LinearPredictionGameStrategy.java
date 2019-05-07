/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs.game.strategies;

import com.prs.game.GameMoveEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Paper-Rock-Scissors game prediction using Liner Prediction method
 * which resolves using Autocorrelation Method
 */
public class LinearPredictionGameStrategy extends AbstractGameStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static int ORDER = 7;
    private double[] coefficients;

    public LinearPredictionGameStrategy() {
        coefficients = new double[ORDER];
        logger.info("Linear Prediction Game Strategy initialized");
        //averageError = 2.0; //initialize as very high, first 4 turn prediction don't work
    }

    @Override
    public GameMoveEnum getPlayerPrediction() {
        double dPredict = 0;
        int i = 0;
        for (GameTurnContainer c : history) {
            dPredict += (c.getPlayer().getValue()) * coefficients[i];
            i++;
            if (i >= coefficients.length) {
                break;
            }
        }
        predicted = GameMoveEnum.fromValue((int) Math.round(Math.abs(dPredict)));
        return predicted;
    }

    @Override
    public void logPlayerTurn(GameMoveEnum playerThrow, GameMoveEnum computerThrow) {
        super.logPlayerTurn(playerThrow, computerThrow);

        double pe = 0.0;
        // compute autocorrelations
        double r[] = new double[ORDER + 1];  // temporary array
        for (int i = 0; i <= ORDER; i++) {
            double sum = 0;
            for (int k = 1; k <= history.size() - i - 1; k++) {
                sum += (history.get(k).getPlayer().getValue()) * (history.get(k + i).getPlayer().getValue());
            }
            r[i] = sum;
        }

        // compute predictor coefficients
        double pc[] = new double[ORDER + 1]; // temporary array
        pe = r[0];           // initialise error to total power
        pc[0] = 2.0;         // first coefficient (b[0]) must = 1 //Changes to 2 as max value

        // for each coefficient in turn
        for (int k = 1; k <= ORDER; k++) {

            // find next coeff from pc[] and r[]
            double sum = 0;
            for (int i = 1; i <= k; i++) {
                sum -= pc[k - i] * r[i];
            }
            pc[k] = sum / pe;

            // perform recursion on pc[]
            for (int i = 1; i <= k / 2; i++) {
                double pci = pc[i] + pc[k] * pc[k - i];
                double pcki = pc[k - i] + pc[k] * pc[i];
                pc[i] = pci;
                pc[k - i] = pcki;
            }

            // calculate residual error
            pe = pe * (1.0 - pc[k] * pc[k]);
        }
        // copy coefficients
        for (int i = 0; i < ORDER; i++) {
            coefficients[i] = pc[i + 1];
        }

    }

    @Override
    public String getStrategyName() {
        return "LinearPrediction Strategy";
    }

}
