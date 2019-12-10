package Assignment11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GameOfLife implements MouseListener, ActionListener, Runnable {
    //boolean[][] board = new boolean[10][10];
    private boolean[][] board = new boolean[20][20];
    private int[][] neighbors = new int[21][21];
    private JFrame ex = new JFrame();
    private DrawPanel panel = new DrawPanel(board, neighbors);
    private boolean gameStart = false;
    private JButton start = new JButton("Start");
    private JButton stop = new JButton("Stop");
    private JButton step = new JButton("Step");
    private Container bottomButtons = new Container();
    public static void main(String[] args) {
        /*
        EventQueue.invokeLater(() -> {
            GameOfLife ex = new GameOfLife();
            ex.setVisible(true);
        });
        */
        new GameOfLife();
    }
    private GameOfLife() {
        JPanel drawPanel = new JPanel();
        ex.add(drawPanel);

        ex.setSize(600, 600);
        ex.setLayout(new BorderLayout());
        ex.setTitle("Points");
        bottomButtons.setLayout( new GridLayout(1, 3));
        bottomButtons.add(step);
        bottomButtons.add(start);
        bottomButtons.add(stop);
        step.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
        ex.add(bottomButtons,BorderLayout.NORTH);
        ex.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);
        ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ex.setVisible(true);
        boardSetup();
        initUI();
    }

    private void initUI() {


    }
    public void boardSetup(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = false;
            }
        }
    }
    public void onClick(int x, int y){
        //These next 4 lines calculate what row you are clicking
        double width = panel.getWidth()/board[0].length;
        int column = Math.min(board[0].length-1,(int)(x/width));
        double height = panel.getHeight()/board.length;
        int row = Math.min(board.length-1,(int)(y/height));
        //changes the square
        board[column][row] = !board[column][row];
        System.out.println(column);
        System.out.println(row);

        //This sends info to the draw panel class
        panel.sync(board);
        ex.repaint();
    }
    public void game(){

        checkNeighbors();
        //this checks the rules, changes accordingly
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j]){
                    if (neighbors[i][j] < 2){
                        board[i][j] = !board[i][j];
                    }
                    else if (neighbors[i][j] > 3){
                        board[i][j] = !board[i][j];
                    }
                }
                else if (!board[i][j]){
                    if (neighbors[i][j] == 3){
                        board[i][j] = !board[i][j];
                    }
                }
            }
        }
        //sends info
        panel.sync(board);
        ex.repaint();
    }
    public void checkNeighbors(){
        clearNeighbors();
        //checks each the blocks surrounding each square.
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[0].length; j++) {
                if (board[i][j]){
                    neighbors[i+1][j+1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                    neighbors[i+1][j] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
                    neighbors[i+1][j-1] ++;
                    /*
                    OOO
                    OOO
                    XOO
                     */
                    neighbors[i][j-1] ++;
                    /*
                    OOO
                    XOO
                    OOO
                     */
                    neighbors[i][j+1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
                    neighbors[i-1][j-1] ++;
                    /*
                    XOO
                    OOO
                    OOO
                     */
                    neighbors[i-1][j] ++;
                    /*
                    OXO
                    OOO
                    OOO
                     */
                    neighbors[i-1][j+1] ++;
                    /*
                    OOX
                    OOO
                    OOO
                     */
                }
            }
        }
        //this checks the squares on the boarder, lazy solution to get rid of an error
        for (int i = 1; i < board.length; i++) {
            if (board[0][i]){
                neighbors[1][i+1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                neighbors[1][i] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
                neighbors[1][i-1] ++;
                    /*
                    OOO
                    OOO
                    XOO
                     */
                neighbors[0][i-1] ++;
                    /*
                    OOO
                    XOO
                    OOO
                     */
                neighbors[0][i+1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
            }
            if (board[i][0]){
                neighbors[i-1][0] ++;
                    /*
                    OXO
                    OOO
                    OOO
                     */
                neighbors[i-1][1] ++;
                    /*
                    OOX
                    OOO
                    OOO
                     */
                neighbors[i][1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
                neighbors[i+1][1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                neighbors[i+1][0] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */

            }
        }
        if (board[0][0]){
            neighbors[0][1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
            neighbors[1][1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
            neighbors[1][0] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
        }

    }
    private void printNeighbors(){
        //prints the neighbors. for some reason doesn't work. Works if board is 10 by 10
        for (int i = 0; i < neighbors.length-1; i++) {
            for (int j = 0; j < neighbors[0].length-1; j++) {
                System.out.print(neighbors[i][j] + " ");
            }
            System.out.println();
        }
    }
    private void clearNeighbors(){
        //resets neighbors, so the board doesnt count neighbors twice.
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors.length; j++) {
                neighbors[i][j] = 0;
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        //checks where mouse was clicked, sends that info
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();
            System.out.println("("+x+","+y+")");
            onClick(x,y);
            printNeighbors();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if  (e.getSource() == start) {
            gameStart = true;
            Thread t = new Thread(this);
            t.start();
        }
        else if (e.getSource() == stop){
            gameStart = false;
        }
        if (e.getSource() == step){
            game();
        }
    }

    @Override
    public void run() {
        while (gameStart){
            game();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}