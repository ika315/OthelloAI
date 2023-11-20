package de.lmu.bio.ifi;
import de.lmu.bio.ifi.OthelloLogic;
import de.lmu.bio.ifi.gui.RandomKI;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;


public class RunnerOthello {

    public static void main(String[] args) {

        OthelloLogic test = new OthelloLogic();

        RandomKI random = new RandomKI();

        random.setGameState(test);

        //manuelle überprüfung

        test.printPossibleMoves(test.getPossibleMoves(true));
        test.makeMove(true,5,4);

        test.printPossibleMoves(test.getPossibleMoves(false));

        //Move move = random.nextMove(new Move(5,4), 0,0);




        /*
        test.printPossibleMoves(test.getPossibleMoves(false));
        test.makeMove(false, 5, 3);

        test.printPossibleMoves(test.getPossibleMoves(true));
        test.makeMove(true,4,2);

        test.printPossibleMoves(test.getPossibleMoves(false));
        test.makeMove(false,5,5);

        test.printPossibleMoves(test.getPossibleMoves(true));
        test.makeMove(true,2,4);

        test.printPossibleMoves(test.getPossibleMoves(false));
        test.makeMove(false,2,3);

        test.printPossibleMoves(test.getPossibleMoves(true));
        test.makeMove(true,1,2);

         */








        System.out.println(test);
        System.out.println(test.gameStatus());
    }




}
