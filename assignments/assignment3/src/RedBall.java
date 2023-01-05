import java.awt.*;
import java.util.Random;


public class RedBall extends Ball{
    Random random = new Random();
    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    public void update(char keyChar){
        switch (keyChar) {
            case 'a':
                this.setXSpeed(-random.nextInt(3) - 1);
                break;
            case 'd':
                this.setXSpeed(random.nextInt(3) + 1);
                break;
            case 'w':
                this.setYSpeed(-random.nextInt(3) - 1);
                break;
            case 's':
                this.setYSpeed(random.nextInt(3) + 1);
        }
    }

    @Override
    public void updateVisitable(boolean flag) {
        this.setVisible(flag);
    }

}
