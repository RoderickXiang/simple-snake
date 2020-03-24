
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener, ActionListener {  //键盘与定时器
    static final int BODYSIZE = 20;
    Snake snake = new Snake();
    Food food = new Food();
    Timer timer = new Timer(100, this);
    static boolean isStart = false;
    static boolean isDead = false;

    public GamePanel() {
        setFocusable(true); //设置监听焦点
        addKeyListener(this);   //添加键盘监听事件
        food.startTimer();
        timer.start();  //开始定时循环
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏
        setBackground(Color.BLACK); //设置背景
        g.setColor(Color.green);
        //画蛇
        for (int i = 0; i < Snake.length; i++)
            g.fillRect(Snake.snakeX[i], Snake.snakeY[i], BODYSIZE, BODYSIZE);
        //画食物
        g.setColor(Color.RED);
        g.fillOval(food.normalX, food.normalY, BODYSIZE, BODYSIZE);
        if (food.hasBonus) { //奖励食物
            g.setColor(Color.YELLOW);
            g.fillOval(food.bonusX, food.bonusY, BODYSIZE, BODYSIZE);
        }

        //画分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        g.drawString("目前得分：" + Point.point, 5, 20);

        //判断游戏状态
        if (!isStart) {
            g.setColor(Color.ORANGE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("使用空格开始游戏", 250, 300);
            timer.stop();
        } else if (isDead) {
            removeKeyListener(this);
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("GAME OVER", 250, 300);
            //结束分数
            try {
                Point.writeResult();
                Point.readBestResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 30));
            g.drawString("最后得分：" + Point.point, 250, 350);
            g.drawString("最高分：" + Point.bestScore, 250, 400);
            timer.stop();

        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == KeyEvent.VK_SPACE) {
            isStart = !isStart;
            if (isStart) {
                timer.start();
            }
        } else if (snake.isTmpMove) {   //判断小蛇是否移动，避免蛇头折回
            snake.changeDirection(e);
        }
        repaint();
    }

    //执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        snake.checkEatFood(food);
        food.changeFoodPosition();
        snake.checkCollide();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
