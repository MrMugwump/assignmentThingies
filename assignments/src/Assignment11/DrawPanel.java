package Assignment11;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    boolean[][] board;
    String[][] neighbors;
    boolean gameStart = false;

    public DrawPanel(boolean[][] otherBoard, int[][] otherNeighbors) {
        board = otherBoard;
        neighbors = new String[otherNeighbors.length][otherNeighbors[0].length];
    }
    public void sync(boolean[][] otherBoard){
        board = otherBoard;

    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        double width = this.getWidth()/board[0].length;
        double height = this.getHeight()/board.length;
        System.out.println(this.getHeight());

        //draws the lines. doesn't work perfectly
        for (int i = 0; i < board[0].length; i++) {
            g2d.drawLine((int)(Math.round(i*width)), 0, (int)(Math.round(i*width)), this.getHeight());
        }
        for (int i = 0; i < board.length; i++) {
            g2d.drawLine(0, (int)(Math.round(i*height)), this.getWidth(), (int)(Math.round(i*height)));
        }
        //checks the booleans, changes the color
        for (int column = 0; column < board.length; column++) {
            for (int row = 0; row < board[0].length; row++) {

                if (board[row][column]){
                    g2d.setColor(Color.blue);
                    g2d.fillRect( (int)Math.round(row*width)+1,
                            (int)Math.round(column*height)+1,
                            (int)width-1,
                            (int)height-1);
                }
                else{
                    g2d.setColor(Color.white);
                    g2d.fillRect( ((int)Math.round(row*width)+1),
                            (int)Math.round(column*height)+1,
                            (int)width-1,
                            (int)height-1);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
