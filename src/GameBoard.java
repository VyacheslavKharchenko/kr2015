import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel{
    private Square field [][];
    private Snake snake;
    private SnakeMovingThread snakeMovingThread;
    private Point currentTailCoordinate;
    private Apple apple;
    private JPanel panelWithCounterAndSpeedMeter;
    private SizeCounterLabel sizeCounterLabel;
    private SpeedMeter speedMeter;

    public GameBoard () {
        initGameBoard();
        initPanelWithCounterAmdSpeedMeter();
        initSizeCounterLabel();
        initSpeedMeter();
        initField();
        initSnake();
        initApple();
        initSnakeMovingThread();
        initActionListenerForSnake();
    }

    private void initGameBoard(){
        setFocusable(true);
        setLayout(new BorderLayout(1, 1));
        setOpaque(true);
        setBackground(new Color(0x040101));
    }

    private void initPanelWithCounterAmdSpeedMeter(){
        panelWithCounterAndSpeedMeter = new JPanel(new GridLayout(0, 2));
        panelWithCounterAndSpeedMeter.setOpaque(true);
        panelWithCounterAndSpeedMeter.setBackground(new Color(0x928AC8));
        add(panelWithCounterAndSpeedMeter, BorderLayout.NORTH);
    }

    private void initSizeCounterLabel(){
        sizeCounterLabel = new SizeCounterLabel();
        panelWithCounterAndSpeedMeter.add(sizeCounterLabel);
    }

    private void initSpeedMeter(){
        speedMeter = new SpeedMeter();
        panelWithCounterAndSpeedMeter.add(speedMeter);
    }

    private void initField(){
        JPanel panelWithSquares = new JPanel(new GridLayout(FIELD_SIZE, FIELD_SIZE));
        panelWithSquares.setBackground(new Color(23));
        field = new Square[FIELD_SIZE][FIELD_SIZE];

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                Square square = new Square();
                field[i][j] = square;
                panelWithSquares.add(square);
            }
        }

        add(panelWithSquares, BorderLayout.CENTER);
    }

    private void initSnake(){
        snake = new Snake();
        currentTailCoordinate = snake.getTailCoordinate();
    }

    private void initApple(){
        apple = new Apple();
        apple.generateApple(field);
    }

    public void moveSnake(){
        if (snake.isAlive()) {
            repaintSnake();
            currentTailCoordinate = snake.getTailCoordinate();
            checkSnakeIsEatApple();
            speedMeter.parseTimeToSleepToSpeed(snakeMovingThread.calculateTimeToSleep());
        }
        else
            reloadGame();
    }

    private void reloadGame(){
        checkResultIsRecord();
        clearFieldFromDeadSnake();
        snake.resurrectSnake();
    }

    private void checkResultIsRecord(){
        int record = RecordsSaver.loadRecord();

        Object[] options = {"Заново"};
        String informalText = "";

        if (record < snake.getSize()){
            informalText = "Новый рекорд!";
            RecordsSaver.saveRecord(snake.getSize());
        }
        else if (record > snake.getSize())
            informalText = "Вы не побили рекорд.";
        else
            informalText = "Сравняли счеты!";

        informalText += "\nПредыдущая лучшая длинна змейки: " + record + "\n" +
                        "Ваш результат: " + snake.getSize();

        JOptionPane.showOptionDialog(this, informalText,
                "Игра окончена",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    }

    private void repaintSnake(){
        snake.makeMove();
        field[currentTailCoordinate.y][currentTailCoordinate.x].makeNormal();

        for (SnakePart snakePart : snake.getSnakeBody()) {
            int x = snakePart.getPoint().x;
            int y = snakePart.getPoint().y;

            field[y][x].makeBody();
        }

        int x = snake.getHeadCoordinate().x;
        int y = snake.getHeadCoordinate().y;
        field[y][x].makeHead();
    }

    private void checkSnakeIsEatApple(){
        int headX = snake.getHeadCoordinate().x;
        int headY = snake.getHeadCoordinate().y;
        int appleX = apple.getCurrentX();
        int appleY = apple.getCurrentY();

        if (headX == appleX && headY == appleY) {
            snake.eatApple(apple);
            apple.generateApple(field);
            sizeCounterLabel.incrementSnakeSize();
        }
    }

    private void clearFieldFromDeadSnake(){
        for (SnakePart snakePart : snake.getSnakeBody()) {
            int x = snakePart.getPoint().x;
            int y = snakePart.getPoint().y;

            field[y][x].makeNormal();
        }

        sizeCounterLabel.clear();
        speedMeter.clear();
    }

    private void initSnakeMovingThread(){
        snakeMovingThread = new SnakeMovingThread(this);
        snakeMovingThread.start();
    }

    private void initActionListenerForSnake(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.moveRight();
                else
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.moveLeft();
                else
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    snake.moveUp();
                else
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.moveDown();
            }
        });
    }

    public int getSnakeSize(){
        return snake.getSize();
    }

    public static final int FIELD_SIZE = 20;
}