package de.lmu.bio.ifi;

import com.sun.nio.sctp.SctpSocketOption;
import szte.mi.Move;
import szte.mi.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AI implements Player {
    /**
     * Performs initialization depending on the parameters.
     *
     * @param order Defines the order of the players. Value 0 means
     *              this is the first player to move, 1 means second and so on.
     *              For two-player games only values 0 and 1 are possible.
     * @param t     Gives the remaining overall running time of the player in
     *              ms. Initialization is also counted as running time.
     * @param rnd   source of randomness to be used wherever random
     *              numbers are needed
     */

    Random rnd;

    long t;
    boolean playerOne;

    private OthelloLogic mygame;
    @Override
    public void init(int order, long t, Random rnd) {
        playerOne = (order == 0);
        this.t = t;
        this.rnd = rnd;
        mygame = new OthelloLogic();
    }

    /*

    public void setGameState(OthelloLogic game){
        this.mygame = game;
    }

     */

    /**
     * Calculates the next move of the player in a two player game.
     * It is assumed that the player is stateful and the game is
     * deterministic, so the parameters only
     * give the previous move of the other player and remaining times.
     *
     * @param prevMove  the previous move of the opponent. It can be null,
     *                  which means the opponent has not moved (or this is the first move).
     * @param tOpponent remaining time of the opponent
     * @param t         remaining time for this player
     */
    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        if (prevMove != null){
            mygame.makeMove(!playerOne, prevMove.x, prevMove.y);
        }

        Move nextMove = findBestMove(mygame);
        mygame.makeMove(false, nextMove.x,nextMove.y);
        return nextMove;
    }

    private OthelloLogic cloneBoard(OthelloLogic mygame) {

        OthelloLogic copyOfGame = new OthelloLogic();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                copyOfGame.gameBoard[i][j] = mygame.gameBoard[i][j];
            }
        }
        return copyOfGame;

    }

        private static final int[][] HEURISTIC_WEIGHTS = {
                {100, -20, 10, 5, 5, 10, -20, 100},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {10, -2, -1, -1, -1, -1, -2, 10},
                {5, -2, -1, -1, -1, -1, -2, 5},
                {5, -2, -1, -1, -1, -1, -2, 5},
                {10, -2, -1, -1, -1, -1, -2, 10},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {100, -20, 10, 5, 5, 10, -20, 100}
        };


        private Move findBestMove(OthelloLogic mygame) {
            List<Move> possibleMoves = mygame.getPossibleMoves(playerOne);
            Move bestMove = null;
            int bestScore = Integer.MIN_VALUE;

            for (Move move : possibleMoves) {
                int score = evaluateMove(mygame, move);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }

            return bestMove;
        }

        private int evaluateMove(OthelloLogic mygame, Move move) {
            int score = 0;
            int[][] gameBoard = mygame.getGameBoard();

            score += HEURISTIC_WEIGHTS[move.y][move.x];


            return score;
        }

/*

    /* if ai is maximizing, it means the ai is the current player.
    if the ai is minimizing it means the human player is the current player



    public int miniMax(OthelloLogic mygame, int depth, boolean isMaximize){
        if (depth == 0){
            return evaluation(mygame);
        }
        if (isMaximize) {
            OthelloLogic copyOfGame = cloneBoard(mygame);
            int maxVal = Integer.MIN_VALUE;
            if (copyOfGame.playerOneIsPlaying == true){
                copyOfGame.playerOneIsPlaying = false;
            }
            List<Move> moves = copyOfGame.getPossibleMoves(false);
            for (Move m : moves) {
                copyOfGame.makeMove(false, m.x, m.y);
                int v = miniMax(copyOfGame, depth-1, false);
                maxVal = Math.max(maxVal, v);

            }
            return maxVal;
        } else {
            int minVal = Integer.MAX_VALUE;
            List<Move> moves = mygame.getPossibleMoves(true);
            for (Move m : moves) {
                OthelloLogic copyOfGame = cloneBoard(mygame);
                copyOfGame.makeMove(true, m.x, m.y);
                int v = miniMax(copyOfGame, depth - 1, true);
                minVal = Math.min(minVal, v);
            }
            return minVal;
        }
    }


    private static final int[][] pieceValues = {
            { 100,  15,  10,  10,  10,  10,  15, 100},

            {  15,   1,   3,   3,   3,   3,   1,  15},

            {  10,   3,   2,   2,   2,   2,   3,  10},

            {  10,   3,   1,   1,   1,   2,   3,  10},

            {  10,   3,   2,   2,   2,   2,   3,  10},

            {  10,   3,   2,   2,   2,   2,   3,  10},

            {  15,   1,   2,   2,   2,   2,   3,  15},

            { 100,  15,  10,  10,  10,  10,  15, 100}
    };



    private int evaluation(OthelloLogic mygame) {

        int blackScore = 0;
        int whiteScore = 0;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (mygame.gameBoard[i][j] == OthelloLogic.X){
                    blackScore += pieceValues[i][j];
                } else if (mygame.gameBoard[i][j] == OthelloLogic.O){
                    whiteScore += pieceValues[i][j];
                }
            }
        }

        return blackScore - whiteScore; //positive score: black player advantage / negative score: white player advantage
    }

 */



}

