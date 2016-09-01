import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Morten on 01/09/16.
 */
public class Tree {

    private Node root = null;


    public Tree() {
    }

    public void delete(int key) {

    }

    public void insert(int key, int type) throws ValueException, HeightException {
        if (root == null) {
            root = new Node(key, type);
        } else {

            Node current = root;

            //Don't allow two identical keys.
            if (current.getKey() == key) {
                throw new ValueException("Key exists!");
            }

            int height = 0;
            boolean placed = false;
            while (!placed) {

                //Throw error if height exceeds 5.
                if (height > 3) throw new HeightException("Too high!");

                //Check if the node should be placed to the right or left.
                if (current.getKey() < key) {

                    //If arrived at empty node, place current node there.
                    if (current.getRight() == null) {
                        current.setRight(new Node(key, type));
                        placed = true;
                    } else { //Else traverse further down.
                        current = current.getRight();
                    }

                } else if (current.getKey() > key){

                    if (current.getLeft() == null) {
                        current.setLeft(new Node(key, type));
                        placed = true;
                    } else {
                        current = current.getLeft();
                    }
                }

                height++;
            }
        }
    }

    public Node getRoot() {
        return root;
    }
}
