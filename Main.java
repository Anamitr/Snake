package canvastest;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    static Scene mainScene;
    static GraphicsContext graphicsContext;
    static int squareSize = 30;
    final static int WIDTH = 40*squareSize;
    final static int HEIGHT = 25*squareSize;
    
    static int[] playerPositionX, playerPositionY;
    static int size = 3;
    final static int MAX_SIZE = 50;
    
    static int acornPositionX, acornPositionY;
    static Random rand = new Random();
    
    static public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
    static Direction direction = Direction.LEFT;
    static int speed = 100;
    
    static AnimationTimer movement = new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {
            move();
        }
    };
    static AnimationTimer awaitingOrders = new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {
            changeDirection();
        }
    };
    
    VBox info = new VBox();
    Label gameOver = new Label("Game Over");

//    static Image left;
//    static Image leftGreen;
//    static Image right;
//    static Image rightGreen;

    static HashSet<String> currentlyActiveKeys;

    @Override
    public void start(Stage mainStage)
    {
        
        mainStage.setTitle("Snake");
        Group root = new Group();
        mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        
        gameOver.setVisible(false);
        info.getChildren().add(gameOver);
        root.getChildren().add(info);
        
        
        graphicsContext = canvas.getGraphicsContext2D();
        drawBoard();
        preparePlayer();
        drawPlayer();
        dropAcorn();        
        prepareActionHandlers();
        
        
        movement.start();        
        awaitingOrders.start();
        mainStage.show();        
    }
    
    public static void delay(int n) {
        try {
            Thread.sleep(n);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }    
    }
    private static void prepareActionHandlers() {
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }
    private static void preparePlayer() {
        playerPositionX = new int[100];
        playerPositionX[0] = 15;
        playerPositionX[1] = 16;
        playerPositionX[2] = 17;
        playerPositionX[3] = 18;
        playerPositionY = new int[100];
        playerPositionY[0] = 7;
        playerPositionY[1] = 7;
        playerPositionY[2] = 7;
        playerPositionY[3] = 7;
        acornPositionX = playerPositionX[0];
        acornPositionY = playerPositionY[0];
    }
    private static void move() {
        if (direction == Direction.LEFT) {
            for(int i = size; i > 0; i--) {
                playerPositionX[i] = playerPositionX[i-1];
                playerPositionY[i] = playerPositionY[i-1];
            }
            playerPositionX[0]--;
            if(playerPositionX[0] < 0) playerPositionX[0] = (WIDTH/squareSize)-1;
            checkCollision();
            checkHaps();
            drawPlayer();
            delay(speed);
        }
        else if (direction == Direction.RIGHT) {
            for(int i = size; i > 0; i--) {
                playerPositionX[i] = playerPositionX[i-1];
                playerPositionY[i] = playerPositionY[i-1];
            }
            playerPositionX[0]++;
            if(playerPositionX[0] > (WIDTH/squareSize)-1) playerPositionX[0] = 0;
            checkCollision();
            checkHaps();
            drawPlayer();
            delay(speed);            
        }
        else if (direction == Direction.UP) {
            for(int i = size; i > 0; i--) {
                playerPositionX[i] = playerPositionX[i-1];
                playerPositionY[i] = playerPositionY[i-1];
            }
            playerPositionY[0]--;
            if(playerPositionY[0] < 0) playerPositionY[0] = (HEIGHT/squareSize)-1;
            checkCollision();
            checkHaps();
            drawPlayer();
            delay(speed);
        }
        else if (direction == Direction.DOWN) {
            for(int i = size; i > 0; i--) {
                playerPositionX[i] = playerPositionX[i-1];
                playerPositionY[i] = playerPositionY[i-1];
            }
            playerPositionY[0]++;
            if(playerPositionY[0] > (HEIGHT/squareSize)-1) playerPositionY[0] = 0;
            checkCollision();
            checkHaps();
            drawPlayer();
            delay(speed);
        }
    }
    private static void changeDirection() {
        if (currentlyActiveKeys.contains("LEFT")) {
            if(direction != Direction.RIGHT) direction = Direction.LEFT;
        }
        else if (currentlyActiveKeys.contains("RIGHT")) {
            if(direction != Direction.LEFT) direction = Direction.RIGHT;         
        }
        else if (currentlyActiveKeys.contains("UP")) {
            if(direction != Direction.DOWN) direction = Direction.UP;
        }
        else if (currentlyActiveKeys.contains("DOWN")) {
            if(direction != Direction.UP) direction = Direction.DOWN;
        }
    }    
    static private void drawBoard() {
        graphicsContext.setFill(Color.YELLOW);        
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 25; j++) {
                graphicsContext.fillRoundRect(i*squareSize, j*squareSize, squareSize, squareSize,0,0);
            }
        }
    }
    static private void drawPlayer() {
        graphicsContext.setFill(Color.GREEN);
        for(int i = 0; i < size; i++)
        {
            graphicsContext.fillRoundRect(playerPositionX[i]*squareSize, playerPositionY[i]*squareSize, squareSize, squareSize,0,0);
        }
        
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillRoundRect(playerPositionX[size]*squareSize, playerPositionY[size]*squareSize, squareSize, squareSize,0,0);
    }
    static private void dropAcorn() {
        while(acornPositionX == playerPositionX[0] && acornPositionY == playerPositionY[0]) {            
            acornPositionX = rand.nextInt((WIDTH/squareSize) + 1);
            acornPositionY = rand.nextInt((HEIGHT/squareSize) + 1);
        }        
        graphicsContext.setFill(Color.BROWN);
        graphicsContext.fillRoundRect(acornPositionX*squareSize, acornPositionY*squareSize, squareSize, squareSize,0,0);
    }
    static private void checkHaps() {
        if(acornPositionX == playerPositionX[0] && acornPositionY == playerPositionY[0]) {
            size++;
            dropAcorn();
            System.out.println(acornPositionX + "," + acornPositionY);
        }        
    }
    static private void checkCollision() {
        for(int i = 1; i < size; i++) {
            if(playerPositionX[i] == playerPositionX[0] && playerPositionY[i] == playerPositionY[0])  {
                movement.stop();
                awaitingOrders.stop();
                
                
            }
        }
    }
    //    private static void loadGraphics()
//    {
//        left = new Image(getResource("left.png"));
//        leftGreen = new Image(getResource("leftG.png"));
//
//        right = new Image(getResource("right.png"));
//        rightGreen = new Image(getResource("rightG.png"));
//    }

//    private static String getResource(String filename)
//    {
//        return Main.class.getResource(filename).toString();
//    }
}