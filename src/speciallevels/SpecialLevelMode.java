package speciallevels;

import java.awt.*;

import game.Game;

public interface SpecialLevelMode {
    void activate(Game game);

    void update(Game game);

    void render(Graphics g, Graphics2D g2d, Game game);

    boolean isActive();
}
