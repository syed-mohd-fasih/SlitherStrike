package utils;

import javax.swing.*;
import java.awt.*;

public class ResourceManager {
    // For now, we'll just handle images. Extend as needed.

    public static Image loadImage(String path) {
        return new ImageIcon(ResourceManager.class.getResource(path)).getImage();
    }

    // You can add methods to handle sounds or other assets
}
