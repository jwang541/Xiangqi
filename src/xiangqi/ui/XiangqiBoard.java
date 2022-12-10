package xiangqi.ui;

import xiangqi.model.Game;
import xiangqi.model.Move;
import xiangqi.utility.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Component representing a Xiangqi board and some other functions.
 */
public class XiangqiBoard extends JComponent {
    final String PLAYER_SAVE_FILE = "vs_player_save.txt";
    final String COMPUTER_SAVE_FILE = "vs_computer_save.txt";
    final String SAVE_DIR = "files/saves/";

    private final int SIDE_LENGTH = 72;
    private final int X_OFFSET = 50;
    private final int Y_OFFSET = 50;

    private Game game;
    private Point selected;
    private Move lastMove;

    public enum GameType {
        PLAYER,
        COMPUTER
    }

    private GameType gameType;

    private JTextArea historyArea;
    private JScrollPane historyScrollPane;
    private JButton newGameButton;
    private JButton saveAndExitButton;

    public XiangqiBoard(GameType gameType) {
        this.game = new Game();
        this.gameType = gameType;

        if (gameType == GameType.PLAYER) {
            this.game = Game.loadFromSave(SAVE_DIR + PLAYER_SAVE_FILE);
        } else {
            this.game = Game.loadFromSave(SAVE_DIR + COMPUTER_SAVE_FILE);
        }

        selected = null;
        lastMove = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = 9 - (e.getY() - Y_OFFSET + SIDE_LENGTH / 2) / SIDE_LENGTH;
                int col = (e.getX() - X_OFFSET + SIDE_LENGTH / 2) / SIDE_LENGTH;

                Point click = new Point(row, col);
                if (selected == null) {
                    if (getGame().getBoard().containsPoint(click))
                        selected = click;
                } else {
                    // Attempts a move if it is the player turn
                    if (getGame().getBoard().containsPoint(click)) {
                        Move playerMove = new Move(
                                getGame().getBoard().pieceAt(selected),
                                getGame().getBoard().pieceAt(click),
                                selected,
                                click
                        );
                        if (getGame().getBoard().legal(playerMove)) {
                            try {
                                getGame().move(playerMove);
                                lastMove = playerMove;
                            } catch (IllegalArgumentException ex) {
                                System.out.println(ex.getMessage());
                            }
                            if (gameType == GameType.COMPUTER) {
                                try {
                                    Move computerMove = AlphaBeta.move(getGame().getBoard());
                                    getGame().move(computerMove);
                                    lastMove = computerMove;
                                } catch (IllegalArgumentException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                            selected = null;
                        } else {
                            if (selected.equals(click))
                                selected = null;
                            else
                                selected = click;
                        }
                    }
                }

                updateHistoryText();

                repaint();
            }
        });
        setLayout(null);

        historyArea = new JTextArea("");
        historyArea.setMargin(new Insets(10, 10, 10, 10));
        historyArea.setFont(new Font("Geneva", Font.PLAIN, 16));

        historyArea.setTabSize(2);
        historyArea.setEditable(false);

        updateHistoryText();

        historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setViewportView(historyArea);
        historyScrollPane.setBounds(725, 125, 250, 350);

        setBackground(new Color(0, 0, 0, 0));
        add(historyScrollPane);

        newGameButton = new JButton("New Game");
        newGameButton.setBounds(713, 585, 270, 30);
        newGameButton.addActionListener(e -> {
            resetGame();
            selected = null;
            lastMove = null;
            repaint();
        });
        add(newGameButton);

        saveAndExitButton = new JButton("Save and Quit");
        saveAndExitButton.setBounds(713, 635, 270, 30);
        saveAndExitButton.addActionListener(e -> {
            try {
                selected = null;
                lastMove = null;

                String savePath = SAVE_DIR;

                if (gameType == GameType.PLAYER)
                    savePath += PLAYER_SAVE_FILE;
                else if (gameType == GameType.COMPUTER)
                    savePath += COMPUTER_SAVE_FILE;

                PrintWriter writer = new PrintWriter(savePath, "UTF-8");
                for (Move m : getGame().getHistory()) {
                    writer.print(m.start.x + " ");
                    writer.print(m.start.y + " ");
                    writer.print(m.end.x + " ");
                    writer.print(m.end.y + "\n");
                }
                writer.flush();
                writer.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(saveAndExitButton);
    }

    private void paintHighlights(Graphics2D g2d) {
        if (lastMove != null) {
            g2d.drawImage(
                    Resources.getScarletHighlightSprite(),
                    32 + SIDE_LENGTH * lastMove.start.y,
                    32 + SIDE_LENGTH * (9 - lastMove.start.x),
                    20, 20, null
            );
            g2d.drawImage(
                    Resources.getScarletHighlightSprite(),
                    5 + SIDE_LENGTH * lastMove.end.y,
                    5 + SIDE_LENGTH * (9 - lastMove.end.x),
                    70, 70, null
            );
        }

        if (selected != null) {
            if (game.getBoard().pieceAt(selected) != null) {
                g2d.drawImage(
                        Resources.getBlueHighlightSprite(),
                        5 + SIDE_LENGTH * selected.y,
                        5 + SIDE_LENGTH * (9 - selected.x),
                        70, 70, null
                );

                java.util.List<Move> moves = game.getBoard().legalMoves();
                moves.removeIf(p -> !p.start.equals(selected));

                for (Move m : moves) {
                    Point p = m.end;
                    if (game.getBoard().pieceAt(p) == null) {
                        g2d.drawImage(
                                Resources.getPurpleHighlightSprite(),
                                32 + SIDE_LENGTH * p.y,
                                32 + SIDE_LENGTH * (9 - p.x),
                                20, 20, null
                        );
                    } else {
                        g2d.drawImage(
                                Resources.getPurpleHighlightSprite(),
                                5 + SIDE_LENGTH * p.y,
                                5 + SIDE_LENGTH * (9 - p.x),
                                70, 70, null
                        );
                    }
                }
            } else {
                g2d.drawImage(
                        Resources.getBlueHighlightSprite(),
                        32 + SIDE_LENGTH * selected.y,
                        32 + SIDE_LENGTH * (9 - selected.x),
                        20, 20, null
                );
            }
        }
    }

    private void paintBoard(Graphics2D g2d) {
        for (int i = 0; i < game.getBoard().NUM_ROWS; i++) {
            for (int j = 0; j < game.getBoard().NUM_COLS; j++) {
                if (game.getBoard().pieceAt(i, j) != null) {
                    g2d.drawImage(
                            Resources.getPieceSprite(
                                    game.getBoard().pieceAt(new Point(i, j)).getCode()
                            ),
                            10 + j * SIDE_LENGTH,
                            10 + (9 - i) * SIDE_LENGTH,
                            60, 60, null
                    );
                }
            }
        }
    }

    private void paintSidebar(Graphics2D g2d) {
        Stroke oldStroke = g2d.getStroke();

        g2d.setStroke(new BasicStroke(5));

        g2d.setColor(new Color(225, 225, 225));
        g2d.fillRect(700, 50, 300, 500);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(oldStroke);

        g2d.setFont(new Font("Geneva", Font.PLAIN, 24));

        String player1Name = "Player 1";
        String player2Name = (gameType == GameType.PLAYER) ? "Player 2" : "Computer";

        if (game.isFinished()) {
            if (game.getBoard().turn() % 2 == 0) {
                g2d.drawString(player2Name + " (1 - 0)", 725, 100);
                g2d.drawString(player1Name + " (0 - 1)", 725, 525);
            } else {
                g2d.drawString(player2Name + " (0 - 1)", 725, 100);
                g2d.drawString(player1Name + " (1 - 0)", 725, 525);
            }
        } else {
            if (game.getBoard().turn() % 2 == 0) {
                g2d.drawString(player2Name, 725, 100);
                g2d.drawString(player1Name + " . . .", 725, 525);
            } else {
                g2d.drawString(player2Name + " . . .", 725, 100);
                g2d.drawString(player1Name, 725, 525);
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g2d.drawImage(
                Resources.getBoardSprite(), 40, 40,
                8 * SIDE_LENGTH + (int) (SIDE_LENGTH * 0.05),
                9 * SIDE_LENGTH + (int) (SIDE_LENGTH * 0.05), null
        );

        paintHighlights(g2d);
        paintBoard(g2d);
        paintSidebar(g2d);
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 750);
    }

    public JButton getNewGameButton() {
        return this.newGameButton;
    }

    public JButton getSaveAndExitButton() {
        return this.saveAndExitButton;
    }

    public void resetGame() {
        this.game = new Game();
        updateHistoryText();
    }

    public Game getGame() {
        return this.game;
    }

    private void updateHistoryText() {
        StringBuilder historyText = new StringBuilder();
        java.util.List<Move> history = getGame().getHistory();

        for (int i = 0; i < history.size(); i++) {
            if (i % 2 == 0) {
                historyText.append(i / 2 + 1).append(". ");
                historyText.append(history.get(i)).append("      ");
            }
            if (i % 2 == 1) {
                historyText.append(history.get(i));
                historyText.append("\n");
            }
        }

        historyArea.setText(historyText.toString());
    }

}