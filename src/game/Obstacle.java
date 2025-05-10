package game;

import java.awt.*;

public class Obstacle {
    private Point position;

    public Obstacle(int x, int y) {
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }
}
