import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.net.URL;
import java.util.*;

/**
 * Created by Morten on 01/09/16.
 */
public class Controller implements Initializable {

    public Circle r1_1,
            r2_1, r2_2,
            r3_1, r3_2, r3_3, r3_4,
            r4_1, r4_2, r4_3, r4_4, r4_5, r4_6, r4_7, r4_8,
            r5_1, r5_2, r5_3, r5_4, r5_5, r5_6, r5_7, r5_8,
            r5_9, r5_10, r5_11, r5_12, r5_13, r5_14, r5_15, r5_16;

    public Text r1_1_lbl,
            r2_1_lbl, r2_2_lbl,
            r3_1_lbl, r3_2_lbl, r3_3_lbl, r3_4_lbl,
            r4_1_lbl, r4_2_lbl, r4_3_lbl, r4_4_lbl, r4_5_lbl, r4_6_lbl, r4_7_lbl, r4_8_lbl,
            r5_1_lbl, r5_2_lbl, r5_3_lbl, r5_4_lbl, r5_5_lbl, r5_6_lbl, r5_7_lbl, r5_8_lbl,
            r5_9_lbl, r5_10_lbl, r5_11_lbl, r5_12_lbl, r5_13_lbl, r5_14_lbl, r5_15_lbl, r5_16_lbl;

    public Line l2_1, l2_2,
            l3_1, l3_2, l3_3, l3_4,
            l4_1, l4_2, l4_3, l4_4, l4_5, l4_6, l4_7, l4_8,
            l5_1, l5_2, l5_3, l5_4, l5_5, l5_6, l5_7, l5_8,
            l5_9, l5_10, l5_11, l5_12, l5_13, l5_14, l5_15, l5_16;

    private Circle[] circles;
    private Text[] texts;
    private Line[] lines;

    public Button insert_btn;
    public TextField insert_txt;

    private Tree tree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Init an empty tree.
        tree = new Tree();

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


    }

    private void insertKey() {
        int key = Integer.parseInt(insert_txt.getText());
        insert_txt.setText("");


        try {
            tree.insert(key, 0);
        } catch (ValueException | HeightException ex) {
            System.out.println(ex.getMessage());
        }

        drawTree();
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

    private void setCircle(int pos, int key, int type) {
        circles[pos].setVisible(true);
        if (type == 1) circles[pos].setFill(Paint.valueOf("#FF0000"));
        texts[pos].setVisible(true);
        texts[pos].setText(key + "");

        //Draw lines. This will be -1 because the first circle hasn't got one.
        if (pos > 0) {
            lines[pos - 1].setVisible(true);
        }
    }
}