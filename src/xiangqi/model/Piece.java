package xiangqi.model;

import xiangqi.utility.Point;

import java.util.List;

/**
 * Represents a Xiangqi piece.
 */
public abstract class Piece {
    /** Which side this is on: 1 = Red, 0 = Black */
    protected int color;

    /** A unique identifier manually assigned to each piece */
    protected int code;

    public Piece(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getCode() {
        return code;
    }

    /**
     * Returns a boolean whether a move is a valid "move candidate" this can make.
     *
     * @param board the current position
     * @param start the starting position of the move
     * @param end   the ending position of the move
     * @return a boolean whether a move is a valid "move candidate" this can make
     */
    public abstract boolean canMove(Board board, Point start, Point end);

    /**
     * Returns all valid "move candidates" this can make.
     *
     * @param board the current position
     * @param start the starting position of the move
     * @return a list of all "move candidates" this piece can make on this position
     *         starting from start
     */
    public abstract List<Point> validMoves(Board board, Point start);

    /**
     * Returns a copy of this.
     *
     * @return a copy of this
     */
    public abstract Piece copy();
}
