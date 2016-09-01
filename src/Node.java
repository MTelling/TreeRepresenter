/**
 * Created by Morten on 01/09/16.
 */
public class Node {

    private int key;
    private int type;
    private Node left = null;
    private Node right = null;

    public Node(int key, int type) {
        this.key = key;
        this.type = type;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
