import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public void init() {
        JFrame frame = new JFrame();
        setTitle("贪吃蛇");
        setResizable(false);
        setBounds(300,100,865,830); //840 *780
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //添加Panel
        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);
    }
}
