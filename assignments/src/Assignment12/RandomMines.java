package Assignment12;

import java.util.ArrayList;
import java.util.Random;

class RandomMines {
    private Random randNum = new Random();
    private boolean[][] mineField = new boolean[20][20];
    private int mines = 3;


    void setMineNumber(int newMines){
        mines = newMines;
    }
    void placeRandom(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < mineField[0].length; i++) {
            for (int j = 0; j < mineField.length; j++) {
                list.add(i*100+j);
            }
        }
        for (int i = 0; i < mines; i++) {
            int choice = (randNum.nextInt(list.size()));
            //System.out.println(list.get(choice)/100);
            //System.out.println(list.get(choice) % 100);
            mineField[list.get(choice)/100][list.get(choice) % 100] = true;
            list.remove(choice);
        }

    }
    boolean[][] getMineField(){
        System.out.println("setting mine "+mineField.length);
        return mineField;

    }
    void setMineField(boolean[][] otherMineField){

        mineField = otherMineField;
        System.out.println("setting something"+mineField.length);
    }
}