import javax.swing.*;
import java.awt.*;

public class Square extends JLabel{
    public static final Color NORMAL_COLOR = new Color(0x00A909);
    public static final Color APPLE_COLOR = new Color(0xFF1713);
    public static final Color HEAD_COLOR = new Color(0x60FF57);
    public static final Color SNAKE_PART_COLOR = new Color(0xD2F122);

    private SquareState squareState;

    public Square(){
        initSquare();
        makeNormal();
    }

    private void initSquare() {
        setOpaque(true); // Виставити "непрозорість"
    }

    public void makeHead(){
        setBackground(HEAD_COLOR);
        squareState = SquareState.HEAD;
    }

    public void makeApple(){
        setBackground(APPLE_COLOR);
        squareState = SquareState.APPLE;
    }

    public void makeNormal(){
        setBackground(NORMAL_COLOR);
        squareState = SquareState.NORMAL;
    }

    public void makeBody(){
        setBackground(SNAKE_PART_COLOR);
        squareState = SquareState.SNAKE_PART;
    }

    public SquareState getSquareState(){
        return squareState;
    }

    public enum SquareState{
        NORMAL, HEAD, SNAKE_PART, APPLE
    }
}