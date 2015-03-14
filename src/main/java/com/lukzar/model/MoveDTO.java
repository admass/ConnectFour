package com.lukzar.model;

/**
 *
 * @author lukasz
 */
public class MoveDTO {

    private int move;
    private int[] moveValues;

    public MoveDTO(int move, int[] moves) {
        this.move = move;
        this.moveValues = moves;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int[] getMoveValues() {
        return moveValues;
    }

    public void setMoveValues(int[] moveValues) {
        this.moveValues = moveValues;
    }

    
}
