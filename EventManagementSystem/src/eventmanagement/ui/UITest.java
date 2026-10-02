package eventmanagement.ui;

public class UITest {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            UI sample = new UI();
            sample.setVisible(true);
        });
    }
}