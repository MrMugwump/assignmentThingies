import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GUITicTacToe implements ActionListener{
    JFrame board = new JFrame("Tic Tac Toe Board");
    JLabel xLabel = new JLabel("X wins: 0" );
    JLabel oLabel = new JLabel("O wins: 0");
    JButton xChangeName = new JButton("Change X's name");
    JButton oChangeName = new JButton("Change O's name");
    JButton button [][] = new JButton[3][3];
    JTextField xChangeField = new JTextField();
    JTextField oChangeField = new JTextField();
    Container north = new Container();
    Container center = new Container();
    String[][] ticTacToeBoard = new String[][]{
            {"  ", "  ", "  "},
            {"  ", "  ", "  "},
            {"  ", "  ", "  "}
    };
    int p1Total = 0;
    int p2Total = 0;
    boolean turn = false;
    String winsX = "X wins: 0";
    String winsO = "O wins: 0";
    String xNameChange = "X";
    String oNameChange = "O";
    JButton onePlayer = new JButton( "One Player");
    JButton twoPlayers = new JButton("Two Players");
    boolean numOfPlayers;
    public static void main(String[] args){
        new GUITicTacToe();
    }
    public GUITicTacToe(){
        board.setSize(400, 400);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setLayout(new GridLayout(1, 2));
        board.add(onePlayer);
        onePlayer.addActionListener(this);
        board.add(twoPlayers);
        twoPlayers.addActionListener(this);
        board.setVisible(true);
        //setup();
    }
    public void setup(){
        board.remove(onePlayer);
        board.remove(twoPlayers);
        board.setLayout(new BorderLayout());
        center.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                button[j][i] = new JButton();
                center.add(button[j][i]);
                button[j][i].addActionListener(this);
            }
        }
        board.add(center, BorderLayout.CENTER);
        north.setLayout(new GridLayout(3, 2));
        north.add(xLabel);
        north.add(oLabel);
        north.add(xChangeName);
        north.add(oChangeName);
        north.add(xChangeField);
        north.add(oChangeField);
        board.add(north, BorderLayout.NORTH);
        xChangeName.addActionListener(this);
        oChangeName.addActionListener(this);

    }
    public void printBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(ticTacToeBoard[i][j]);
            }
            System.out.println();
        }
    }
    // This just prints the board using nested for loops
    //This places the piece & checks to see if it is valid. If it isn't, you lose your turn.
    public void tttMove(int yAxis, int xAxis, boolean turn){
        if (turn){
            button[yAxis][xAxis].setText("X");
            button[yAxis][xAxis].setEnabled(false);
        }
        else {
            button[yAxis][xAxis].setText("O");
            button[yAxis][xAxis].setEnabled(false);
        }
    }
    public boolean winCondition(boolean checkTurn){
        boolean win = false;
        String theTurn;
        if (checkTurn){
            theTurn = "X";
        }
        else {
            theTurn= "O";
        }
        System.out.println("CHECKING FOR WIN");
        //horizantal row
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 3; x++) {
                System.out.println();
                if (!button[y][x].getText().equals(theTurn) || button[y][x].isEnabled()){ //ticTacToeBoard[y][x].equals("  ") || ticTacToeBoard[y][x].equals(theTurn)
                    win = false;
                    //System.out.println("NO WIN AT ("+y+","+x+") DURING HORIZONTAL CHECK");
                    break;

                }
                else {
                    win = true;
                    if (x == 2) {
                        break;
                    }
                }
            }
            if (win){
                break;
            }
        }
        //vertical row
        if (!win) {
            for (int x = 0; x < 3; x ++) {
                for (int y = 0; y < 3; y++) {
                    if (!button[y][x].getText().equals(theTurn) || button[y][x].isEnabled()){
                        win = false;
                        System.out.println("TURN IS "+ theTurn);
                        System.out.println("NO WIN AT ("+y+","+x+") DURING VERTICAL CHECK");
                        break;
                    }
                    else {
                        System.out.println("POSSIBLE WIN AT ("+y+","+x+") DURING VERTICAL CHECK");
                        win = true;
                        if (y == 2) {
                            break;
                        }
                    }
                }
                if (win){
                    break;
                }
            }
        }
        if (!win) {//diagonal left to right
            for (int xy = 0; xy < 3; xy++) {
                if (!(button[xy][xy].getText().equals(theTurn))) {
                    win = false;
                    //System.out.println("NO WIN AT ("+xy+","+xy+") DURING LEFT TO RIGHT CHECK");
                    break;
                }
                else {
                    win = true;
                }
            }
        }
        if (!win) {//diagonal right to left
            for (int xy = 2; xy > -1; xy--) {
                if (!(button[2-xy][xy].getText().equals(theTurn))) {
                    win = false;
                    //System.out.println("NO WIN AT ("+(2-xy)+","+xy+") DURING RIGHT TO LEFT CHECK");
                    break;
                }
                else {
                    win = true;
                }
            }
        }
        return win;
    }

    //checks for draw.
    public boolean catScratch(){
        boolean draw = true;
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 3; x++) {
                if (button[y][x].isEnabled()){
                    draw = false;
                    break;
                }
            }
        }
        return draw;
    }
    public void ai(){
        int [] arrayPlace = new int[2];
        int counter = 0;
        int counterYay = 0;
        int randNumY;
        int randNumX;
        //horizantal row
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 3; x++) {
                if (button[y][x].getText().equals("X")){ //ticTacToeBoard[y][x].equals("  ") || ticTacToeBoard[y][x].equals(theTurn)
                    counter++;
                }
                else if (button[y][x].isEnabled()){
                    arrayPlace[0] = y;
                    arrayPlace[1] = x;
                }
                else if (button[y][x].getText().equals("O")){
                    counterYay ++;
                }
            }
            if (counter < 2) {
                counter = 0;
            }
            if (counter == 2){
                break;
            }
        }

        //vertical row
        if (counter < 2) {
            counter = 0;
            counterYay = 0;
            for (int x = 0; x < 3; x ++) {
                for (int y = 0; y < 3; y++) {
                    if (button[y][x].getText().equals("X")){ //ticTacToeBoard[y][x].equals("  ") || ticTacToeBoard[y][x].equals(theTurn)
                        counter++;
                    }
                    else if (button[y][x].isEnabled()){
                        arrayPlace[0] = y;
                        arrayPlace[1] = x;
                    }
                    else if (button[y][x].getText().equals("O")){
                        counterYay ++;
                    }
                }
                if (counter < 2) {
                    counter = 0;
                }
            }

        }
        if (counter < 2) {//diagonal left to right
            counterYay = 0;
            counter = 0;
            for (int xy = 0; xy < 3; xy++) {
                if (button[xy][xy].getText().equals("X")){ //ticTacToeBoard[y][x].equals("  ") || ticTacToeBoard[y][x].equals(theTurn)
                    counter++;
                }
                else if (button[xy][xy].isEnabled()){
                    arrayPlace[0] = xy;
                    arrayPlace[1] = xy;
                }
                else if (button[xy][xy].getText().equals("O")){
                    counterYay ++;
                }
            }

        }
        if (counter < 2) {//diagonal right to left
            counter = 0;
            counterYay = 0;
            for (int xy = 2; xy > -1; xy--) {
                if (button[2-xy][xy].getText().equals("X")){ //ticTacToeBoard[y][x].equals("  ") || ticTacToeBoard[y][x].equals(theTurn)
                    counter++;
                }
                else if (button[2-xy][xy].isEnabled()){
                    arrayPlace[0] = 2-xy;
                    arrayPlace[1] = xy;
                }
                else if (button[2-xy][xy].getText().equals("O")){
                    counterYay ++;
                }
            }
        }
        System.out.println("WE HAVE REACHED THE POINT WHERE I DECIDE TO MAKE A MOVE");
        if (button[arrayPlace[0]][arrayPlace[1]].isEnabled()) {
            System.out.println("WE ARE MAKING MOVE: NOT RANDOM");
            if (counter == 2) {
                System.out.println("ACTIVATED: DANGER AT (" + arrayPlace[0] + "," + arrayPlace[1] + ")");
                tttMove(arrayPlace[0], arrayPlace[1], false);
            } else if (counterYay == 2) {
                System.out.println("ACTIVATED: CLOSE TO SUCCESS");
                tttMove(arrayPlace[0], arrayPlace[1], false);
            }
            else {
                System.out.println("RANDOM SPOT BEING PLACED");
                do{
                    randNumX = (int)(Math.random() * 3);
                    randNumY = (int)(Math.random() * 3);
                }
                while (!button[randNumY][randNumX].isEnabled());
                System.out.println("(" + randNumX + "," + randNumY + ")");
                tttMove(randNumY, randNumX, false);
            }
        }
        else {
            System.out.println("RANDOM SPOT BEING PLACED");
            do{
                randNumX = (int)(Math.random() * 3);
                randNumY = (int)(Math.random() * 3);
            }
            while (!button[randNumY][randNumX].isEnabled());
            System.out.println("(" + randNumX + "," + randNumY + ")");
            tttMove(randNumY, randNumX, false);
        }
        turn = false;
    }
    //@Override
    public void actionPerformed(ActionEvent e) {
        //use the 2d array to place text.
        if (e.getSource().equals(onePlayer)){
            setup();
            numOfPlayers = true;
        }
        else if (e.getSource().equals(twoPlayers)){
            setup();
            numOfPlayers = false;
        }

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                if (e.getSource().equals(button[i][j])) {
                    tttMove(i, j, turn);
                    if (numOfPlayers){
                        if (!(winCondition(true) || catScratch())) {
                            ai();
                        }
                    }
                    if (winCondition(turn)){
                        System.out.println("A win");
                        if (turn){

                            p1Total ++;
                        }
                        else {
                            p2Total ++;
                        }
                    }
                }

            }
        }
        //checks to see if you win so you don't have to watch p2 play a useless piece



        if (e.getSource().equals(xChangeName)){
            xNameChange = xChangeField.getText();
            xChangeField.setText("");
        }
        else if (e.getSource().equals(oChangeName)){
            oNameChange = oChangeField.getText();
            oChangeField.setText("");
        }
        winsX = xNameChange + " wins = " + p1Total;
        winsO = oNameChange + " wins = " + p2Total;
        xLabel.setText(winsX);
        oLabel.setText(winsO);
        if (winCondition(turn) || catScratch()) {
            if (turn){
                turn = false;
            }
            else{
                turn = true;
            }
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    button[j][i].setText("");
                    button[j][i].setEnabled(true);
                }
            }
            turn = true;
        }
        else {
            if (turn){
                turn = false;
            }
            else{
                turn = true;
            }
        }
    }
}
