package Assignment12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper implements ActionListener/*, MouseListener */{
    private JFrame board = new JFrame("Minesweeper");
    private JTextField setBombNumber = new JTextField("Set Bomb Number");
    private JTextField setBoardDimensionsX = new JTextField("how many rows");
    private JTextField setBoardDimensionsY = new JTextField("how many columns");
    private JButton bombNumberButton = new JButton("Commit entry");
    private JButton boardDimensionsYButton = new JButton("Commit entry");
    private JButton boardDimensionsXButton = new JButton("Commit entry");
    private JButton startGameButton = new JButton("Start Game?");
    private JDialog winner = new JDialog();
    private JDialog loser = new JDialog();
    private Container topArea = new Container();
    private Container bottomArea = new Container();
    private JButton[][] mineField;
    private Container field = new Container();
    private Computation comp = new Computation();
    private RandomMines bombPlace = new RandomMines();
    private int[][] neighbors;
    private int numOfRows = 10;
    private int numOfColumns = 10;
    private int numOfBombs = 3;
    private int[] whichMine = new int[2];
    public static void main(String[] args) {
        new Minesweeper();
    }
    private Minesweeper(){
        //board.setLayout(new GridLayout(3,1));
        beginGame();
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setVisible(true);
        winner.setSize(200,200);
        loser.setSize(200,200);


        //initUI();
        //numberPrint();
    }
    private void beginGame(){
        board.remove(field);

        board.setSize(1000,1000);
        board.setLayout(new GridLayout(3,1));
        topArea.setLayout(new GridLayout(2,1));
        topArea.add(setBombNumber);
        topArea.add(bombNumberButton);
        board.add(topArea);
        bottomArea.setLayout(new GridLayout(2,2));
        setBoardDimensionsX.setHorizontalAlignment(JTextField.CENTER);
        setBoardDimensionsY.setHorizontalAlignment(JTextField.CENTER);
        setBombNumber.setHorizontalAlignment(JTextField.CENTER);
        bottomArea.add(setBoardDimensionsX);
        bottomArea.add(setBoardDimensionsY);
        bottomArea.add(boardDimensionsXButton);
        bottomArea.add(boardDimensionsYButton);
        //bottomArea.add(setBoardDimensions[1]);
        board.add(bottomArea);
        board.add(startGameButton);
        bombNumberButton.addActionListener(this);
        boardDimensionsXButton.addActionListener(this);
        boardDimensionsYButton.addActionListener(this);
        startGameButton.addActionListener(this);
    }
    private void initUI(){
        mineField = new JButton[numOfRows][numOfColumns];
        //System.out.println("Rows "+numOfRows);
        //System.out.println("Columns "+numOfColumns);
        boolean[][] settingBoard = new boolean[numOfRows][numOfColumns];
        comp.setBoard(settingBoard);
        bombPlace.setMineField(settingBoard);
        board.remove(topArea);
        board.remove(bottomArea);
        board.remove(startGameButton);
        board.setLayout(new BorderLayout());
        field.setLayout(new GridLayout(numOfRows,numOfColumns));

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j] = new JButton();
                //mineField[i][j].addActionListener(this);
                whichMine[0] = i;
                whichMine[1] = j;
                mineField[i][j].addMouseListener(new MouseAdapter() {
                    boolean pressed;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int k = 0; k < mineField.length; k++) {
                            for (int l = 0; l < mineField[0].length; l++) {
                                if (e.getSource() == mineField[k][l]){
                                    whichMine[0] = k;
                                    whichMine[1] = l;
                                }
                            }
                        }
                        mineField[whichMine[0]][whichMine[1]].getModel().setArmed(true);
                        mineField[whichMine[0]][whichMine[1]].getModel().setPressed(true);
                        pressed = true;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //if(isRightButtonPressed) {underlyingButton.getModel().setPressed(true));
                        mineField[whichMine[0]][whichMine[1]].getModel().setArmed(false);
                        mineField[whichMine[0]][whichMine[1]].getModel().setPressed(false);

                        if (pressed) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                executeSquares(3);
                            }
                            else {
                                executeSquares(1);
                            }
                        }
                        pressed = false;

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        pressed = false;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        pressed = true;
                    }
                });
                //mineField[i][j].addMouseListener(this);
                field.add(mineField[i][j]);
            }
        }

        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.add(field, BorderLayout.CENTER);
        //comp.getNeighbors();
        board.setVisible(true);
    }

    private void numberPrint(){
        //neighbors = comp.getNeighbors();
        boolean[][] mines = comp.getBoard();
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {
                if (!mineField[i][j].isEnabled()){
                    mineField[i][j].setText(""+neighbors[i][j]);
                }
                if (mines[i][j]){
                    if (!mineField[i][j].getText().equals("F")) {
                        mineField[i][j].setText("B");
                    }
                }
            }
        }
    }

    private void checkOpenSquares() {
        //System.out.println("huh");
        neighbors = comp.getNeighbors(numOfBombs);

        /*
        if (!mineField[whichMine[0]][whichMine[1]].isEnabled() && neighbors[whichMine[0]][whichMine[1]] == 0){
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    System.out.println("running");
                    if (comp.checkOpenSquare(whichMine[0] + i, whichMine[1] + j)){
                        mineField[whichMine[0]+i][whichMine[1]+j].setEnabled(false);
                    }
                }
            }
        }
        */
        System.out.println("running");

        boolean change;
        do {
            change = false;
            for (int i = 0; i < mineField.length; i++) {
                for (int j = 0; j < mineField[0].length; j++) {
                    if (neighbors[j][i] == 0) {
                        //if (!mineField[j][i].isEnabled())
                        if(openSquares(j,i)){
                            change = true;
                        }
                        //}
                    }
                }
            }
        }

        while (change);
        numberPrint();

    }
    private boolean openSquares(int j, int i){
        boolean change = false;
        //numberPrint();
        if (!mineField[j][i].isEnabled()) {
            for (int k = -1; k < 2; k++) {
                for (int l = -1; l < 2; l++) {
                    if ((j + k) != mineField.length) {
                        if ((j + k) != -1) {
                            if ((i + l) != mineField[0].length) {
                                if ((i + l) != -1) {
                                    if (mineField[j + k][i + l].isEnabled()) {

                                        mineField[j + k][i + l].setEnabled(false);
                                        change = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*
        if (j != 0) {
            if (neighbors[j - 1][i] == 0 && mineField[j - 1][i].isEnabled()) {
                mineField[j - 1][i].setEnabled(false);
                change = true;
            }
        }
        if (j != mineField[0].length-1) {
            if (neighbors[j + 1][i] == 0 && mineField[j + 1][i].isEnabled()) {
                mineField[j + 1][i].setEnabled(false);
                change = true;
            }
        }
        if (i != 0) {
            if (neighbors[j][i - 1] == 0 && mineField[j][i - 1].isEnabled()) {
                mineField[j][i - 1].setEnabled(false);
                change = true;
            }
        }
        if (i != mineField.length-1) {
            if (neighbors[j][i + 1] == 0 && mineField[j][i + 1].isEnabled()) {
                mineField[j][i + 1].setEnabled(false);
                change = true;
            }
        }
        */
        return change;


    }
    private void winCondition(){
        boolean win = true;
        boolean[][] mines = bombPlace.getMineField();
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[0].length; j++) {
                if (mineField[i][j].getText().equals("B")){
                    win = false;
                }
            }
        }
        if (win){
            System.out.println("win");

            JLabel winnerText = new JLabel("You Won!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            winner.add(winnerText);
            //board.add(winner);
            winner.setVisible(true);
            board.remove(field);
            //beginNewGame();
        }
        else{
            System.out.println("fail");
        }

    }
    /*
    public void beginNewGame(){
        comp.resetGame();
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j].setText("");
                mineField[i][j].setEnabled(true);
            }
        }
    }
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bombNumberButton){
            numOfBombs = (Integer.parseInt(setBombNumber.getText()));
        }
        else if (e.getSource() == boardDimensionsXButton){
            numOfRows = Integer.parseInt(setBoardDimensionsX.getText());
        }
        else if (e.getSource() == boardDimensionsYButton){
            numOfColumns = Integer.parseInt(setBoardDimensionsY.getText());
        }
        else if (e.getSource() == startGameButton){
            initUI();
        }
    }

    private void executeSquares(int button){
        System.out.println("Button: " + button);
        System.out.println("JButton: ("+whichMine[0]+","+whichMine[1]+")");
        if (button == 3){
            if (mineField[whichMine[0]][whichMine[1]].isEnabled()) {
                if (mineField[whichMine[0]][whichMine[1]].getText().equals("F")){
                    mineField[whichMine[0]][whichMine[1]].setText("");
                }
                else {
                    mineField[whichMine[0]][whichMine[1]].setText("F");
                }
                System.out.println("Checking win");
                winCondition();
            }
        }
        else if (button == 1){
            if (mineField[whichMine[0]][whichMine[1]].getText().equals("B")){
                JLabel loserText = new JLabel("You Lost :(");
                loser.add(loserText);
                //board.add(winner);
                loser.setVisible(true);
                //beginNewGame();
            }
            mineField[whichMine[0]][whichMine[1]].setEnabled(false);
            checkOpenSquares();
        }

    }

}
