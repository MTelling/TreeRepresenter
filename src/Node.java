/**
 * Created by Morten on 01/09/16.
 */
public class Node {

    private int key;
    private int type;
    private Node left = null;
    private Node right = null;
    private Node parent = null;

    public Node(Node parent, int key, int type) {
        this.parent = parent;
        this.key = key;
        this.type = type;
    }


    public int getKey() {
        return key;
    }

    public int getType() {
        return type;
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) { this.parent = parent; }
}
