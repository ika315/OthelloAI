package de.lmu.bio.ifi;

import szte.mi.Move;
import szte.mi.Player;

import java.util.List;
import java.util.Random;

public class MiniMaxAI implements Player {

    Random rnd;

    long t;
    boolean playerOne;

    private OthelloLogic mygame;

    int playerNum;

    int oppNum;

    OthelloLogic copyMax;




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
        playerOne = (order == 0);
        this.t = t;
        this.rnd = rnd;
        mygame = new OthelloLogic();

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
        if (prevMove != null) {
            mygame.makeMove(!playerOne, prevMove.x, prevMove.y);
        }

        Move nextMove = findBestMove(mygame, 3);
        if (nextMove != null) {
            mygame.makeMove(playerOne, nextMove.x, nextMove.y);
            return nextMove;

        } else {
            System.out.println("returned null");
            return null;
        }
    }

    private Move findBestMove(OthelloLogic mygame, int depth) {
        List<Move> possibleMoves = mygame.getPossibleMoves(playerOne);
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : possibleMoves) {
            OthelloLogic initialCopy = cloneBoard(mygame);
            initialCopy.makeMove(playerOne, move.x, move.y);
            int score = miniMax(initialCopy, depth,true);
            if (score >= bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int miniMax(OthelloLogic initialCopy, int depth, boolean isMaximizing) {
        if (depth == 0){
            return evaluation(initialCopy);
        }
        if (isMaximizing) {
            copyMax = cloneBoard(initialCopy);
            int maxVal = Integer.MIN_VALUE;
            List<Move> moves = copyMax.getPossibleMoves(playerOne);

            for (Move m : moves) {
                copyMax.makeMove(!playerOne, m.x, m.y);
                int v = miniMax(copyMax, depth - 1,copyMax.playerOneIsPlaying == playerOne);
                maxVal = Math.max(maxVal, v);
            }

            return maxVal;
        } else {
            OthelloLogic copyMin = cloneBoard(copyMax);
            int minVal = Integer.MAX_VALUE;
            List<Move> possible = copyMin.getPossibleMoves(!playerOne);

            for (Move n : possible){
                copyMin.makeMove(playerOne, n.x, n.y);
                int v = miniMax(copyMin, depth-1, copyMin.playerOneIsPlaying == playerOne);
                minVal = Math.min(minVal, v);
            }
            return minVal;
        }
    }

    private static final int[][] HEURISTIC_WEIGHTS = {
            {200, -80, 40, 20, 20, 40, -80, 200},
            {-80, -160, -10, -10, -10, -10, -160, -80},
            {40, -10, -5, -5, -5, -5, -10, 40},
            {20, -10, -5, -5, -5, -5, -10, 20},
            {20, -10, -5, -5, -5, -5, -10, 20},
            {40, -10, -5, -5, -5, -5, -10, 40},
            {-80, -160, -10, -10, -10, -10, -160, -80},
            {200, -80, 40, 20, 20, 40, -80, 200}
    };

    private int evaluation(OthelloLogic initialCopy) {
        int position = 0;
        int stableBonus = 0;
        int cornerBonus = 0;
        int opponentStonesPenalty = 0;
        int mobility = 0;

        playerNum = playerOne ? 1 : 2;
        oppNum = playerOne ? 2 : 1;

        int[][] gameBoard = initialCopy.gameBoard;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] == playerNum) {
                    position += HEURISTIC_WEIGHTS[i][j];
                } else if (gameBoard[i][j] == oppNum) {
                    opponentStonesPenalty -= HEURISTIC_WEIGHTS[i][j];
                }

                if ((i == 0 || i == 7) && (j == 0 || j == 7)) {
                    if (gameBoard[i][j] == playerNum) {
                        cornerBonus += 50;
                    } else if (gameBoard[i][j] == oppNum) {
                        opponentStonesPenalty -= 80;
                    }
                    if (i == 0 && j == 7) {
                        if (gameBoard[i][j - 1] == playerNum) {
                            stableBonus += 50;
                        } else if (gameBoard[i][j - 1] == oppNum) {
                            opponentStonesPenalty -= 60;
                        }
                    }
                    if (i == 0 && j == 0) {
                        if (gameBoard[i][j + 1] == playerNum) {
                            stableBonus += 50;
                        } else if (gameBoard[i][j + 1] == oppNum) {
                            opponentStonesPenalty -= 60;
                        }
                    }
                    if (i == 7 && j == 0) {
                        if (gameBoard[i][j + 1] == playerNum) {
                            stableBonus += 50;
                        } else if (gameBoard[i][j + 1] == oppNum) {
                            opponentStonesPenalty -= 60;
                        }
                    }
                    if (i == 7 && j == 7) {
                        if (gameBoard[i][j - 1] == playerNum) {
                            stableBonus += 50;
                        } else if (gameBoard[i][j - 1] == oppNum) {
                            opponentStonesPenalty -= 60;
                        }
                    }
                }
                if ((i == 1 || i == 6) && (j == 1 || j == 6)) {
                    if (gameBoard[i][j] == playerNum) {
                        position -= 30;
                    } else if (gameBoard[i][j] == oppNum) {
                        opponentStonesPenalty -= 60;
                    }
                }

                }
            }

        return 30 * position + 10 * cornerBonus + 5 * stableBonus + 15 * opponentStonesPenalty;
    }




    private OthelloLogic cloneBoard(OthelloLogic mygame) {
        OthelloLogic copyOfGame = new OthelloLogic();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                copyOfGame.gameBoard[i][j] = mygame.gameBoard[i][j];
            }
        }

        copyOfGame.playerOneIsPlaying = mygame.playerOneIsPlaying;
        return copyOfGame;
    }

}
