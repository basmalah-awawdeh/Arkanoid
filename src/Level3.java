
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level 3.
 */
public class Level3 implements LevelInformation {
    private static final int NUMBER_OF_BALLS = 2;
    private static final int PADDLE_SPEED = 6;
    private static final int PADDLE_WIDTH = 100;
    private static final String LEVEL_NAME = "Green 3";
    private static final Color BACKGROUND_COLOR = Color.GREEN;
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 30;
    private static final int GUI_HEIGHT = 600;
    private static final int GUI_WIDTH = 800;
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
        velocities.add(new Velocity(2, -3));
        velocities.add(new Velocity(-2, -3));
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
            public void drawOn(DrawSurface d) {
                // Draw the background.
                d.setColor(BACKGROUND_COLOR);
                d.fillRectangle(ZERO, ZERO, GUI_WIDTH, GUI_HEIGHT);

                // Draw the tower.
                int towerWidth = 100;
                int towerHeight = 180;
                int towerX = 20;
                int towerY = GUI_HEIGHT - towerHeight;
                d.setColor(Color.BLACK);
                d.fillRectangle(towerX, towerY, towerWidth, towerHeight);

                // Draw the blocks inside the tower.
                int numBlocksInRow = 5;
                int numBlocksInColumn = 5;
                Color blockColor = Color.WHITE;
                int blockWidth = 10;
                int blockHeight = 25;
                int gapSize = 7;
                int startX = towerX + (towerWidth - (numBlocksInRow
                        * blockWidth + (numBlocksInRow - ONE) * gapSize)) / TWO;
                int startY = towerY + (towerHeight
                        - (numBlocksInColumn * blockHeight
                        + (numBlocksInColumn - ONE) * gapSize)) / TWO;

                for (int j = ZERO; j < numBlocksInColumn; j++) {
                    for (int i = ZERO; i < numBlocksInRow; i++) {
                        int blockX = startX + i * (blockWidth + gapSize);
                        int blockY = startY + j * (blockHeight + gapSize);

                        d.setColor(blockColor);
                        d.fillRectangle(blockX, blockY, blockWidth, blockHeight);
                    }
                }

                // Draw additional elements in the background.
                d.setColor(Color.DARK_GRAY);
                d.fillRectangle(towerX + 35, towerY - 50, 30, 50);
                d.setColor(Color.GRAY);
                d.fillRectangle(towerX + 45, towerY - 200, 10, 150);

                // Draw balls.
                int ballX = towerX + 50;
                int ballY = towerY - 200;
                d.setColor(Color.ORANGE);
                d.fillCircle(ballX, ballY, 13);
                d.setColor(Color.RED);
                d.fillCircle(ballX, ballY, 9);
                d.setColor(Color.WHITE);
                d.fillCircle(ballX, ballY, 3);
            }

            public void timePassed() {
                // Add any time-based logic here
            }
        };
    }
    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // Create and add blocks to the game environment.
        int blockWidth = 50;
        int blockHeight = 30;
        int startX = 10;
        int startY = 100;
        int rows = 5;
        int blocksInRow = 10;

        for (int i = ZERO; i < rows; i++) {
            Color color;
            if (i == ZERO) {
                color = Color.GRAY;
            } else if (i == ONE) {
                color = Color.RED;
            } else if (i == TWO) {
                color = Color.YELLOW;
            } else if (i == 3) {
                color = Color.CYAN;
            } else if (i == 4) {
                color = Color.PINK;
            } else {
                color = Color.GREEN;
            }

            for (int j = ZERO; j < blocksInRow; j++) {
                int x = GUI_WIDTH - (startX + (j + ONE) * blockWidth);
                int y = startY + i * blockHeight;
                Rectangle blockRect = new Rectangle(new Point(x, y),
                        blockWidth, blockHeight);
                Block block = new Block(blockRect, color);
                blocks.add(block);
            }
            blocksInRow--;
        }
        return blocks;
    }


    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
