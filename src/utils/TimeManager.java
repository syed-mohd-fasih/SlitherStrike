package utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TimeManager {
    private Timer timer;
    private int delay;

    public TimeManager(String difficulty, ActionListener actionListener) {
        switch (difficulty) {
            case "Easy":
                delay = 150;
                break;
            case "Medium":
                delay = 100;
                break;
            case "Hard":
                delay = 50;
                break;
            default:
                delay = 120;
        }
        timer = new Timer(delay, actionListener);
    }

    public Timer getTimer() {
        return timer;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
