package de.lmu.bio.ifi;
import szte.mi.Move;


public class RunnerOthello {

    public static void main(String[] args) {

        OthelloLogic test = new OthelloLogic();


        //manuelle überprüfung

        test.printPossibleMoves(test.getPossibleMoves(true));
        test.makeMove(true,5,4);

        System.out.println(test);

        test.printPossibleMoves(test.getPossibleMoves(false));

        test.makeMove(false, 5,3);



        /*

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
