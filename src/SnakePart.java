import java.awt.*;

public class SnakePart {
    private Point point;
    private SnakePartStateState partSnakePartStateState;

    public SnakePart(Point startPoint, SnakePartStateState partSnakePartStateState){
        this.point = startPoint;
        this.partSnakePartStateState = partSnakePartStateState;
    }

    public SnakePart(Point startPoint){
        this(startPoint, SnakePartStateState.BODY);
    }

    public SnakePart(int x, int y){
        this(new Point(x, y));
    }

    public void makeHead(){
        partSnakePartStateState = SnakePartStateState.HEAD;
    }

    public void makeBody(){
        partSnakePartStateState = SnakePartStateState.BODY;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public Point getPoint(){
        return point;
    }

    public SnakePart clone(){
        return new SnakePart(point);
    }

    public enum SnakePartStateState {
        HEAD, BODY;
    }
}