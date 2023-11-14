package de.lmu.bio.ifi;

import szte.mi.Move;
import java.util.ArrayList;
import java.util.List;

public class OthelloLogic implements Game {

    //variables for the board
    public int[][] gameBoard;
    public static final int size = 8;
    public static final int X = 1;
    public static final int O = 2;
    public static final int EMPTY = 0;

    //variables for game flow/game status
    public GameStatus status;
    public int numberMoves;
    boolean validMove = false;
    int counterX;
    int counterO;

    //variables for logic
    int neighbour;
    public boolean playerOneIsPlaying = true;

    //constructor that initializes an othello game board
    public OthelloLogic() {
        gameBoard = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                gameBoard[i][j] = EMPTY;
            }
        }
        gameBoard[3][4] = X;
        gameBoard[4][3] = X;
        gameBoard[3][3] = O;
        gameBoard[4][4] = O;
        status = GameStatus.RUNNING;
    }

    //checks whether all the moves are within the board
    private boolean checkValidMove(int x, int y) {
        if (x >= 0 && x < gameBoard.length && y < gameBoard.length && y >= 0){
            if (this.gameBoard[y][x] == EMPTY){
                return true;
            }
        }
        return false;
    }

    /**
     * Make a move for the given player at the given position.
     *
     * @param playerOne true if player 1, else player 2.
     * @param x         the x coordinate of the move.
     * @param y         the y coordinate of the move.
     * @return true if the move was valid, else false.
     */
    @Override
    public boolean makeMove(boolean playerOne, int x, int y) {

        //keep track of current player
        int currentColour = playerOne ? 1 : 2;
        int otherColour = playerOne ? 2 : 1;

        //array where all the possible move directions are saved
        //-1 left/up +1 right/down
        Move[] directions = new Move[] {new Move(-1,-1), new Move(-1,0), new Move(-1,1), new Move(0,-1), new Move(0,1), new Move(1,-1), new Move(1,0), new Move(1,1)};

        //is the move even inside the board?
        //prevent mixup/mistakes when its not playerOne's turn bc eg. no possible moves left
        if (checkValidMove(x,y) == false || playerOne != playerOneIsPlaying) {
            return false;
        }

        //array list (bc flexible) to look at neighbours of current move
        //if there are no neighbours (which we will check later), its simply not a valid move
        ArrayList<Move> surrounding = new ArrayList<>();

        //move around current move by using our directions to look at all 8 directions (left, right, etc)
        for (Move m : directions) {
            checkNeighborInBoard(x,y,m);
            //check whether neighbour is within bounds
            if (checkNeighborInBoard(x,y,m) == true) {
                neighbour = gameBoard[y + m.y][x + m.x];
                //if neighbour is opponent, we want to explore that direction more
                //if not opponent, we dont really care bc we cant flip
                if (neighbour == otherColour) {
                    surrounding.add(m);
                }
            }
        }

        validMove = false;

        //for every neighbour we need to flip the pieces
        for (Move s : surrounding){
            if(checkFlip(x,y,currentColour,s) == true){
                validMove = true;
            }
        }

        //if it was over all a valid move, we set the move on the game board
        //we also keep track of the number of moves in case of a draw
        //and keep track of whose turn it is in case of no possible moves
        if (validMove == true){
            int player = EMPTY;
            if (playerOne == true) {
                player = X;
                gameBoard[y][x] = player;
                numberMoves++;


            } else {
                player = O;
                gameBoard[y][x] = player;
                numberMoves++;

            }
            playerOneIsPlaying = !playerOneIsPlaying;
            return true;
        }


        //if we have no opponent piece surrounding our coordinates,
        //we cant make a move (rules)
        if (surrounding.isEmpty()){
            return false;
        }
        System.out.println("Invalid move");
        return false;
    }

    //check all the flips for the neighbours we saved
    public boolean checkFlip(int x, int y,int color,Move move){

        //create arraylist where we save all the coordinates
        //where we need to flip
        ArrayList<Move> flippable = new ArrayList<>();


        //only if neighbour is still inside board
        while (checkNeighborInBoard(x,y,move) == true){

            //look at coordinates within saved directions
            y += move.y;
            x += move.x;

            //if its the opponent color, we can flip it
            if (gameBoard[y][x] != color && gameBoard[y][x] != EMPTY){
                flippable.add(new Move(x,y));

            //if in direction an empty cell, we cant flip bc we cant sandwich opponents piece (XOOOX)
            } else if (gameBoard[y][x] == EMPTY) {
                return false;
            } else {
                flipThePieces(flippable,color);
                return true;
            }

            //check for every direction bounds
            checkNeighborInBoard(x,y,move);
        }

        return false;
    }

    private boolean checkNeighborInBoard(int x, int y, Move move) {
        boolean neighbourInBoard = true;
        return neighbourInBoard=x + move.x >= 0 && x + move.x < size && y + move.y >= 0 && y + move.y < size;
    }

    private void flipThePieces(ArrayList<Move> flippable, int color) {
        //flip all the noted pieces to the current players color
        for (Move m : flippable){
            this.gameBoard[m.y][m.x] = color;
        }
    }


    /**
     * Get all possible moves for the current player.
     * Return null if it is not the turn of the given player.
     * The list is empty if there are no possible moves.
     *
     * @param playerOne
     * @return a list of all possible moves.
     */
    @Override
    public List<Move> getPossibleMoves(boolean playerOne) {

        List<Move> possibleMoves = new ArrayList<>();

        //if its not the current player accessing the possible moves
        //return null
        if (playerOne != playerOneIsPlaying) {
            return null;
        }

        //bc make move method changes the game board we create a copy of our current game board
        //this way we can recreate it
        //because checking the return of the makeMove method also changes the gameBoard
        int[][] temporary = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                temporary[row][col] = gameBoard[row][col];
            }
        }

        //now check for game board possible moves
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (makeMove(playerOne, i, j)) {
                    playerOneIsPlaying = !playerOneIsPlaying;
                    possibleMoves.add(new Move(i, j));
                }

                //recreate array
                for (int a = 0; a < size; a++) {
                    System.arraycopy(temporary[a], 0, gameBoard[a], 0, size);
                }
            }
        }

        return possibleMoves;
    }

    public void printPossibleMoves(List<Move> possibleMoves) {
        System.out.println("Possible Moves for Player " + (playerOneIsPlaying ? "1" : "2") + ":");

        for (Move move : possibleMoves) {
            System.out.println("Row: " + move.y + ", Column: " + move.x);
        }
    }



    /**
     * Check and return the status of the game, if there is a winner, a draw or still running.
     *
     *
     * @return the current game status.
     */
    @Override
    public GameStatus gameStatus() {
        //if the number of the pieces are the same and the board is already full, we have a draw
        if (numberMoves == size * size && counterX == counterO) {
            return GameStatus.DRAW;
        }
        //if the players do have possible moves, the game is still running
            else if (getPossibleMoves(playerOneIsPlaying).isEmpty() == false){
                return GameStatus.RUNNING;

        //else count the pieces on the board to determine winner
            } else {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (gameBoard[i][j] == X) {
                            counterX++;
                        }
                        if (gameBoard[i][j] == O) {
                            counterO++;
                        }
                    }
                }
            }

            if (counterX > counterO) {
                return GameStatus.PLAYER_1_WON;
            } else if (counterO > counterX) {
                return GameStatus.PLAYER_2_WON;
            }

        return status;
    }


    public String toString(){
        StringBuilder boardToString = new StringBuilder();
        for (int i = 0; i <size; i++){
            for (int j = 0; j < size; j++){
                if (this.gameBoard[i][j] == EMPTY){
                    boardToString.append(".");
                } else if (this.gameBoard[i][j] == X){
                    boardToString.append("X");
                } else if (this.gameBoard[i][j] == O){
                    boardToString.append("O");
                }
                boardToString.append(" ");
            }
            boardToString.append("\n");
        }
        return boardToString.toString();
    }
}
