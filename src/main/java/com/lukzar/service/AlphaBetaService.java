package com.lukzar.service;

import com.lukzar.model.Board;
import com.lukzar.model.MoveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lukasz
 */
@Service
public class AlphaBetaService {
    
    @Autowired
    HeuristicService heuristicService;

    private static final int WINNING_MOVE = 100;
    private static final int MAX_DEPTH = 6;

    private final Board BOARD = new Board();

    public MoveDTO getMove(int[][] board, int player) {
        BOARD.setBoard(board);
        BOARD.setPlayer(player);
        int move = -1;
        int maxValue = Integer.MIN_VALUE;
        int[] moves = new int[7];

        for (int column : BOARD.getValidMoves()) {
            int moveValue = moveValue(column);
            moves[column] = moveValue;
            if (moveValue > maxValue) {
                maxValue = moveValue;
                move = column;
//                if (moveValue >= WINNING_MOVE) {
//                    break;
//                }
            }
        }
        return new MoveDTO(move, moves);
    }

    private int moveValue(int column) {
        BOARD.makeMove(column);
        int result = alphabeta(MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, BOARD.getPlayer() % 2 + 1);
        BOARD.undoMove(column);
        return result;
    }

    private int alphabeta(int depth, int alpha, int beta, int player) {
        int winner = BOARD.hasVictory();
        if (depth == 0 || winner > 0 || BOARD.isTie()) {
            return heuristicMoveValue(winner, depth, player);
        }
        boolean playerMove = player == BOARD.getPlayer();
        if (!playerMove) {
            for (int column : BOARD.getValidMoves()) {
                BOARD.makeMove(column, player);
                beta = Math.min(beta, alphabeta(depth - 1, alpha, beta, player % 2 + 1));
                BOARD.undoMove(column);
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        } else {
            for (int column : BOARD.getValidMoves()) {
                BOARD.makeMove(column, player);
                alpha = Math.max(alpha, alphabeta(depth - 1, alpha, beta, player % 2 + 1));
                BOARD.undoMove(column);
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        }
    }

    private int heuristicMoveValue(int winner, int depth, int player) {
        int score;
        if (winner > 0) {
            score = WINNING_MOVE + depth + 1;
            if (winner == BOARD.getPlayer()) {
                return score;
            } else {
                return -score;
            }
        } else {
            score = heuristicService.heuristicFunction(BOARD);
        }
        return score;
    }

}
