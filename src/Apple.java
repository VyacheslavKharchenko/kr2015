import java.awt.*;
import java.util.Random;

public class Apple {
    private Point currentCoordinate;
    private static Random random = new Random();

    public void generateApple(Square [][] gameField){
        int maxCoordinate = gameField.length;

        int x, y;
        do {
            x = random.nextInt(maxCoordinate);
            y = random.nextInt(maxCoordinate);
        } while (gameField[y][x].getSquareState() != Square.SquareState.NORMAL);

        currentCoordinate = new Point(x, y);
        gameField[y][x].makeApple();
    }

    public int getCurrentX(){
        return currentCoordinate.x;
    }

    public int getCurrentY(){
        return currentCoordinate.y;
    }
}