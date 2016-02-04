import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private GameBoard gameBoard;

    public MainFrame(){
        initMainFrame();
        initGameBoard();
    }

    private void initMainFrame(){
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(665, 670);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Змейка минимализм. Ничего лишнего.");
    }

    private void initGameBoard(){
        gameBoard = new GameBoard();
        add(gameBoard, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}