package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    public Horse(int color) {
        super(color);
        if (color == 0)
            this.code = 4;
        else if (color == 1)
            this.code = -4;
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
     * The horse moves and captures 1 step orthogonally then 1 outwards diagonally.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color)
            return moves;

        Point t1 = start.add(new Point(1, 0));
        if (board.containsPoint(t1) && board.pieceAt(t1) == null) {
            Point c1 = start.add(new Point(2, -1));
            Point c2 = start.add(new Point(2, 1));

            if (board.containsPoint(c1) && (board.pieceAt(c1) == null
                    || board.pieceAt(c1).getColor() != this.color))
                moves.add(c1);
            if (board.containsPoint(c2) && (board.pieceAt(c2) == null
                    || board.pieceAt(c2).getColor() != this.color))
                moves.add(c2);
        }

        Point t2 = start.add(new Point(-1, 0));
        if (board.containsPoint(t2) && board.pieceAt(t2) == null) {
            Point c1 = start.add(new Point(-2, -1));
            Point c2 = start.add(new Point(-2, 1));

            if (board.containsPoint(c1) && (board.pieceAt(c1) == null
                    || board.pieceAt(c1).getColor() != this.color))
                moves.add(c1);
            if (board.containsPoint(c2) && (board.pieceAt(c2) == null
                    || board.pieceAt(c2).getColor() != this.color))
                moves.add(c2);
        }

        Point t3 = start.add(new Point(0, 1));
        if (board.containsPoint(t3) && board.pieceAt(t3) == null) {
            Point c1 = start.add(new Point(-1, 2));
            Point c2 = start.add(new Point(1, 2));

            if (board.containsPoint(c1) && (board.pieceAt(c1) == null
                    || board.pieceAt(c1).getColor() != this.color))
                moves.add(c1);
            if (board.containsPoint(c2) && (board.pieceAt(c2) == null
                    || board.pieceAt(c2).getColor() != this.color))
                moves.add(c2);
        }

        Point t4 = start.add(new Point(0, -1));
        if (board.containsPoint(t4) && board.pieceAt(t4) == null) {
            Point c1 = start.add(new Point(-1, -2));
            Point c2 = start.add(new Point(1, -2));

            if (board.containsPoint(c1) && (board.pieceAt(c1) == null
                    || board.pieceAt(c1).getColor() != this.color))
                moves.add(c1);
            if (board.containsPoint(c2) && (board.pieceAt(c2) == null
                    || board.pieceAt(c2).getColor() != this.color))
                moves.add(c2);
        }

        return moves;
    }

    @Override
    public Piece copy() {
        return new Horse(this.color);
    }
}
