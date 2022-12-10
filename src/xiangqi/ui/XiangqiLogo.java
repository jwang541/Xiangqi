package xiangqi.ui;

import javax.swing.*;
import java.awt.*;

public class XiangqiLogo extends JComponent {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Resources.getLogoSprite(), 0, 0, 454, 155, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(454, 155);
    }
}
