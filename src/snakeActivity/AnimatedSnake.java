package snakeActivity;

import Graphics.*;
import Graphics.Point;
import Graphics.Rectangle;

import java.awt.*;
import java.util.Random;

public class AnimatedSnake {

    private static final double SEGMENT_SIZE = 50;
    private static double degree = 0;
    private static double radius = 0;
    private Random random;
    private CanvasWindow canvas;

    private GraphicsObject[] snakeSegments;

    /**
     * Constructor
     * @param width - the width of the window
     * @param height - the height of the window
     * @param numberOfSegments - the number of GraphicsoObjects making up individual segments for the snake.
     */
    public AnimatedSnake(int width, int height, int numberOfSegments) {
        canvas = new CanvasWindow("Snake Animation", width, height);
        random = new Random();
        
        snakeSegments = new GraphicsObject[numberOfSegments];

        generateSnake();
    }

    /**
     * This method should fill in the array storing the graphical objects of the snake
     * (Note: the array should not be created in this method).
     *
     * For now this should just get random shapes, however, once you get everything working
     * Feel free to customize this to, for example, have a specific snake head and tail.
     */
    protected void generateSnake() {
        for (int i = 0; i < snakeSegments.length; i++) {
            GraphicsObject segment = getRandomShape();
            snakeSegments[i] = segment;
            canvas.add(segment);
        }
    }

    /**
     * This method makes the snake animate.
     * Once you finish making the snake work you can override this method to make the
     * snake do more interesting things.
     * Right now it just kinda slithers around in a circle.
     */
    public void run() {
        int i = 0;
        double step = 0.015;
        radius = Math.min(canvas.getWidth()/2, canvas.getHeight()/2);
        radius = radius - SEGMENT_SIZE;

        //noinspection InfiniteLoopStatement
        canvas.animate(() -> {
            // infinite animation loop!
            // increase the angle for the movement
            degree += step;

            // make the radius change as the snake moves
            double thisR = radius+Math.sin(8*degree)*SEGMENT_SIZE;

            // compute x and y values
            double x = Math.sin(degree)*thisR - SEGMENT_SIZE/2 + canvas.getWidth()/2;
            double y = Math.cos(degree)*thisR - SEGMENT_SIZE/2 + canvas.getHeight()/2;

            // move the head of the snake to this position, and have each segment follow.
            snakeMove(x,y);
        });
    }

    /**
     * Move the snake by updating the positions of the segments
     */
    protected void snakeMove(double x, double y) {
        Point positionA = new Point(x,y);
        Point positionB = new Point(0,0);
        for (GraphicsObject segment : snakeSegments ) {
            positionB = segment.getPosition();
            segment.setPosition(positionA);
            positionA = positionB;
        }
    }

    /**
     * Create and return a random small graphics object.
     * The new shape will always be in the top left corner.
     */
    protected GraphicsObject getRandomShape() {
        int randomInt = random.nextInt(5);
        if (randomInt == 0) {
            Rectangle rectangle = new Rectangle(0, 0, SEGMENT_SIZE, SEGMENT_SIZE);
            rectangle.setFilled(true);
            rectangle.setFillColor(getRandomColor());
            return rectangle;
        } else { // r == 1
            Ellipse ellipse = new Ellipse(0, 0, SEGMENT_SIZE, SEGMENT_SIZE);
            ellipse.setFilled(true);
            ellipse.setFillColor(getRandomColor());
            return ellipse;
        }
    }

    protected Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static void main(String[] args){
        AnimatedSnake snake = new AnimatedSnake(800, 600, 10);
        snake.run();
    }
}
