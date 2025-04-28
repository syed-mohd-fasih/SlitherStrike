package game;

import java.awt.*;

public class Fruit {
    private Point position;

    public Fruit(int x, int y) {
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }
}
