package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ResourceManager {
    public static Image BOLT_ICON;
    public static Image BOMB_ICON;
    public static Image DOUBLE_ICON;
    public static Image FRUIT_ICON;
    public static Image SHIELD_ICON;

    static {
        try {
            BOLT_ICON = ImageIO.read(ResourceManager.class.getResource("/bolt.png"));
            BOMB_ICON = ImageIO.read(ResourceManager.class.getResource("/bomb.png"));
            DOUBLE_ICON = ImageIO.read(ResourceManager.class.getResource("/double.png"));
            FRUIT_ICON = ImageIO.read(ResourceManager.class.getResource("/fruit.png"));
            SHIELD_ICON = ImageIO.read(ResourceManager.class.getResource("/shield.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
