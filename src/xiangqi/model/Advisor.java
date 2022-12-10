package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Piece {

    public Advisor(int color) {
        super(color);
        if (color == 0)
            this.code = 2;
        else if (color == 1)
            this.code = -2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Board board, Point start, Point end) {
        if (!board.containsPoint(start) || !board.containsPoint(end)
                || end.y < 3 || end.y > 5
                || Math.abs(start.x - end.x) != 1 || Math.abs(start.y - end.y) != 1
                || board.turn() % 2 != this.color) {
            return false;
        }
        if (this.color == 0) {
            if (end.x < 0 || end.x > 2)
                return false;
            if (board.pieceAt(end) == null)
                return true;
            else if (board.pieceAt(end).getColor() != this.color)
                return true;
        } else if (this.color == 1) {
            if (end.x < 7 || end.x > 9)
                return false;
            if (board.pieceAt(end) == null)
                return true;
            else if (board.pieceAt(end).getColor() != this.color)
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * The advisor moves and captures diagonally 1 step and cannot leave the palace.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color)
            return moves;

        Point c1 = start.add(new Point(-1, -1));
        Point c2 = start.add(new Point(1, -1));
        Point c3 = start.add(new Point(-1, 1));
        Point c4 = start.add(new Point(1, 1));

        if (canMove(board, start, c1))
            moves.add(c1);
        if (canMove(board, start, c2))
            moves.add(c2);
        if (canMove(board, start, c3))
            moves.add(c3);
        if (canMove(board, start, c4))
            moves.add(c4);

        return moves;
    }

    @Override
    public Piece copy() {
        return new Advisor(this.color);
    }
}
