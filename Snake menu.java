/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.awt.Dimension;
import java.awt.*;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import java.util.Random;


public class Snake extends Application {
    @Override
    public void start(Stage primaryStage) {
        int sceneX = 1200, sceneY = 600;
        BorderPane border = new BorderPane();
        
        VBox menu = addMenu();
        //double left = ((double)sceneX - 3*Double.MAX_VALUE) / 2;
        menu.setPadding(new Insets(20,0,20,450));
        menu.setStyle("-fx-background-color: red");
        border.setTop(menu);
        
        Label score = new Label("Score");
        score.setPadding(new Insets(20,0,20,450));
        border.setBottom(score);
        
        GridPane field = addBoard();
        border.setCenter(field);

     
       
        Scene scene = new Scene(border, sceneX, sceneY);
        
        primaryStage.setTitle("Snake. The acorn devourer.");
        primaryStage.setScene(scene);
        primaryStage.show();        
        //System.out.println(menu.getWidth());
    }
    
    public VBox addMenu() {        
        Button btnNewGame = new Button("New game");
        btnNewGame.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        Button btnHighScores = new Button("High Scores");
        Button btnExit = new Button("Exit");
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        btnNewGame.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnHighScores.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnExit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);            
        //btnNewGame.setPreferredSize(new Dimension(100, 100));
        
        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        //tileButtons.setPadding(new Insets(20, 00, 00, 0));
        tileButtons.getChildren().addAll(btnNewGame, btnHighScores, btnExit);
        System.out.println(btnNewGame.getWidth());
        
        VBox menu = new VBox();
        menu.getChildren().add(tileButtons);
        //menu.setAlignment(Pos.CENTER);
        
        
        return menu;
    }
    
    public GridPane addBoard() {
        GridPane grid = new GridPane();

        //grid.getColumnConstraints().add(new ColumnConstraints(gridWidth));
        //grid.getRowConstraints().add(new RowConstraints(gridHeight));
        Random rand = new Random();
        Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};

        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 40; col++) {
                int n = rand.nextInt(5);
                Rectangle rec = new Rectangle();
                rec.setWidth(20);
                rec.setHeight(20);
                rec.setFill(Color.YELLOW);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                grid.getChildren().addAll(rec);
            }
        }
        return grid;
    }
    
    public class Point {
        public int x,y;
        Point(int xx, int yy) {            
            x = xx; y = yy;
        }
    }
    
    public class Player {
        Point position;
        Player() {
            position = new Point(10,20);        
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
