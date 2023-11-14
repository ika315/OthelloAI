package de.lmu.bio.ifi.gui;

import de.lmu.bio.ifi.GameStatus;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import de.lmu.bio.ifi.OthelloLogic;
import javafx.scene.control.Button;

public class OthelloGUI extends Application implements EventHandler<ActionEvent> {
    private OthelloLogic mygame;
    private static final int size = 8;
    private static final int buttonSize = 60;
    private Label statusLabel;

    private Label whosTurn;

    Button[][] buttons = new Button[size][size];
    GridPane board;


    @Override
    public void start(Stage stage) throws Exception {

        mygame = new OthelloLogic();

        stage.setTitle("Irem's Othello GUI");
        BorderPane root = new BorderPane();
        board = new GridPane();

        HBox topHBox = new HBox();
        HBox bottomHBox = new HBox();

        Label othelloTitle = new Label("Othello");
        othelloTitle.setFont(Font.font("Algerian", 45));

        statusLabel = new Label("Status: " + mygame.status);
        whosTurn = new Label("It's Player One's turn.");

        topHBox.getChildren().add(othelloTitle);
        topHBox.setAlignment(Pos.CENTER);
        root.setTop(topHBox);
        BorderPane.setAlignment(topHBox, Pos.CENTER);

        bottomHBox.getChildren().add(statusLabel);
        bottomHBox.getChildren().add(whosTurn);
        bottomHBox.setSpacing(20);
        bottomHBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomHBox);
        BorderPane.setAlignment(bottomHBox, Pos.CENTER);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                OthelloButton button = new OthelloButton(r, c);
                button.setOnAction(this);
                board.add(button, c, r);
                buttons[r][c] = button;

                if ((r == 3 && c == 4) || (r == 4 && c == 3)) {
                    if (mygame.gameBoard[3][4] == OthelloLogic.X || mygame.gameBoard[4][3] == OthelloLogic.X) {
                        button.drawBlack();
                    }
                }

                if ((r == 3 && c == 3) || (r == 4) && (c == 4)) {
                    if (mygame.gameBoard[3][3] == OthelloLogic.O || mygame.gameBoard[4][4] == OthelloLogic.O) {
                        button.drawWhite();
                    }
                }
            }
        }


        board.setAlignment(Pos.CENTER);
        root.setCenter(board);
        BorderPane.setAlignment(board, Pos.CENTER);

        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (mygame.status == GameStatus.RUNNING) {
            if (actionEvent.getSource() instanceof OthelloButton) {
                ((OthelloButton) actionEvent.getSource()).clicked(mygame.playerOneIsPlaying ? OthelloLogic.X : OthelloLogic.O);
            }
        }

    }

    class OthelloButton extends Button {
        private int r;
        private int c;

        public OthelloButton(int r, int c) {
            super();
            this.r = r;
            this.c = c;
            setPrefSize(buttonSize, buttonSize);
            setStyle("-fx-background-color: #556B2F; " + "-fx-border-color: black; " + "-fx-border-width: 2;");
        }

        public void clicked(int player) {
            if (player == OthelloLogic.X) {
                //showPossibleMoves(player);
                if (mygame.makeMove(true, c, r) == true) {
                    paintFlippedButtons();
                    buttons[r][c].setGraphic(drawBlack());
                }
            } else if (player == OthelloLogic.O) {
                //showPossibleMoves(player);
                if (mygame.makeMove(false, c, r) == true) {
                    paintFlippedButtons();
                    buttons[r][c].setGraphic(drawWhite());
                }
            }
            whosTurn.setText("It's " + (mygame.playerOneIsPlaying ? "Player One's" : "Player Two's") + " turn.");

            statusLabel.setText("Status: " + mygame.status);
            this.setOnAction(null);
        }

        private void paintFlippedButtons() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (mygame.gameBoard[i][j] == OthelloLogic.X) {
                        buttons[i][j].setGraphic(drawBlack());
                    }
                    if (mygame.gameBoard[i][j] == OthelloLogic.O) {
                        buttons[i][j].setGraphic(drawWhite());
                    }
                }
            }
        }
        /*
        private void showPossibleMoves(int player) {
            List<Move> possibleMoves = new ArrayList<>();

            possibleMoves = mygame.getPossibleMoves(mygame.playerOneIsPlaying ? true : false);

            for (Move move : possibleMoves) {
                if (player == OthelloLogic.X) {
                    int row = move.x;
                    int col = move.y;
                    paintPossibleMoves(row, col);
                }
            }
        }

        private void paintPossibleMoves(int row, int col) {
            buttons[row][col].setGraphic(drawTransparent());
        }

*/
        public Node drawBlack() {
            Circle c = new Circle(buttonSize / 3, Color.BLACK);
            c.setStroke(Color.WHITE);
            c.setCenterX(buttonSize / 2);
            c.setCenterY(buttonSize / 2);
            setGraphic(c);
            setStyle(getStyle() + "-fx-border-width: 2;" + "-fx-padding: 0;");

            return c;
        }

        public Node drawWhite() {
            Circle c = new Circle(buttonSize / 3, Color.WHITE);
            c.setStroke(Color.BLACK);
            c.setCenterX(buttonSize / 2);
            c.setCenterY(buttonSize / 2);
            setGraphic(c);
            setStyle(getStyle() + " -fx-border-width: 2;" + "-fx-padding: 0;");

            return c;
        }

        public Node drawTransparent() {
            Circle c = new Circle(buttonSize / 3, Color.BEIGE);
            c.setCenterX(buttonSize / 2);
            c.setCenterY(buttonSize / 2);
            setGraphic(c);
            setStyle(getStyle() + " -fx-border-width: 2;" + "-fx-padding: 0;");

            return c;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

