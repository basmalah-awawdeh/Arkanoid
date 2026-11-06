
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level 1.
 */
public class Level1 implements LevelInformation {
    private static final int NUMBER_OF_BALLS = 1;
    private static final int PADDLE_SPEED = 5;
    private static final int PADDLE_WIDTH = 100;
    private static final String LEVEL_NAME = "Direct Hit";
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color BLOCK_COLOR = Color.RED;
    private static final int BLOCK_WIDTH = 30;
    private static final int BLOCK_HEIGHT = 30;
    private static final int BLOCKS_IN_ROW = 1;
    private static final Color[] BLOCK_COLORS = {Color.RED};
    private static final int ZERO = 0;
    private static final int TWO = 2;
    /**
     * Instantiates a new Level 1.
     */
    public Level1() {
        // Empty constructor.
    }

    @Override
    public int numberOfBalls() {
        return NUMBER_OF_BALLS;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(new Velocity(ZERO, -5));
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

                // Draw the target block.
                int centerX = surface.getWidth() / TWO;
                int centerY = 150;
                int targetRadius = 80;
                int blockWidth = 20;
                int blockHeight = 80;

                // Draw the target.
                surface.setColor(BLOCK_COLOR);
                surface.drawCircle(centerX, centerY, targetRadius);
                surface.drawCircle(centerX, centerY, targetRadius
                        - blockWidth);
                surface.drawCircle(centerX, centerY, targetRadius
                        - 2 * blockWidth);

                // Draw the lines around the target.
                surface.drawLine(centerX - targetRadius - blockHeight / TWO,
                        centerY, centerX + targetRadius + blockHeight / TWO,
                        centerY);
                surface.drawLine(centerX, centerY - targetRadius
                                - blockHeight / TWO, centerX, centerY
                        + targetRadius + blockHeight / TWO);
            }

            @Override
            public void timePassed() {
                // No animation or time-based behavior for the background.
            }
        };
    }


    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // Add blocks in rows.
        int blocksStartX = 385;
        int blocksStartY = 135;

        for (int i = ZERO; i < BLOCKS_IN_ROW; i++) {
            Color blockColor = BLOCK_COLORS[i % BLOCK_COLORS.length];
            Block block = new Block(new Rectangle(new Point(blocksStartX
                    + i * BLOCK_WIDTH, blocksStartY),
                    BLOCK_WIDTH, BLOCK_HEIGHT), blockColor);
            blocks.add(block);
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
