package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(int color) {
        super(color);
        if (color == 0)
            this.code = 7;
        else if (color == 1)
            this.code = -7;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Board board, Point start, Point end) {
        if (!board.containsPoint(start) || !board.containsPoint(end)
                || board.turn() % 2 != this.color) {
            return false;
        }

        if (board.pieceAt(end) != null) {
            if (board.pieceAt(end).getColor() == this.color)
                return false;
        }
        if (this.color == 0) {
            if (end.x - start.x == 1 && end.y == start.y)
                return true;
            if (start.x > 4 && end.x == start.x && Math.abs(end.y - start.y) == 1)
                return true;
        } else if (this.color == 1) {
            if (end.x - start.x == -1 && end.y == start.y)
                return true;
            if (start.x < 5 && end.x == start.x && Math.abs(end.y - start.y) == 1)
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * The soldier moves and captures forward 1 step. After crossing the river, can
     * also
     * go sideways 1 step.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color) {
            return moves;
        }

        Point c1 = start.add(new Point(1, 0));
        Point c2 = start.add(new Point(0, 1));
        Point c3 = start.add(new Point(-1, 0));
        Point c4 = start.add(new Point(0, -1));

        if (canMove(board, start, c1)) {
            moves.add(c1);
        }
        if (canMove(board, start, c2)) {
            moves.add(c2);
        }
        if (canMove(board, start, c3)) {
            moves.add(c3);
        }
        if (canMove(board, start, c4)) {
            moves.add(c4);
        }

        return moves;
    }

    @Override
    public Piece copy() {
        return new Soldier(this.color);
    }

}
