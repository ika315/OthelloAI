package de.lmu.bio.ifi;
import de.lmu.bio.ifi.OthelloLogic;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;


public class RunnerOthello {

    public static void main(String[] args) {

        OthelloLogic test = new OthelloLogic();


        //manuelle überprüfung

        System.out.println(test.getPossibleMoves(true));
        test.makeMove(true, 5, 4);

        System.out.println(test.getPossibleMoves(false));
        test.makeMove(false, 5, 3);

        System.out.println(test.getPossibleMoves(true));
        test.makeMove(true,4,2);

        System.out.println(test.getPossibleMoves(false));
        test.makeMove(false,5,5);

        System.out.println(test.getPossibleMoves(true));
        test.makeMove(true,2,4);

        System.out.println(test.getPossibleMoves(false));
        test.makeMove(false,2,3);

        System.out.println(test.getPossibleMoves(true));
        test.makeMove(true,1,2);

        System.out.println(test);
        System.out.println(test.gameStatus());
    }




}
