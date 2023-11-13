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
import szte.mi.Move;

public class OthelloGUI extends Application implements EventHandler<ActionEvent> {
    private OthelloLogic mygame;
    private static final int size = 8;
    private static final int buttonSize = 60;
    private Label statusLabel;

    private Button resetButton;

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
        othelloTitle.setTextFill(Color.BLACK);

        statusLabel = new Label("Status: " + mygame.status);
        resetButton = new Button("Start a new game");

        topHBox.getChildren().add(othelloTitle);
        topHBox.setAlignment(Pos.CENTER);
        root.setTop(topHBox);
        BorderPane.setAlignment(topHBox,Pos.CENTER);

        bottomHBox.getChildren().addAll(statusLabel, resetButton);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setSpacing(20);
        root.setBottom(bottomHBox);
        BorderPane.setAlignment(bottomHBox, Pos.CENTER);

        for (int r = 0; r < size; r++){
            for (int c = 0; c < size; c++){
                OthelloButton button = new OthelloButton(r,c);
                button.setOnAction(this);
                board.add(button,c,r);

                if ((r == 3 && c == 4) || (r == 4 && c == 3)) {
                    if (mygame.gameBoard[3][4] == OthelloLogic.X || mygame.gameBoard[4][3] == OthelloLogic.X) {
                        button.drawBlack();
                    }
                }

                if ((r == 3 && c == 3) || (r == 4) && (c == 4)){
                    if (mygame.gameBoard[3][3] == OthelloLogic.O || mygame.gameBoard[4][4] == OthelloLogic.O){
                        button.drawWhite();
                    }
                }
            }
        }


        board.setAlignment(Pos.CENTER);
        root.setCenter(board);
        BorderPane.setAlignment(board,Pos.CENTER);

        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (mygame.status == GameStatus.RUNNING){
            if (actionEvent.getSource() instanceof OthelloButton){
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
            setStyle("-fx-background-color: #556B2F; -fx-border-color: black; -fx-border-width: 2; -fx-background-insets: 0, 1;");
        }

        public void clicked(int player){
            if (player == OthelloLogic.X){
                if (mygame.makeMove(true, c, r) == true) {
                    drawBlack();
                }
            } else {
                    if(mygame.makeMove(false, c, r) == true) {
                        drawWhite();
                    }
            }

            statusLabel.setText("Status: " + mygame.status);
            this.setOnAction(null);
        }

        public void drawBlack(){
            Circle c = new Circle(buttonSize/3, Color.BLACK);
            c.setStroke(Color.WHITE);
            c.setCenterX(buttonSize/2);
            c.setCenterY(buttonSize/2);
            setGraphic(c);
            setStyle(getStyle() + " -fx-border-width: 2; -fx-background-insets: 0, 1; -fx-padding: 0");

        }

        public void drawWhite(){
            Circle c = new Circle(buttonSize/3, Color.WHITE);
            c.setStroke(Color.BLACK);
            c.setCenterX(buttonSize/2);
            c.setCenterY(buttonSize/2);
            setGraphic(c);
            setStyle(getStyle() + " -fx-border-width: 2; -fx-background-insets: 0, 1; -fx-padding: 0");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
