package xiangqi.ui;

import xiangqi.model.*;
import xiangqi.utility.*;

import java.util.*;

public class AlphaBeta {
    /** arbitrary lower bound of the heuristic function */
    final static double MIN_EVAL = -1000;

    /** arbitrary upper bound of the heuristic function */
    final static double MAX_EVAL = 1000;

    /** number of heuristic functions evaluated since last reset */
    private static int positionsEvaluated = 0;

    /**
     * Approximates whether a position is better for Red or Black.
     *
     * @param board the position to consider
     * @return eval the evaluation of the board
     */
    public static double heuristic(Board board) {
        int[] redCodes = new int[8];
        int[] blackCodes = new int[8];
        for (int i = 0; i < board.NUM_ROWS; i++) {
            for (int j = 0; j < board.NUM_COLS; j++) {
                int code = (board.pieceAt(i, j) == null)
                        ? 0
                        : board.pieceAt(new Point(i, j)).getCode();
                if (code > 0) {
                    redCodes[code]++;
                } else if (code < 0) {
                    blackCodes[-1 * code]++;
                }
                if (code == 7 && i > 4) {
                    redCodes[7]++;
                } else if (code == -7 && i < 5) {
                    blackCodes[7]++;
                }
            }
        }
        double turn = (board.turn() % 2 == 0) ? 1.0 : -1.0;
        double eval = 0;
        eval += turn * 100.0 * (redCodes[1] - blackCodes[1]);
        eval += turn * 2.0 * (redCodes[2] - blackCodes[2]);
        eval += turn * 2.0 * (redCodes[3] - blackCodes[3]);
        eval += turn * 4.0 * (redCodes[4] - blackCodes[4]);
        eval += turn * 9.0 * (redCodes[5] - blackCodes[5]);
        eval += turn * 4.5 * (redCodes[6] - blackCodes[6]);
        eval += turn * 1.0 * (redCodes[7] - blackCodes[7]);
        eval += Math.random() * 0.01;

        positionsEvaluated++;
        return eval;
    }

    /**
     * Searches for the best move from a given position using
     * a minimax search with alpha-beta pruning.
     *
     * @param board the position to consider
     * @param depth how many more steps to look deeper
     * @param a     alpha-cutoff: worst score that current player is guaranteed
     * @param b     beta-cutoff: best score that opponent is guaranteed
     * @return the best move and the evaluation of the board resulting from that
     *         move
     */
    public static Pair<Double, Move> evaluate(Board board, int depth, double a, double b) {
        if (depth == 0) {
            return new Pair<>(heuristic(board), null);
        }

        List<Move> moves = board.candidateMoves();
        double maxEval = MIN_EVAL - 1;
        Move maxMove = null;

        board.setTurn(board.turn() + 1);
        for (Move m : moves) {
            board.setPieceAt(m.end, m.piece);
            board.setPieceAt(m.start, null);

            Pair<Double, Move> em = evaluate(board, depth - 1, -b, -a);
            double eval = -1 * em.first;
            if (eval > maxEval) {
                maxEval = eval;
                maxMove = m;
            }

            board.setPieceAt(m.start, m.piece);
            board.setPieceAt(m.end, m.target);

            a = Math.max(a, eval);
            if (a >= b)
                break;
        }
        board.setTurn(board.turn() - 1);

        return new Pair<>(maxEval, maxMove);
    }

    /**
     * Uses the search function with iterative deepening.
     *
     * @param board the position to consider
     * @return best the best move from the current position
     */
    public static Move move(Board board) {
        Move best = null;
        positionsEvaluated = 0;
        for (int i = 0; i <= 100; i++) {
            Pair<Double, Move> em = evaluate(board.copy(), i, MIN_EVAL, MAX_EVAL);
            best = em.second;

            if (positionsEvaluated > 1000000)
                break;
            positionsEvaluated = 0;
        }
        System.out.println("Positions evaluated: " + positionsEvaluated);
        positionsEvaluated = 0;
        return best;
    }

}
