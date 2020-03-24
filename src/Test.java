import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EventListener;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new MyPaint());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

class MyPaint extends JPanel {
    public void init() {
        setBounds(200, 200, 600, 500);
        //setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏
        g.setColor(Color.BLACK);
        g.fillRect(100,100,100,100);
    }
}

@FunctionalInterface
interface ActionListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e);

}
