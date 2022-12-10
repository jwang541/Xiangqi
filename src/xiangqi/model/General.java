package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class General extends Piece {
    public General(int color) {
        super(color);
        if (color == 0)
            this.code = 1;
        else if (color == 1)
            this.code = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Board board, Point start, Point end) {
        if (!board.containsPoint(start) || !board.containsPoint(end)
                || end.y < 3 || end.y > 5
                || (start.x != end.x && start.y != end.y
                        || board.turn() % 2 != this.color)) {
            return false;
        }
        if (this.color == 0) {
            if (end.x < 0)
                return false;
            if (end.x > 2) {
                if (start.y == end.y && board.pieceAt(end) != null
                        && board.pieceAt(end).code == -1) {
                    for (int i = 1; i < end.x - start.x; i++) {
                        if (board.pieceAt(start.add(new Point(i, 0))) != null) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
            if (board.pieceAt(end) == null)
                return true;
            else if (board.pieceAt(end).getColor() != this.color)
                return true;
        } else if (this.color == 1) {
            if (end.x > 9)
                return false;
            if (end.x < 7) {
                if (start.y == end.y && board.pieceAt(end) != null
                        && board.pieceAt(end).code == 1) {
                    for (int i = 1; i < start.x - end.x; i++) {
                        if (board.pieceAt(start.add(new Point(-i, 0))) != null) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
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
     * The general moves and captures orthogonally 1 step and cannot leave the
     * palace.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color)
            return moves;

        Point c1 = start.add(new Point(1, 0));
        Point c3 = start.add(new Point(0, 1));
        Point c5 = start.add(new Point(-1, 0));
        Point c7 = start.add(new Point(0, -1));

        if (canMove(board, start, c1))
            moves.add(c1);
        if (canMove(board, start, c3))
            moves.add(c3);
        if (canMove(board, start, c5))
            moves.add(c5);
        if (canMove(board, start, c7))
            moves.add(c7);

        for (int i = 5; i <= 9; i++) {
            Point fg1 = start.add(new Point(i, 0));
            Point fg2 = start.add(new Point(-i, 0));

            if (canMove(board, start, fg1))
                moves.add(fg1);
            if (canMove(board, start, fg2))
                moves.add(fg2);
        }

        return moves;
    }

    @Override
    public Piece copy() {
        return new General(this.color);
    }
}
