package speciallevels;

import game.Game;

import java.awt.*;

public class SplitHeadsMode implements SpecialLevelMode {
    private boolean active;

    @Override
    public void activate(Game game) {
        active = true;
    }

    @Override
    public void update(Game game) {
    }

    @Override
    public void render(Graphics g, Graphics2D g2d, Game game) {
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
