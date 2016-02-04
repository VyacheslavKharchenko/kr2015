public class SnakeMovingThread implements Runnable{
    private Thread t;
    private volatile boolean isPause, isExit;
    private GameBoard gameBoard;

    public SnakeMovingThread(GameBoard gameBoard){
        this.t = new Thread(this);
        this.gameBoard = gameBoard;
        pause();
        t.start();
    }

    @Override
    public void run() {
        while (!isExit){
            checkPause();
            gameBoard.moveSnake();

            try {
                long timeToSleep = calculateTimeToSleep();
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void checkPause(){
        while (isPause)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public long calculateTimeToSleep(){
        long timeToSleep = DEFOULT_TIME_TO_SLEEP - 15*gameBoard.getSnakeSize();

        if (timeToSleep < 100)
            timeToSleep = 100;

        return timeToSleep;
    }

    public void pause(){
        isPause = true;
    }

    public synchronized void resume(){
        isPause = false;
        notify();
    }

    public void start(){
        resume();
    }

    public static final long DEFOULT_TIME_TO_SLEEP = 500;
}