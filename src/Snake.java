import java.awt.*;
import java.util.*;

public class Snake {
    private LinkedList <SnakePart> snakeList;
    private SnakeState currentState, newState;

    public Snake() {
        snakeList = new LinkedList<>();
        resurrectSnake();
    }

    public void resurrectSnake(){
        snakeList.clear();

        for (int i = 0; i < START_SNAKE_SIZE; i++)
            snakeList.addLast(new SnakePart(new Point(START_POINT.x - i, START_POINT.y)));

        snakeList.getFirst().makeHead();
        currentState = SnakeState.MOVE_DOWN;
        newState = currentState;
    }

    public void moveUp(){
        if (currentState != SnakeState.MOVE_DOWN)
            newState = SnakeState.MOVE_UP;
    }

    public void moveDown(){
        if (currentState != SnakeState.MOVE_UP)
            newState = SnakeState.MOVE_DOWN;
    }

    public void moveLeft(){
        if (currentState != SnakeState.MOVE_RIGHT)
            newState = SnakeState.MOVE_LEFT;
    }

    public void moveRight(){
        if (currentState != SnakeState.MOVE_LEFT)
            newState = SnakeState.MOVE_RIGHT;
    }

    public void eatApple(Apple apple){
        SnakePart snakePart = new SnakePart(apple.getCurrentX(), apple.getCurrentY());
        snakePart.makeHead();
        snakeList.getFirst().makeBody();
        snakeList.addFirst(snakePart);
    }

    public void makeMove(){
        checkSnackIsEatHerself();
        if (currentState != SnakeState.DEAD) {
            currentState = newState;
            switch (currentState) {
                case MOVE_DOWN:
                    changeTailCoordinate(0, 1);
                    break;
                case MOVE_UP:
                    changeTailCoordinate(0, -1);
                    break;
                case MOVE_LEFT:
                    changeTailCoordinate(-1, 0);
                    break;
                case MOVE_RIGHT:
                    changeTailCoordinate(1, 0);
                    break;
            }
        }
    }

    private void changeTailCoordinate(int appendX, int appendY){
        Point headCoordinate = snakeList.getFirst().getPoint();

        int x = (headCoordinate.x + appendX) % GameBoard.FIELD_SIZE;
        int y = (headCoordinate.y + appendY) % GameBoard.FIELD_SIZE;

        if (x < 0)
            x = GameBoard.FIELD_SIZE + x;

        if (y < 0)
            y = GameBoard.FIELD_SIZE + y;

        SnakePart tail = snakeList.getLast();
        tail.setPoint(new Point(x, y));

        makeTailHead();
    }

    private void makeTailHead(){
        SnakePart tail = snakeList.getLast();
        snakeList.removeLast();
        snakeList.getFirst().makeBody();
        tail.makeHead();
        snakeList.addFirst(tail);
    }

    private void checkSnackIsEatHerself(){
        int headX = snakeList.getFirst().getPoint().x;
        int headY = snakeList.getFirst().getPoint().y;

        for (int i = 2; i < snakeList.size(); i++) {
            int bodyX = snakeList.get(i).getPoint().x;
            int bodyY = snakeList.get(i).getPoint().y;

            if (bodyX == headX && bodyY == headY) {
                currentState = SnakeState.DEAD;
                break;
            }
        }
    }

    public LinkedList <SnakePart> getSnakeBody(){
        return snakeList;
    }

    public Point getTailCoordinate(){
        return snakeList.getLast().getPoint();
    }

    public Point getHeadCoordinate(){
        return snakeList.getFirst().getPoint();
    }

    public boolean isAlive(){
        return currentState != SnakeState.DEAD;
    }

    public enum SnakeState{
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, DEAD
    }

    public int getSize(){
        return snakeList.size();
    }

    public static final Point START_POINT = new Point(GameBoard.FIELD_SIZE/2, GameBoard.FIELD_SIZE/2);
    public static final int START_SNAKE_SIZE = 3;
}