import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Food {
    Random rd = new Random();
    private int tmpX = rd.nextInt(840);
    private int tmpY = rd.nextInt(780);
    int normalX = tmpX - tmpX % 20;
    int normalY = tmpY - tmpY % 20;
    int bonusX;
    int bonusY;
    boolean isEat = false;
    boolean hasBonus = false;
    boolean isBonusEat = false;
    Timer foodTimer = new Timer();  //定时器
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            createBonus();
            hasBonus = !hasBonus; //过一段时间会消失
        }
    };  //定时器任务

    public void changeFoodPosition() {
        if (isEat) {
            tmpX = rd.nextInt(840);
            tmpY = rd.nextInt(780);
            normalX = tmpX - tmpX % 20;
            normalY = tmpY - tmpY % 20;
            isEat = false;
        }
    }

    public void createBonus() {
        int tmpX = rd.nextInt(840);
        int tmpY = rd.nextInt(780);
        bonusX = tmpX - tmpX % 20;
        bonusY = tmpY - tmpY % 20;
    }

    public void startTimer() {
        foodTimer.scheduleAtFixedRate(task, 10000, 10000);
    }

}
