import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WhiteBallRandom extends Ball implements Subject<Ball>{
    List<Ball> observers = new ArrayList<>();

    public WhiteBallRandom(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {

    }

    @Override
    public void updateVisitable(boolean flag) {

    }

    public void registerObserver(Ball ball) {
        observers.add(ball);
    }

    public void removeObserver() {
        observers = new ArrayList<>();
    }

    @Override
    public void notifyObservers(char keyChar) {

    }

    public void notifyObservers() {
        for (Ball ball : observers){
            if (isIntersect(this,ball))
                ball.updateVisitable(true);
            else
                ball.setVisible(false);
        }
    }

    public boolean isIntersect(Ball a, Ball b) {
        int dis = (a.getWidth() + b.getWidth()) / 2;
        double diffXPow = Math.pow(a.convertToCentralX() - b.convertToCentralX(), 2);
        double diffYPow = Math.pow(a.convertToCentralY() - b.convertToCentralY(), 2);
        return diffXPow + diffYPow < dis * dis;
    }
}
