import java.awt.*;

/**
 * Created by Morten on 01/09/16.
 */
public class NodeContainer {

    Node node;

    Point pos;

    public NodeContainer(Node node, int row, int rowNum) {
        this.node = node;
        pos = new Point(row, rowNum);
    }

    public int getListPos() {
        return ((int) Math.pow(2, pos.x)) + pos.y - 1;
    }

    public Point getLeftPos() {
        return new Point(pos.x + 1, pos.y * 2);
    }

    public Point getRightPos() {
        return new Point(pos.x + 1, pos.y * 2 + 1);
    }
}
