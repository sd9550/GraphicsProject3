import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App() {
        add(new Board());
        setSize(460, 480);
        setTitle("Graphics Project 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var app = new App();
            //app.setResizable(false);
            app.setVisible(true);
        });
    }
}