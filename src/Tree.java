import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Morten on 01/09/16.
 */
public abstract class Tree {

    protected Node root = null;

    public Node getRoot() {
        return root;
    }

    public abstract void insert(int key) throws HeightException, ValueException;
    public abstract void delete(int key);

    public abstract Node successor(Node node);
    public abstract Node predecessor(Node node);


}
