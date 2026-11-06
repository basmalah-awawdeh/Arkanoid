
import java.awt.Color;
import java.util.ArrayList;

import java.util.List;

import biuoop.DrawSurface;

/**
 * The type Level 2.
 */
public class Level2 implements LevelInformation {
    private static final int NUMBER_OF_BALLS = 10;
    private static final int PADDLE_SPEED = 5;
    private static final int PADDLE_WIDTH = 700;
    private static final String LEVEL_NAME = "Wide Easy";
    private static final Color BACKGROUND_COLOR = Color.CYAN;
    private static final Color[] BLOCK_COLORS = {Color.RED, Color.ORANGE,
            Color.YELLOW, Color.GREEN, Color.BLUE};
    private static final int BLOCK_WIDTH = 52;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCKS_IN_ROW = 15;
    private static final int BLOCKS_START_X = (800 - BLOCK_WIDTH
            * BLOCKS_IN_ROW) / 2;
    private static final int BLOCKS_START_Y = 200;
    private static final int NUMBER_OF_BLOCKS_TO_REMOVE = BLOCKS_IN_ROW;
    private static final int ZERO = 0;
    private static final int TWO = 2;
    private static final int ONE = 1;

    @Override
    public int numberOfBalls() {
        return NUMBER_OF_BALLS;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        int numBallsToRight = NUMBER_OF_BALLS / TWO;
        int numBallsToLeft = NUMBER_OF_BALLS - numBallsToRight;

        double angleStepToRight = 180.0 / (numBallsToRight + ONE);
        double angleStepToLeft = 180.0 / (numBallsToLeft + ONE);

        // Add balls going to the right.
        for (int i = ZERO; i < numBallsToRight; i++) {
            double angle = -90.0 + (i + ONE) * angleStepToRight;
            double radians = Math.toRadians(angle);
            int dx = (int) (Math.cos(radians) * 5);
            int dy = (int) (Math.sin(radians) * 5);
            velocities.add(new Velocity(dx, dy));
        }

        // Add balls going to the left.
        for (int i = ZERO; i < numBallsToLeft; i++) {
            double angle = 90.0 - (i + ONE) * angleStepToLeft;
            double radians = Math.toRadians(angle);
            int dx = (int) (Math.cos(radians) * -5);
            int dy = (int) (Math.sin(radians) * -5);
            velocities.add(new Velocity(dx, dy));
        }

        return velocities;
    }


    @Override
    public int paddleSpeed() {
        return PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return LEVEL_NAME;
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface surface) {
                // Draw the background on the screen.
                surface.setColor(BACKGROUND_COLOR);
                surface.fillRectangle(ZERO, ZERO, surface.getWidth(),
                        surface.getHeight());
                int sunX = BLOCKS_START_X + BLOCK_WIDTH * BLOCKS_IN_ROW / 6;
                int sunY = BLOCKS_START_Y - BLOCK_HEIGHT - 100;
                // Draw the sun's rays.
                surface.setColor(Color.YELLOW);
                int raysPerBlock = 10;
                int totalLines = BLOCKS_IN_ROW * raysPerBlock;
                double angleStep = Math.PI / totalLines;
                int startX = 10;
                int endX = 686;
                int stepX = (endX - startX) / totalLines;
                for (int i = ZERO; i < totalLines; i++) {
                    double angle = Math.PI / 2 + (i * angleStep);
                    int x1 = sunX;
                    int y1 = sunY;
                    int x2 = startX + i * stepX;
                    int y2 = BLOCKS_START_Y;
                    surface.drawLine(x1, y1, x2, y2);
                }

                // Draw the sun itself.
                int sunRadius = 50;

                // Draw the outer circle of the sun.
                surface.setColor(Color.ORANGE);
                surface.fillCircle(sunX, sunY, sunRadius);

                // Draw the inner circle of the sun.
                int innerRadius = sunRadius - 10;
                surface.setColor(Color.YELLOW);
                surface.fillCircle(sunX, sunY, innerRadius);
                // Draw the clouds.
                int cloud1X = 650;
                int cloud1Y = 150;
                int cloud2X = 400;
                int cloud2Y = 90;
                int cloudRadius = 30;

                // Draw cloud 1.
                surface.setColor(Color.WHITE);
                surface.fillCircle(cloud1X, cloud1Y, cloudRadius);
                surface.fillCircle(cloud1X + cloudRadius, cloud1Y,
                        cloudRadius);
                surface.fillCircle(cloud1X + TWO * cloudRadius, cloud1Y,
                        cloudRadius);


                // Draw cloud 2.
                surface.fillCircle(cloud2X, cloud2Y, cloudRadius);
                surface.fillCircle(cloud2X + cloudRadius, cloud2Y,
                        cloudRadius);
                surface.fillCircle(cloud2X + TWO * cloudRadius, cloud2Y,
                        cloudRadius);

            }

            @Override
            public void timePassed() {
                // No animation or time-based behavior for the background
            }
        };
    }


    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (int i = ZERO; i < BLOCKS_IN_ROW; i++) {
            Color blockColor = BLOCK_COLORS[i % BLOCK_COLORS.length];
            Block block = new Block(new Rectangle(new Point(BLOCKS_START_X
                    + i * BLOCK_WIDTH, BLOCKS_START_Y),
                    BLOCK_WIDTH, BLOCK_HEIGHT), blockColor);
            blocks.add(block);
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return NUMBER_OF_BLOCKS_TO_REMOVE;
    }
}
