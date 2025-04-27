public class Main {
    public static void main(String[] args) {
        // Always start GUI apps on Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new ui.MainMenu();
        });
    }
}