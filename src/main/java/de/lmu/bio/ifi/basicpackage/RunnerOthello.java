package de.lmu.bio.ifi.basicpackage;
import de.lmu.bio.ifi.OthelloLogic;



public class RunnerOthello {

    public static void main(String[] args) {

        OthelloLogic test = new OthelloLogic();

        test.makeMove(true, 4, 5);
        test.makeMove(false, 6, 3);

        System.out.println(test);
        System.out.println(test.gameStatus());
    }




}
