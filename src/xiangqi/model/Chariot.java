package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
    public Chariot(int color) {
        super(color);
        if (color == 0)
            this.code = 5;
        else if (color == 1)
            this.code = -5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove(Board board, Point start, Point end) {
        if (!board.containsPoint(start) || !board.containsPoint(end)
                || (end.x - start.x != 0 && end.y - start.y != 0)
                || (board.turn() % 2 != this.color)) {
            return false;
        }

        if (board.pieceAt(end) != null) {
            if (board.pieceAt(end).getColor() == this.color)
                return false;
        }
        if (end.x - start.x == 0) {
            if (end.y > start.y) {
                for (int i = 1; i < end.y - start.y; i++) {
                    if (board.pieceAt(new Point(start.x, start.y + i)) != null)
                        return false;
                }
                return true;
            } else if (end.y < start.y) {
                for (int i = 1; i < start.y - end.y; i++) {
                    if (board.pieceAt(new Point(start.x, start.y - i)) != null)
                        return false;
                }
                return true;
            }
        } else if (end.y - start.y == 0) {
            if (end.x > start.x) {
                for (int i = 1; i < end.x - start.x; i++) {
                    if (board.pieceAt(new Point(start.x + i, start.y)) != null)
                        return false;
                }
                return true;
            } else if (end.x < start.x) {
                for (int i = 1; i < start.x - end.x; i++) {
                    if (board.pieceAt(new Point(start.x - i, start.y)) != null)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * The chariot moves and captures orthogonally any number of unlocked steps.
     */
    @Override
    public List<Point> validMoves(Board board, Point start) {
        List<Point> moves = new ArrayList<>();
        if (board.turn() % 2 != this.color)
            return moves;

        for (int i = 1; i <= 8; i++) {
            Point c = new Point(start.x, start.y - i);
            if (!board.containsPoint(c)) break;
            if (board.pieceAt(c) == null) {
                moves.add(c);
            } else {
                if (board.pieceAt(c).color != this.color)
                    moves.add(c);
                break;
            }
        }

        for (int i = 1; i <= 8; i++) {
            Point c = new Point(start.x, start.y + i);
            if (!board.containsPoint(c)) break;
            if (board.pieceAt(c) == null) {
                moves.add(c);
            } else {
                if (board.pieceAt(c).color != this.color)
                    moves.add(c);
                break;
            }
        }

        for (int i = 1; i <= 9; i++) {
            Point c = new Point(start.x - i, start.y);
            if (!board.containsPoint(c)) break;
            if (board.pieceAt(c) == null) {
                moves.add(c);
            } else {
                if (board.pieceAt(c).color != this.color)
                    moves.add(c);
                break;
            }
        }

        for (int i = 1; i <= 9; i++) {
            Point c = new Point(start.x + i, start.y);
            if (!board.containsPoint(c)) break;
            if (board.pieceAt(c) == null) {
                moves.add(c);
            } else {
                if (board.pieceAt(c).color != this.color)
                    moves.add(c);
                break;
            }
        }

        return moves;
    }

    @Override
    public Piece copy() {
        return new Chariot(this.color);
    }
}
