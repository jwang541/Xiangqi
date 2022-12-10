package xiangqi.model;

import xiangqi.utility.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Xiangqi position.
 */
public class Board {
    public final int NUM_ROWS = 10;
    public final int NUM_COLS = 9;

    /** Number of valid moves that have been made to get to this position */
    private int turn = 0;

    /** The arrangement of pieces on the board */
    private Piece[][] pieces = new Piece[NUM_ROWS][NUM_COLS];

    public boolean containsPoint(Point p) {
        return (p.x >= 0 && p.y >= 0
                && p.x < NUM_ROWS && p.y < NUM_COLS);
    }

    public Piece pieceAt(Point p) {
        if (containsPoint(p))
            return pieces[p.x][p.y];
        else
            return null;
    }

    public Piece pieceAt(int r, int c) {
        if (containsPoint(new Point(r, c)))
            return pieces[r][c];
        else
            return null;
    }

    public void setPieceAt(Point p, Piece piece) {
        pieces[p.x][p.y] = piece;
    }

    public int turn() {
        return turn;
    }

    public void setTurn(int t) {
        turn = t;
    }

    /**
     * Determines whether a given move is legal in this position.
     *
     * @param m the move to consider
     * @throws IllegalArgumentException if m is null
     */
    public void move(Move m) {
        if (m == null) {
            throw new IllegalArgumentException(m + " was null");
        }
        if (legal(m)) {
            pieces[m.end.x][m.end.y] = pieces[m.start.x][m.start.y];
            pieces[m.start.x][m.start.y] = null;
            turn++;
        } else {
            throw new IllegalArgumentException(m + " was an illegal move");
        }
    }

    /**
     * Return a list of all legal moves from this position that don't
     * take checks into account (i.e., you can move your general into check
     * or move a piece defending your general, ...).
     *
     * @return a list of all legal moves
     */
    public List<Move> candidateMoves() {
        List<Move> moves = new ArrayList<>();
        boolean hasKing = false;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (pieces[i][j] == null)
                    continue;
                if (turn % 2 == 0 && pieces[i][j].code == 1)
                    hasKing = true;
                if (turn % 2 == 1 && pieces[i][j].code == -1)
                    hasKing = true;
                List<Point> ends = pieces[i][j].validMoves(this, new Point(i, j));
                for (Point p : ends) {
                    moves.add(new Move(pieces[i][j], pieceAt(p), new Point(i, j), p));
                }
            }
        }
        /*
         * If the player to move does not have a general/king,
         * they cannot move.
         *
         * Technically this would never happen in a match between players,
         * but I think this makes the AI player a bit faster due to how the
         * alpha-beta search works.
         */
        if (hasKing)
            return moves;
        else
            return new ArrayList<>();
    }

    /**
     * Return a list of all legal moves from this position.
     *
     * @return a list of all legal moves
     */
    public List<Move> legalMoves() {
        List<Move> candidates = candidateMoves();

        /*
         * Perform a search to see if any move candidates lead to a position
         * where the opposing player can capture the current player's general.
         *
         * If they can, the move is not legal.
         */

        List<Move> legalMoves = new ArrayList<>();
        Board copy = this.copy();
        for (Move m : candidates) {
            copy.setPieceAt(m.end, m.piece);
            copy.setPieceAt(m.start, null);
            copy.setTurn(copy.turn() + 1);

            boolean legal = true;
            List<Move> enemyMoves = copy.candidateMoves();
            for (Move em : enemyMoves) {
                if (this.turn % 2 == 0) {
                    if (copy.pieceAt(em.end) != null
                            && copy.pieceAt(em.end).code == 1) {
                        legal = false;
                        break;
                    }
                } else {
                    if (copy.pieceAt(em.end) != null
                            && copy.pieceAt(em.end).code == -1) {
                        legal = false;
                        break;
                    }
                }
            }
            if (legal) {
                legalMoves.add(m);
            }

            copy.setTurn(copy.turn() - 1);
            copy.setPieceAt(m.start, m.piece);
            copy.setPieceAt(m.end, m.target);
        }
        return legalMoves;

    }

    /**
     * Determines whether a given move is legal in this position.
     *
     * @return true if the move is legal, false otherwise
     */
    public boolean legal(Move move) {
        boolean legal = false;
        for (Move lm : legalMoves()) {
            if (lm.equals(move)) {
                legal = true;
                break;
            }
        }
        return legal;
    }

    /**
     * Determines whether a given move is legal in this position.
     *
     * @return a deep copy of this
     */
    public Board copy() {
        Board that = new Board();
        that.turn = this.turn;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (this.pieces[i][j] == null)
                    that.pieces[i][j] = null;
                else
                    that.pieces[i][j] = this.pieces[i][j].copy();
            }
        }
        return that;
    }

    /**
     * Returns the traditional starting setup.
     *
     * @return a Board representing the setup
     */
    public static Board initialPosition() {
        Board b = new Board();

        // Red pieces
        b.pieces[0][4] = new General(0);
        b.pieces[0][3] = new Advisor(0);
        b.pieces[0][5] = new Advisor(0);
        b.pieces[0][2] = new Elephant(0);
        b.pieces[0][6] = new Elephant(0);
        b.pieces[0][1] = new Horse(0);
        b.pieces[0][7] = new Horse(0);
        b.pieces[0][0] = new Chariot(0);
        b.pieces[0][8] = new Chariot(0);

        b.pieces[2][1] = new Cannon(0);
        b.pieces[2][7] = new Cannon(0);

        b.pieces[3][0] = new Soldier(0);
        b.pieces[3][2] = new Soldier(0);
        b.pieces[3][4] = new Soldier(0);
        b.pieces[3][6] = new Soldier(0);
        b.pieces[3][8] = new Soldier(0);

        // Black pieces
        b.pieces[9][4] = new General(1);
        b.pieces[9][3] = new Advisor(1);
        b.pieces[9][5] = new Advisor(1);
        b.pieces[9][2] = new Elephant(1);
        b.pieces[9][6] = new Elephant(1);
        b.pieces[9][1] = new Horse(1);
        b.pieces[9][7] = new Horse(1);
        b.pieces[9][0] = new Chariot(1);
        b.pieces[9][8] = new Chariot(1);

        b.pieces[7][1] = new Cannon(1);
        b.pieces[7][7] = new Cannon(1);

        b.pieces[6][0] = new Soldier(1);
        b.pieces[6][2] = new Soldier(1);
        b.pieces[6][4] = new Soldier(1);
        b.pieces[6][6] = new Soldier(1);
        b.pieces[6][8] = new Soldier(1);

        return b;
    }
}
