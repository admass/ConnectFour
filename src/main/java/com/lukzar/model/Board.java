package com.lukzar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class Board {

    public static int NUMBER_TO_WIN = 4;

    int[][] board;
    int player;

    public Board(int[][] board, int player) {
        this.board = board;
        this.player = player;
    }

    public Board() {
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return this.player;
    }

    public int rows() {
        return board.length;
    }

    public int columns() {

        return rows() > 0 ? board[0].length : 0;
    }

    public int valueAt(int row, int column) {
        if (0 <= row && row < rows()
                && 0 <= column && column < columns()) {
            return this.board[row][column];
        }
        return -1;
    }

    public void setValue(int row, int column, int value) {
        board[row][column] = value;
    }

    public void makeMove(int column) {
        setValue(firstFreeRowInColumn(column), column, this.player);
    }

    public void makeMove(int column, int player) {
        setValue(firstFreeRowInColumn(column), column, player);
    }

    public void undoMove(int column) {
        setValue(firstFreeRowInColumn(column) + 1, column, 0);
    }

    public boolean isColumnFull(int column) {
        return valueAt(0, column) != 0;
    }

    public boolean isMoveValid(int column) {
        return (column < columns() && !isColumnFull(column));
    }

    public int firstFreeRowInColumn(int column) {
        for (int i = rows() - 1; i >= 0; i--) {
            if (valueAt(i, column) == 0) {
                return i;
            }
        }
        return -1;
    }

    public int sum(int fromRow, int fromCol, int rowDir, int colDir, int player) {
        int sum = 0;
        int i = fromRow, j = fromCol;
        while (valueAt(i, j) == player) {
            i += rowDir;
            j += colDir;
            sum += 1;
        }
        return sum;
    }

    public int sumPossible(int fromRow, int fromCol, int rowDir, int colDir, int player) {
        int sum = 0;
        int i = fromRow, j = fromCol;
        while ((valueAt(i, j) == player || valueAt(i, j) == 0) && sum != 4) {
            i += rowDir;
            j += colDir;
            sum += 1;
        }
        return sum > 4 ? 4 : sum;
    }

    public int hasVictory() {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++) {
                if (isVictoryField(i, j)) {
                    return valueAt(i, j);
                }
            }
        }
        return 0;
    }

    public boolean isTie() {
        for (int i = 0; i < columns(); i++) {
            if (valueAt(0, i) == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isVictoryField(int row, int column) {
        int playerAt = valueAt(row, column);
        return (playerAt != 0) && (sumHorizontal(row, column, playerAt) >= NUMBER_TO_WIN - 1
                || sumVertical(row, column, playerAt) >= NUMBER_TO_WIN - 1
                || sumLeftTop(row, column, playerAt) >= NUMBER_TO_WIN - 1
                || sumRightTop(row, column, playerAt) >= NUMBER_TO_WIN - 1);
    }

    public int sumRightTop(final int row, int column, int player) {
        return sum(row - 1, column + 1, -1, 1, player) + sum(row + 1, column - 1, 1, -1, player);
    }

    public int sumLeftTop(final int row, int column, int player) {
        return sum(row - 1, column - 1, -1, -1, player) + sum(row + 1, column + 1, 1, 1, player);
    }

    public int sumVertical(final int row, int column, int player) {
        return sum(row - 1, column, -1, 0, player) + sum(row + 1, column, 1, 0, player);
    }

    public int sumHorizontal(final int row, int column, int player) {
        return sum(row, column + 1, 0, 1, player) + sum(row, column - 1, 0, -1, player);
    }

    public List<Integer> getValidMoves() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < columns(); i++) {
            if (isMoveValid(i)) {
                list.add(i);
            }
        }
        Collections.shuffle(list);
        return list;
    }

    public int count(int value) {
        int count = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == value) {
                    count++;
                }
            }
        }
        return count;
    }

}
