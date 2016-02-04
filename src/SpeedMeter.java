import javax.swing.*;
import java.awt.*;

public class SpeedMeter extends JLabel{
    public SpeedMeter(){
        initSpeedMeter();
        clear();
    }

    private void initSpeedMeter(){
        setOpaque(false);
        setForeground(new Color(0xFFFFFF));
        setFont(new Font("Segoe Print", Font.PLAIN, 19));
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    public void clear(){
        setText("Скорость: 0 квадратов/сек");
    }

    public void parseTimeToSleepToSpeed(long timeToSleep) {
        double speed = (1000/(double)timeToSleep);
        String speedString = Double.toString(speed);

        try {
            speedString = speedString.substring(0, speedString.indexOf(".") + 3);
        }catch (StringIndexOutOfBoundsException ignored){}

        setText("Скорость: " + speedString + " квадратов/сек");
    }
}