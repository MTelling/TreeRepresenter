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

    public void delete(int searchKey) {
        Node current = root;

        while (current != null) {

            int currentKey = current.getKey();

            if (currentKey == searchKey) {
                //There are three cases:

                //CASE 1: Node to be deleted is a leaf.
                if (current.getLeft() == null && current.getRight() == null) {

                    if (current == root) {
                        root = null;
                    } else { //If it's not the root.
                        swap(current, null);
                        current.setParent(null);
                    }
                }

                //CASE 2: Node to be deleted has single child.
                if ((current.getLeft() == null && current.getRight() != null)
                        || (current.getLeft() != null && current.getRight() == null)) {

                    Node child = null;

                    if (current.getLeft() != null) {
                        child = current.getLeft();
                    } else {
                        child = current.getRight();
                    }

                    //Set the parent of the child to the parent of the current node.
                    child.setParent(current.getParent());
                    swap(current, child);

                }

                //CASE 3: Node has two children.




                current = null;

            } else if (currentKey > searchKey) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }


        }
    }

    private void swap(Node old, Node other) {
        //If current was smaller than its parent, it must have been to the left.
        if (old.getParent().getKey() > old.getKey()) {
            old.getParent().setLeft(other);
        } else { //Else must have been to the right.
            old.getParent().setRight(other);
        }
    }

    public void insert(int key, int type) throws ValueException, HeightException {
        if (root == null) {
            root = new Node(null, key, type);
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
                        current.setRight(new Node(current, key, type));
                        placed = true;
                    } else { //Else traverse further down.
                        current = current.getRight();
                    }

                } else if (current.getKey() > key){

                    if (current.getLeft() == null) {
                        current.setLeft(new Node(current, key, type));
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
