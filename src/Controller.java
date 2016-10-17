import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.net.URL;
import java.util.*;

/**
 * Created by Morten on 01/09/16.
 */
public class Controller implements Initializable {

    @FXML
    private Circle r1_1,
            r2_1, r2_2,
            r3_1, r3_2, r3_3, r3_4,
            r4_1, r4_2, r4_3, r4_4, r4_5, r4_6, r4_7, r4_8,
            r5_1, r5_2, r5_3, r5_4, r5_5, r5_6, r5_7, r5_8,
            r5_9, r5_10, r5_11, r5_12, r5_13, r5_14, r5_15, r5_16;

    @FXML
    private Text r1_1_lbl,
            r2_1_lbl, r2_2_lbl,
            r3_1_lbl, r3_2_lbl, r3_3_lbl, r3_4_lbl,
            r4_1_lbl, r4_2_lbl, r4_3_lbl, r4_4_lbl, r4_5_lbl, r4_6_lbl, r4_7_lbl, r4_8_lbl,
            r5_1_lbl, r5_2_lbl, r5_3_lbl, r5_4_lbl, r5_5_lbl, r5_6_lbl, r5_7_lbl, r5_8_lbl,
            r5_9_lbl, r5_10_lbl, r5_11_lbl, r5_12_lbl, r5_13_lbl, r5_14_lbl, r5_15_lbl, r5_16_lbl;

    @FXML
    private Line l2_1, l2_2,
            l3_1, l3_2, l3_3, l3_4,
            l4_1, l4_2, l4_3, l4_4, l4_5, l4_6, l4_7, l4_8,
            l5_1, l5_2, l5_3, l5_4, l5_5, l5_6, l5_7, l5_8,
            l5_9, l5_10, l5_11, l5_12, l5_13, l5_14, l5_15, l5_16;

    private Circle[] circles;
    private Text[] texts;
    private Line[] lines;

    private Queue<Integer> traversalOrder = new LinkedList<>();
    private Color highlightColor = Color.valueOf("#ff0000");
    private final int ANIMATION_TIME = 400;

    @FXML
    private Button insert_btn, inorder_btn, preorder_btn, postorder_btn;

    @FXML
    private TextField insert_txt;

    private Tree tree;

    private boolean isAnimating = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Init an empty tree.
        tree = new BinarySearchTree();

        //Read all javafx objects into arrays.
        circles = new Circle[]{
            r1_1,
            r2_1, r2_2,
            r3_1, r3_2, r3_3, r3_4,
            r4_1, r4_2, r4_3, r4_4, r4_5, r4_6, r4_7, r4_8,
            r5_1, r5_2, r5_3, r5_4, r5_5, r5_6, r5_7, r5_8,
            r5_9, r5_10, r5_11, r5_12, r5_13, r5_14, r5_15, r5_16};

        texts = new Text[]{
            r1_1_lbl,
            r2_1_lbl, r2_2_lbl,
            r3_1_lbl, r3_2_lbl, r3_3_lbl, r3_4_lbl,
            r4_1_lbl, r4_2_lbl, r4_3_lbl, r4_4_lbl, r4_5_lbl, r4_6_lbl, r4_7_lbl, r4_8_lbl,
            r5_1_lbl, r5_2_lbl, r5_3_lbl, r5_4_lbl, r5_5_lbl, r5_6_lbl, r5_7_lbl, r5_8_lbl,
            r5_9_lbl, r5_10_lbl, r5_11_lbl, r5_12_lbl, r5_13_lbl, r5_14_lbl, r5_15_lbl, r5_16_lbl};

        lines = new Line[]{
            l2_1, l2_2,
            l3_1, l3_2, l3_3, l3_4,
            l4_1, l4_2, l4_3, l4_4, l4_5, l4_6, l4_7, l4_8,
            l5_1, l5_2, l5_3, l5_4, l5_5, l5_6, l5_7, l5_8,
            l5_9, l5_10, l5_11, l5_12, l5_13, l5_14, l5_15, l5_16};


        //Make tree invisible as default.
        removeTree();

        //Set action listener on insert btn.
        insert_btn.setOnMouseClicked(e -> {
            insertKey();
        });

        insert_txt.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                insertKey();
            }
        });

        inorder_btn.setOnMouseClicked(e -> {
            if (!isAnimating) {
                System.out.print("Inorder traversal: ");
                inorderTraverse(tree.getRoot());
                System.out.println("");
                animateTraverse();
            }
        });

        preorder_btn.setOnMouseClicked(e -> {
            if (!isAnimating) {
                System.out.print("Preorder traversal: ");
                preorderTraverse(tree.getRoot());
                System.out.println("");
                animateTraverse();
            }
        });

        postorder_btn.setOnMouseClicked(e -> {
            if (!isAnimating) {
                System.out.print("Postorder traversal: ");
                postorderTraverse(tree.getRoot());
                System.out.println("");
                animateTraverse();
            }
        });

    }

    private void animateTraverse() {

            isAnimating = true;

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(ANIMATION_TIME),
                    ae -> highlightNode(traversalOrder.poll())));
            timeline.setCycleCount(traversalOrder.size() + 1);
            timeline.setOnFinished(ae -> endAnimation());
            timeline.play();

    }

    private void highlightNode(Integer i ) {
        if (i != null) {

            StrokeTransition strokeTransition = new StrokeTransition(
                    Duration.millis(ANIMATION_TIME),
                    circles[i],
                    Color.BLACK,
                    highlightColor
            );
            strokeTransition.play();
        }
    }

    private void endAnimation() {
        for (Circle circle: circles) {

            StrokeTransition strokeTransition = new StrokeTransition(
                    Duration.millis(ANIMATION_TIME),
                    circle,
                    highlightColor,
                    Color.BLACK
            );
            strokeTransition.play();
        }

        isAnimating = false;
    }


    /**
     * Responsible for calling the insert function in the Tree-class and then redrawing.
     */
    private void insertKey() {
        try {
            int key = Integer.parseInt(insert_txt.getText());
            tree.insert(key);
            insert_txt.setText("");
            drawTree();

        } catch (ValueException | HeightException e) {
            displayError(e.getMessage());
        } catch (NumberFormatException e) {
            displayError("You can only input numbers for now!");
        }
    }

    /**
     * Displays a warning box.
     * @param msg
     */
    private void displayError(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    /**
     * Sets everything from the tree invisible
     */
    private void removeTree() {
        for (Circle circle: circles) {
            circle.setVisible(false);
        }

        for (Text text: texts) {
            text.setVisible(false);
        }

        for (Line line: lines) {
            line.setVisible(false);
        }
    }


    /**
     * Draws the entire tree, by using a sort of BFS.
     */
    private void drawTree() {

        if (tree.getRoot() == null) return;

        //Init queue
        Node rootNode = tree.getRoot();
        NodeContainer rootContainer = new NodeContainer(rootNode, 0, 0);
        Queue<NodeContainer> queue = new LinkedList<NodeContainer>();
        queue.add(rootContainer);


        while (!queue.isEmpty()) {
            //Pull the next node in line
            NodeContainer curr = queue.poll();

            //Draw current
            setCircle(curr.getListPos(), curr.node.getKey(), curr.node.getType());

            //Add the left and right child to the queue.
            if (curr.node.getLeft() != null) queue.add(
                    new NodeContainer(curr.node.getLeft(), curr.getLeftPos().x, curr.getLeftPos().y));

            if (curr.node.getRight() != null) queue.add(
                    new NodeContainer(curr.node.getRight(), curr.getRightPos().x, curr.getRightPos().y));


        }
    }

    /**
     * Makes the circle at a given position appear with the right key and color.
     * @param pos
     * @param key
     * @param type
     */
    private void setCircle(int pos, int key, int type) {
        circles[pos].setVisible(true);
        if (type == 1) circles[pos].setFill(Paint.valueOf("#FF0000"));
        texts[pos].setVisible(true);
        texts[pos].setText(key + "");

        //Draw lines. This will be -1 because the first circle hasn't got one.
        if (pos > 0) {
            lines[pos - 1].setVisible(true);
        }

        //Set action listener on circles
        circles[pos].setOnMouseClicked(e -> {
            delete(key);
        });

        texts[pos].setOnMouseClicked(e -> {
            delete(key);
        });
    }

    private void delete(int key) {

        tree.delete(key);

        //Somethings going away, so redraw the entire tree.
        removeTree();
        drawTree();
    }


    private void inorderTraverse(Node node) {
        if (node == null) {
            return;
        }

        inorderTraverse(node.getLeft());

        addNodeToTraversalList(node);

        inorderTraverse(node.getRight());
    }

    private void preorderTraverse(Node node) {
        if (node == null) {
            return;
        }

        addNodeToTraversalList(node);

        preorderTraverse(node.getLeft());

        preorderTraverse(node.getRight());
    }

    private void postorderTraverse(Node node) {
        if (node == null) {
            return;
        }

        postorderTraverse(node.getLeft());

        postorderTraverse(node.getRight());

        addNodeToTraversalList(node);
    }


    private void addNodeToTraversalList(Node node) {
        System.out.print(node.getKey() + " ");
        for (int i = 0; i< texts.length; i++) {
            if (texts[i].getText().equalsIgnoreCase(node.getKey() +"")) {
                traversalOrder.add(i);
            }
        }
    }

}
