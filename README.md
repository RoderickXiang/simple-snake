<!-- TOC -->

- [一切先从核心玩法开始](#一切先从核心玩法开始)
    - [如何运动](#如何运动)
    - [控制蛇头的方向](#控制蛇头的方向)
- [游戏界面](#游戏界面)
    - [绘制游戏内容](#绘制游戏内容)
    - [让一切都动起来](#让一切都动起来)
    - [获取键盘事件](#获取键盘事件)
- [游戏功能实现](#游戏功能实现)
    - [穿墙效果](#穿墙效果)
    - [蛇的身子变长（重点）](#蛇的身子变长重点)
    - [碰撞检测](#碰撞检测)
    - [检测吃食物](#检测吃食物)
    - [计分系统](#计分系统)

<!-- /TOC -->
### 一切先从核心玩法开始
首先贪吃蛇的运动是发生在一个规范的矩阵中，而矩阵中的每一方格就是蛇的身子，而蛇的运动就是一次一次更新正方形的位置，然后通过panel绘制上去<br>
所以我们使用两个数组来储存贪吃蛇的位置,snakeX用来储存横坐标，而snakeY用来储存纵坐标的位置<br>
```java
//贪吃蛇初始的位置（一个头，两个身子）
public Snake() {
        snakeX[0] = 400;
        snakeY[0] = 400;
        snakeX[1] = 380;
        snakeY[1] = 400;
        snakeX[2] = 360;
        snakeY[2] = 400;
    }
```
#### 如何运动
我们只要更新蛇的位置就能让它动起来了，所以我们定义两个速度"speedX"和"speedY"用来控制头的位置<br>
只动了头并没有用，还有身子，我们只需要用从尾巴开始使用循环继承上一个身体的位置，知道继承头部位置<br>
```java
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
```
#### 控制蛇头的方向
为了跟好的控制小蛇，直接控制蛇头来的最为直接。但是这里有一个会产生BUG的点，蛇头不能做反方向运动，所以我们还要给蛇头添加一个方向"direction"属性，只有当按键按下和方向条件满足时才能改变蛇头的运动方向<br>
```java
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
```
### 游戏界面
游戏界面我们使用Jframe来实现（我好方便实现关闭功能）<br>
写一个类类继承Jframe，在里面添加面板并且进行初始化设置<br>
```java
public class MyFrame extends JFrame {
    public void init() {
        JFrame frame = new JFrame();
        setTitle("贪吃蛇");
        setResizable(false);
        setBounds(300,100,865,830); //840 *780 （界面的大小一定要设置正确）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //添加Panel
        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);
    }
}
```
#### 绘制游戏内容
已经添加了JPanal（GamePanel）,所以到Jpanal中添加游戏中需要绘制的内容<br>
在Jpanal中重写paintComponent就能在实例化Jpanal时使用画笔绘制图像（方法自行被调用），这个类也是作为游戏的主类<br>
```java
class MyPaint extends JPanel {}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏
        g.setColor(Color.BLACK);
        g.fillRect(100,100,100,100);
        //绘制所有的功能，省略不写......
    }
}
```
所以有这个就能画小蛇了！<br>
#### 让一切都动起来
为了有运动的效果，我们使用循环实现，这里我们使用ActionListener（函数式接口）用作定时器，我们实现一下接口里面的actionPerformed方法在方法里写需要定时执行的事件。定时器使用timer.start()开启<br>
```java
//执行定时操作
@Override
public void actionPerformed(ActionEvent e) {
    snake.move();
    snake.checkEatFood(food);
    food.changeFoodPosition();  //更改食物的位置
    snake.checkCollide();   //碰撞检测
    repaint();  //重新绘制图像
}
```
#### 获取键盘事件
实现KeyListener接口来进行事件监听<br>
使用之间还要设置监听<br>
```java
JPanal.setFocusable(true); //设置监听焦点
Jpanal.addKeyListener(this);   //添加键盘监听事件
接口中有三个抽象方法<br>
```
```java
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
```
### 游戏功能实现
#### 穿墙效果
```java
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
```
#### 蛇的身子变长（重点）
我们让小蛇的尾部变长而不是头部，所以要参考蛇身最后的参数，并且获取速度<br>
速度使用前坐标减后左边产生的差值得到<br>
增加的位置通过蛇尾减速度得到<br>
```java
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
```
#### 碰撞检测
遍历除了蛇头以外的位置,当坐标有相同的时候停止游戏<br>
```java
public void checkCollide() {
    for (int i = 1; i < length; i++) {
        if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
            GamePanel.isDead = true;
            System.out.format("%d %d %d %d\n", snakeX[i], snakeY[i], snakeX[0], snakeY[0]);
            break;
        }
    }
}
```
#### 检测吃食物
判断头部位置是否与食物重叠就行<br>
特殊食物还需要判断下是否存在<br>
```java
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
```
#### 计分系统
使用BufferReader和BufferWriter读写记录，获取最高分<br>
```java
public static void writeResult() throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter("记录.txt", true));
    bw.write("player" + "\t" + point);
    bw.newLine();
    bw.close();
}
public static void readBestResult() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("记录.txt"));
    String line;
    int score;
    while ((line = br.readLine()) != null) {
        String[] tmp = line.split("\t");
        score = Integer.parseInt(tmp[1]);
        if (score > bestScore) {
            bestScore = score;
        }
    }
}
```
