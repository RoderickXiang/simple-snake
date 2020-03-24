import java.awt.event.KeyEvent;

public class Snake {
    static String direction = "right";
    static int length = 3;
    static int[] snakeX = new int[500];
    static int[] snakeY = new int[500];
    static int speedX = 20;
    static int speedY = 0;
    boolean isTmpMove = true;

    public Snake() {
        snakeX[0] = 400;
        snakeY[0] = 400;
        snakeX[1] = 380;
        snakeY[1] = 400;
        snakeX[2] = 360;
        snakeY[2] = 400;
    }

    public void move() {
        //身体跟着头走
        for (int i = length - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        snakeX[0] += speedX;
        snakeY[0] += speedY;
        isTmpMove = true;
        checkBoundaries();
        System.out.format("%d %d\n", snakeX[0], snakeY[0]);
    }

    public void changeDirection(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !direction.equals("down")) {
            direction = "up";
            speedX = 0;
            speedY = -20;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !direction.equals("UP")) {
            direction = "down";
            speedX = 0;
            speedY = 20;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !direction.equals("left")) {
            direction = "right";
            speedX = 20;
            speedY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && !direction.equals("right")) {
            direction = "left";
            speedX = -20;
            speedY = 0;
        }
        isTmpMove = false;
    }

    public void checkBoundaries() {
        if (snakeX[0] > 840) {
            snakeX[0] = 0;
        } else if (snakeX[0] < 0) {
            snakeX[0] = 840;
        } else if (snakeY[0] > 780) {
            snakeY[0] = 0;
        } else if (snakeY[0] < 0) {
            snakeY[0] = 780;
        }
    }

    public void checkEatFood(Food food) {
        if (snakeX[0] == food.normalX && snakeY[0] == food.normalY) {
            food.isEat = true;
            getLonger(food);
        } else if (snakeX[0] == food.bonusX && snakeY[0] == food.bonusY && food.hasBonus) {
            food.isBonusEat = true;
            food.hasBonus = false;
            getLonger(food);
        }
    }

    public void getLonger(Food food) {
        if (food.isEat) {
            Point.getNormalFoodPoint();
            snakeX[length] = snakeX[length - 1] - (snakeX[length - 2] - snakeX[length - 1]);
            snakeY[length] = snakeY[length - 1] - (snakeY[length - 2] - snakeY[length - 1]);
            length++;
        } else if (food.isBonusEat) {
            Point.getBonusFoodPoint();
            snakeX[length] = snakeX[length - 1] - (snakeX[length - 2] - snakeX[length - 1]);
            snakeY[length] = snakeY[length - 1] - (snakeY[length - 2] - snakeY[length - 1]);
            length++;
            food.isBonusEat = false;
        }
    }

    public void checkCollide() {
        for (int i = 1; i < length; i++) {
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
                GamePanel.isDead = true;
                System.out.format("%d %d %d %d\n", snakeX[i], snakeY[i], snakeX[0], snakeY[0]);
                break;
            }
        }
    }
}
