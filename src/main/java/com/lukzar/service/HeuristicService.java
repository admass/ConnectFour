package com.lukzar.service;

import com.lukzar.model.Board;
import org.springframework.stereotype.Component;

/**
 *
 * @author lukasz
 */
@Component
public class HeuristicService {

    private Board board;
    private int forPlayer;
    int[] playerMoves = new int[4];
    int[] opponentMoves = new int[4];

    public int heuristicFunction(Board board) {
        this.board = board;
        this.forPlayer = board.getPlayer();
        this.playerMoves = new int[]{0, 0, 0, 0};
        this.opponentMoves = new int[]{0, 0, 0, 0};
        for (int row = 0; row < board.rows(); row++) {
            for (int column = 0; column < board.columns(); column++) {
                if (board.valueAt(row, column) != 0) {
                    sumPossible(row, column, forPlayer);
                    sumPossible(row, column, (forPlayer % 2) + 1);
                }
            }
        }
        int score;
        score = (playerMoves[3] - opponentMoves[3]) * 50;
        score = score + ((playerMoves[2] - opponentMoves[2]) * 30);
        score = score + ((playerMoves[1] - opponentMoves[1]) * 10);
        score = score / 2;
        score = score < -99 ? -99 : score;
        score = score > 99 ? 99 : score;
        return score;
    }

    public void sumPossible(int row, int column, int player) {
        int number = board.sum(row, column, -1, 0, player);
        int possibleNumber = board.sumPossible(row, column, -1, 0, player);
        if (possibleNumber == 4) {
            addMove(number, player);
        }
        number = board.sum(row, column, -1, -1, player);
        possibleNumber = board.sumPossible(row, column, -1, -1, player);
        if (possibleNumber == 4) {
            addMove(number, player);
        }
        number = board.sum(row, column, -1, 1, player);
        possibleNumber = board.sumPossible(row, column, -1, 1, player);
        if (possibleNumber == 4) {
            addMove(number, player);
        }
        number = board.sum(row, column, 0, 1, player);
        possibleNumber = board.sumPossible(row, column, 0, 1, player);
        if (possibleNumber == 4) {
            addMove(number, player);
        }
        number = board.sum(row, column, 0, -1, player);
        possibleNumber = board.sumPossible(row, column, 0, -1, player);
        if (possibleNumber == 4) {
            addMove(number, player);
        }
    }

    private void addMove(int number, int player) {
        if (player == forPlayer) {
            playerMoves[number]++;
        } else {
            opponentMoves[number]++;
        }
    }

}
