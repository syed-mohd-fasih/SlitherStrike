package game;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;
    private int speed;
    private boolean invincible;
    private boolean visible = true;
    private Direction pendingDirection = null;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(5, 5)); // Starting point
        direction = Direction.RIGHT;
        invincible = false;
        speed = 100; // Base speed
    }

    public void move() {
        Point head = new Point(body.getFirst());
        switch (direction) {
            case UP -> head.y--;
            case DOWN -> head.y++;
            case LEFT -> head.x--;
            case RIGHT -> head.x++;
        }
        body.addFirst(head);
        body.removeLast();
    }

    public void grow() {
        Point head = new Point(body.getFirst());
        body.addFirst(head);
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction newDirection) {
        // Always allow setting pending direction (even if invalid 180)
        pendingDirection = newDirection;
    }

    public Direction getPendingDirection() {
        return pendingDirection;
    }

    public void applyPendingDirection() {
        if ((this.direction == Direction.UP && pendingDirection != Direction.DOWN) ||
                (this.direction == Direction.DOWN && pendingDirection != Direction.UP) ||
                (this.direction == Direction.LEFT && pendingDirection != Direction.RIGHT) ||
                (this.direction == Direction.RIGHT && pendingDirection != Direction.LEFT)) {

            this.direction = pendingDirection;
        }
        pendingDirection = null; // Reset after applying
    }

    public void increaseSpeed() {
        speed += 50;
    }

    public void resetSpeed() {
        speed = 100;  // Reset to base speed
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
