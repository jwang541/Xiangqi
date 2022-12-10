package xiangqi.ui;

import javax.swing.*;
import java.awt.*;

public class RunXiangqi implements Runnable {
    private final Dimension WINDOW_DIMENSION = new Dimension(1200, 800);

    JFrame frame;
    JPanel cards;

    @Override
    public void run() {
        Resources.loadAll();

        frame = new JFrame("Xiangqi");
        frame.setSize(WINDOW_DIMENSION);
        frame.setPreferredSize(WINDOW_DIMENSION);
        frame.getContentPane().setBackground(new Color(225, 225, 225));

        cards = new JPanel(new CardLayout());
        cards.add(titleScene(), "title");
        cards.add(playerGameScene(), "player");
        cards.add(computerGameScene(), "computer");
        cards.add(instructionsScene(), "instructions");

        frame.add(cards);

        switchScene("title");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void switchScene(String name) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, name);
    }

    private JPanel playerGameScene() {
        JPanel layout = new JPanel();
        layout.setBackground(new Color(255, 255, 255));

        XiangqiBoard xiangqiBoard = new XiangqiBoard(XiangqiBoard.GameType.PLAYER);
        xiangqiBoard.getSaveAndExitButton().addActionListener(e -> switchScene("title"));
        layout.add(xiangqiBoard);

        return layout;
    }

    private JPanel computerGameScene() {
        JPanel layout = new JPanel();
        layout.setBackground(new Color(255, 255, 255));

        XiangqiBoard xiangqiBoard = new XiangqiBoard(XiangqiBoard.GameType.COMPUTER);
        xiangqiBoard.getSaveAndExitButton().addActionListener(e -> switchScene("title"));
        layout.add(xiangqiBoard);

        return layout;
    }

    private JPanel titleScene() {
        JPanel layout = new JPanel();
        layout.setLayout(null);
        layout.setBackground(new Color(255, 255, 255));

        XiangqiLogo logo = new XiangqiLogo();
        logo.setBounds(376, 200, 454, 155);
        layout.add(logo);

        JButton playerButton = new JButton("vs. Player");
        playerButton.setBounds(465, 400, 270, 30);
        playerButton.addActionListener(e -> switchScene("player"));
        layout.add(playerButton);

        JButton computerButton = new JButton("vs. Computer");
        computerButton.setBounds(465, 450, 270, 30);
        computerButton.addActionListener(e -> switchScene("computer"));
        layout.add(computerButton);

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setBounds(465, 500, 270, 30);
        instructionsButton.addActionListener(e -> switchScene("instructions"));
        layout.add(instructionsButton);

        return layout;
    }

    private JPanel instructionsScene() {
        JPanel layout = new JPanel();
        layout.setLayout(null);
        layout.setBackground(new Color(255, 255, 255));

        JTextArea instructions = new JTextArea();
        instructions.setBounds(300, 100, 600, 450);
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        instructions.setText(
                """
                        The game is played on a board with a 9x10 grid separated in the middle by the "River."

                        Each player has 16 pieces, including a king, two guards, two elephants, two horses, two chariots, two cannons, and five soldiers.

                        The king is placed in the center of the board, and the other pieces are placed on the three rows in front of the king.

                        The objective of the game is to capture the opponent's king, or to block the opponent's moves If a player's king is in danger of being captured, they must make a move to protect it. If no such move is possible, they lose.

                        If a player is unable to make a legal move, they lose."""
        );

        layout.add(instructions);
        instructions.setOpaque(false);
        instructions.setFont(new Font("Geneva", Font.PLAIN, 20));
        instructions.setBackground(new Color(0, 0, 0, 0));
        instructions.setForeground(Color.BLACK);

        JButton playerButton = new JButton("Back");
        playerButton.setBounds(465, 600, 270, 30);
        layout.add(playerButton);
        playerButton.addActionListener(e -> switchScene("title"));

        return layout;
    }

}
