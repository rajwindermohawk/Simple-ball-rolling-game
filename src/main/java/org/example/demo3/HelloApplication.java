package org.example.demo3;
//use space to run the game
//I RAJWINDER SINGH,000924676 certify that is work is my own work and I have not allowed anyone else to copy from it
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private int score = 0;
    private boolean gameOver = false;

    private double objectX = 50;
    private double objectY = 280;
    private double jumpHeight = 160;
    private double jumpSpeed = 3;
    private boolean isJumping = false;

    private double obstacleX = WIDTH;
    private double obstacleY = 425;
    private double obstacleWidth = 50;
    private double obstacleHeight = 55;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        VBox root = new VBox(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jumping Game");
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("SPACE")) {
                jump();
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    update();
                    render(gc);
                }
            }
        }.start();
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;
            double startY = objectY;
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    objectY -= jumpSpeed;
                    if (objectY <= startY - jumpHeight) {
                        stop();
                        new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                objectY += jumpSpeed;
                                if (objectY >= startY) {
                                    objectY = startY;
                                    isJumping = false;
                                    stop();
                                }
                            }
                        }.start();
                    }
                }
            }.start();
        }
    }

    private void update() {
        if (obstacleX < -obstacleWidth) {
            obstacleX = WIDTH;
            score++;
        } else {
            obstacleX -= 3;
        }

        if (objectX + 88 > obstacleX && objectX < obstacleX + obstacleWidth && objectY + 200 > obstacleY) {
            gameOver = true;
        }
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw sky
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, WIDTH, 300);

        // Draw sun
        gc.setFill(Color.YELLOW);
        gc.fillOval(350, 60, 40, 40);

        // Draw grass
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 300, WIDTH, 80);

        // Draw road
        gc.setFill(Color.GREY);
        gc.fillRect(0, 380, WIDTH, 120);

        // Draw object
        gc.setFill(Color.BLACK);
        gc.fillRect(objectX, objectY, 88, 200);

        // Draw obstacle
        gc.setFill(Color.RED);
        gc.fillRect(obstacleX, obstacleY, obstacleWidth, obstacleHeight);

        // Draw score
        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + score, 700, 30);

        if (gameOver) {
            gc.fillText("Game Over! Your score is: " + score, 300, 250);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
