package xiangqi.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Global "library" containing all the images used so we
 * can just load all of them at once and use them wherever
 * we need them later.
 */
public class Resources {
    private static final String BOARD_SPRITE_PATH = "files/resources/xiangqi_board.png";
    private static final String GENERAL_SPRITE_PATH = "files/resources/xiangqi_generals.png";
    private static final String ADVISOR_SPRITE_PATH = "files/resources/xiangqi_advisors.png";
    private static final String ELEPHANT_SPRITE_PATH = "files/resources/xiangqi_elephants.png";
    private static final String HORSE_SPRITE_PATH = "files/resources/xiangqi_horses.png";
    private static final String CHARIOT_SPRITE_PATH = "files/resources/xiangqi_chariots.png";
    private static final String CANNON_SPRITE_PATH = "files/resources/xiangqi_cannons.png";
    private static final String SOLDIER_SPRITE_PATH = "files/resources/xiangqi_soldiers.png";
    private static final String BLUE_HIGHLIGHT_SPRITE_PATH = "files/resources/xiangqi_highlight_blue.png";
    private static final String PURPLE_HIGHLIGHT_SPRITE_PATH = "files/resources/xiangqi_highlight_purple.png";
    private static final String SCARLET_HIGHLIGHT_SPRITE_PATH = "files/resources/xiangqi_highlight_scarlet.png";
    private static final String LOGO_SPRITE_PATH = "files/resources/xiangqi_logo.png";

    private static Image boardSprite = null;
    private static Image[] traditionalRedSprites = new Image[8];
    private static Image[] traditionalBlackSprites = new Image[8];
    private static Image[] redSprites = new Image[8];
    private static Image[] blackSprites = new Image[8];
    private static Image blueHighlightSprite = null;
    private static Image purpleHighlightSprite = null;
    private static Image scarletHighlightSprite = null;
    private static Image logoSprite = null;

    public static Image load(String relativePath) {
        try {
            return ImageIO.read(new File(relativePath).getAbsoluteFile());
        } catch (IOException e) {
            System.out.println("Could not open " + new File(relativePath).getAbsolutePath());
            return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        }
    }

    public static Image load(String relativePath, int x, int y, int w, int h) {
        try {
            return ImageIO.read(new File(relativePath).getAbsoluteFile())
                    .getSubimage(x, y, w, h);
        } catch (IOException e) {
            System.out.println("Could not open " + new File(relativePath).getAbsolutePath());
            return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        }
    }

    public static void loadAll() {
        boardSprite = load(BOARD_SPRITE_PATH);

        traditionalRedSprites[1] = load(GENERAL_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[1] = load(GENERAL_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[2] = load(ADVISOR_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[2] = load(ADVISOR_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[3] = load(ELEPHANT_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[3] = load(ELEPHANT_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[4] = load(HORSE_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[4] = load(HORSE_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[5] = load(CHARIOT_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[5] = load(CHARIOT_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[6] = load(CANNON_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[6] = load(CANNON_SPRITE_PATH, 200, 0, 100, 100);

        traditionalRedSprites[7] = load(SOLDIER_SPRITE_PATH, 0, 0, 100, 100);
        traditionalBlackSprites[7] = load(SOLDIER_SPRITE_PATH, 200, 0, 100, 100);

        redSprites[1] = load(GENERAL_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[1] = load(GENERAL_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[2] = load(ADVISOR_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[2] = load(ADVISOR_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[3] = load(ELEPHANT_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[3] = load(ELEPHANT_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[4] = load(HORSE_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[4] = load(HORSE_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[5] = load(CHARIOT_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[5] = load(CHARIOT_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[6] = load(CANNON_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[6] = load(CANNON_SPRITE_PATH, 300, 0, 100, 100);

        redSprites[7] = load(SOLDIER_SPRITE_PATH, 100, 0, 100, 100);
        blackSprites[7] = load(SOLDIER_SPRITE_PATH, 300, 0, 100, 100);

        blueHighlightSprite = load(BLUE_HIGHLIGHT_SPRITE_PATH);
        purpleHighlightSprite = load(PURPLE_HIGHLIGHT_SPRITE_PATH);
        scarletHighlightSprite = load(SCARLET_HIGHLIGHT_SPRITE_PATH);

        logoSprite = load(LOGO_SPRITE_PATH);
    }

    public static Image getBoardSprite() {
        return boardSprite;
    }

    public static Image getPieceSprite(int code) {
        try {
            if (code > 0) {
                return redSprites[code];
            } else if (code < 0) {
                return blackSprites[-1 * code];
            } else {
                return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        }
    }

    public static Image getBlueHighlightSprite() {
        return blueHighlightSprite;
    }

    public static Image getPurpleHighlightSprite() {
        return purpleHighlightSprite;
    }

    public static Image getScarletHighlightSprite() {
        return scarletHighlightSprite;
    }

    public static Image getLogoSprite() {
        return logoSprite;
    }

}
