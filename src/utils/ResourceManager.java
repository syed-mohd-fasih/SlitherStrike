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
    public static Image OBSTACLE_ICON;
    public static Image SNAKE_HEAD;
    public static Image SNAKE_BODY;
    public static Image MAIN_BG;
    public static Image GAME_BG;
    public static Image GAME_OVER_BG;

    static {
        try {
            BOLT_ICON = ImageIO.read(ResourceManager.class.getResource("/bolt.png"));
            BOMB_ICON = ImageIO.read(ResourceManager.class.getResource("/bomb.png"));
            DOUBLE_ICON = ImageIO.read(ResourceManager.class.getResource("/double.png"));
            FRUIT_ICON = ImageIO.read(ResourceManager.class.getResource("/fruit.png"));
            SHIELD_ICON = ImageIO.read(ResourceManager.class.getResource("/shield.png"));
            OBSTACLE_ICON = ImageIO.read(ResourceManager.class.getResource("/rock.png"));
            SNAKE_HEAD = ImageIO.read(ResourceManager.class.getResource("/snake_head.png"));
            SNAKE_BODY = ImageIO.read(ResourceManager.class.getResource("/snake_body.png"));
            MAIN_BG = ImageIO.read(ResourceManager.class.getResource("/main_bg.png"));
            GAME_BG = ImageIO.read(ResourceManager.class.getResource("/game_bg.png"));
            GAME_OVER_BG = ImageIO.read(ResourceManager.class.getResource("/game_over.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
