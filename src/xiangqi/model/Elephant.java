package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
    public Elephant(int color) {
        super(color);
        if (color == 0)
            this.code = 3;
        else if (color == 1)
            this.code = -3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Board board, Point start, Point end) {
        List<Point> moves = validMoves(board, start);
        for (Point p : moves) {
            if (end.equals(p))
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * The elephant moves and captures diagonally exactly 2 steps and cannot cross
     * the river.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color)
            return moves;

        /*
        Point c1 = start.add(new Point(-2, -2));
        Point c2 = start.add(new Point(2, -2));
        Point c3 = start.add(new Point(-2, 2));
        Point c4 = start.add(new Point(2, 2));

        if (canMove(board, start, c1))
            moves.add(c1);
        if (canMove(board, start, c2))
            moves.add(c2);
        if (canMove(board, start, c3))
            moves.add(c3);
        if (canMove(board, start, c4))
            moves.add(c4);
        */

        Point t1 = start.add(new Point(1, 1));
        if (board.containsPoint(t1) && board.pieceAt(t1) == null) {
            Point c = start.add(new Point(2, 2));
            if (board.containsPoint(c) && (board.pieceAt(c) == null
                    || board.pieceAt(c).getColor() != this.color))
                moves.add(c);
        }

        Point t2 = start.add(new Point(-1, -1));
        if (board.containsPoint(t2) && board.pieceAt(t2) == null) {
            Point c = start.add(new Point(-2, -2));
            if (board.containsPoint(c) && (board.pieceAt(c) == null
                    || board.pieceAt(c).getColor() != this.color))
                moves.add(c);
        }

        Point t3 = start.add(new Point(-1, 1));
        if (board.containsPoint(t3) && board.pieceAt(t3) == null) {
            Point c = start.add(new Point(-2, 2));
            if (board.containsPoint(c) && (board.pieceAt(c) == null
                    || board.pieceAt(c).getColor() != this.color))
                moves.add(c);
        }

        Point t4 = start.add(new Point(1, -1));
        if (board.containsPoint(t4) && board.pieceAt(t4) == null) {
            Point c = start.add(new Point(2, -2));
            if (board.containsPoint(c) && (board.pieceAt(c) == null
                    || board.pieceAt(c).getColor() != this.color))
                moves.add(c);
        }

        return moves;
    }

    @Override
    public Piece copy() {
        return new Elephant(this.color);
    }
}
