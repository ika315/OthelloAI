package de.lmu.bio.ifi.gui;

import de.lmu.bio.ifi.OthelloLogic;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;






public class RandomKI implements szte.mi.Player {

    private int order;
    private Random random;
    private OthelloLogic mygame;
    private List<Move> possibleMoves;

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
    @Override
    public void init(int order, long t, Random rnd) {
    }

    public void setGameState(OthelloLogic game){
        this.mygame = game;
    }

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

        random = new Random();
        possibleMoves = mygame.getPossibleMoves(false);

        if (possibleMoves.isEmpty()){
            return null;
        }
        Move nextMove = possibleMoves.get(random.nextInt(possibleMoves.size()));

        mygame.makeMove(false,nextMove.x,nextMove.y);

        return nextMove;

    }
}
