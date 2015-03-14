package com.lukzar.model;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author lukasz
 */
public class BoardTest {

    /**
     * Test of isValidMove method, of class AI.
     */
    @Test
    public void testIsValidMove() {
        int[][] initialBoard = new int[][]{
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}};
        Board board = new Board(initialBoard, 1);
        Assert.assertTrue(board.isMoveValid(0));
        Assert.assertTrue(board.isMoveValid(3));
        Assert.assertFalse(board.isMoveValid(1));
        Assert.assertFalse(board.isMoveValid(4));
    }

    @Test
    public void testSums() {
        int[][] initialBoard = new int[][]{
            {0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0}
        };
        Board board = new Board(initialBoard, 1);
        Assert.assertEquals(3, board.sumVertical(2, 2, 1));
        Assert.assertEquals(3, board.sumHorizontal(2, 2, 1));
        Assert.assertEquals(2, board.sumLeftTop(2, 2, 1));
        Assert.assertEquals(2, board.sumRightTop(2, 2, 1));
        Assert.assertEquals(10, board.count(0));
    }

    @Test
    public void testIsWinning1() {
        int[][] initialBoard = new int[][]{
            {1, 2, 2, 2, 0},
            {1, 1, 1, 2, 1},
            {1, 2, 2, 1, 1},
            {1, 2, 1, 1, 1}
        };
        Board board = new Board(initialBoard, 1);
        Assert.assertEquals(1,board.hasVictory());
    }
    
    @Test
    public void testIsWinning2() {
        int[][] initialBoard = new int[][]{
            {0, 2, 2, 2, 2},
            {1, 1, 1, 2, 1},
            {1, 2, 2, 1, 1},
            {1, 2, 1, 1, 1}
        };
        Board board = new Board(initialBoard, 1);
        Assert.assertEquals(2,board.hasVictory());
    }

    @Test
    public void testMove() {
        int[][] initialBoard = new int[][]{
            {0, 0, 0, 0},
            {1, 0, 1, 1},
            {1, 1, 1, 1}
        };
        Board board = new Board(initialBoard, 1);
        board.makeMove(1);
        Assert.assertEquals(1, initialBoard[1][1]);
        board.undoMove(1);
        Assert.assertEquals(0, initialBoard[1][1]);
        board.makeMove(1);
        board.makeMove(1);
        Assert.assertEquals(1, initialBoard[1][1]);
        Assert.assertEquals(1, initialBoard[0][1]);
        board.undoMove(1);
        board.undoMove(1);
        Assert.assertEquals(0, initialBoard[1][1]);
        Assert.assertEquals(0, initialBoard[0][1]);
    }

}
