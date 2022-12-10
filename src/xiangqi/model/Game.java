package xiangqi.model;

import xiangqi.utility.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private Board board;
    private List<Move> history;

    public Game() {
        board = Board.initialPosition();
        history = new ArrayList<>();
    }

    public void move(Move m) {
        if (m == null) {
            throw new IllegalArgumentException("argument was null");
        }
        try {
            board.move(m);
            history.add(m);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getHistory() {
        return history;
    }

    public boolean isFinished() {
        List<Move> legalMoves = board.legalMoves();
        return (legalMoves.isEmpty());
    }

    public static Game loadFromSave(String filePath) {
        try {
            Game game = new Game();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                String[] tokens = line.split(" ");
                if (tokens.length != 4) {
                    System.out.println("Xiangqi file format invalid: needs 4 coordinates per row");
                    return new Game();
                }
                int[] coords;
                try {
                    coords = Arrays.stream(tokens).mapToInt((Integer::parseInt)).toArray();
                } catch (NumberFormatException e) {
                    System.out.println("Number formatting exception while loading game");
                    return new Game();
                }
                Point start = new Point(coords[0], coords[1]);
                Point end = new Point(coords[2], coords[3]);

                Move m = new Move(game.board.pieceAt(start), game.board.pieceAt(end), start, end);
                game.board.setPieceAt(end, game.board.pieceAt(start));
                game.board.setPieceAt(start, null);
                game.history.add(m);
                game.board.setTurn(game.board.turn() + 1);

                line = br.readLine();
            }
            return game;
        } catch (IOException e) {
            System.out.println("IOException while loading game");
            return new Game();
        }
    }
}
