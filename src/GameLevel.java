

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;

import java.util.List;

/**
 * The type Game level.
 */
public class GameLevel implements Animation {
    private CountdownAnimation countdownAnimation;

    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter score;
    private LevelInformation levelInfo;
    private Counter blockCounter;
    private Counter ballCounter;
    private boolean levelFinished;
    private PauseScreen pauseScreen;
    private boolean paused;
    private KeyboardSensor keyboard;
    private int currentLevel;
    private int remainingBalls;
    private int remainingBlocks;
    private static final int ZERO = 0;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int UP = 10;
    private static final int SIDE = 20;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int HUNDRED = 100;

    /**
     * Instantiates a new Game level.
     *
     * @param levelInfo the level info
     * @param gui       the gui
     */
    public GameLevel(LevelInformation levelInfo, GUI gui) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.sleeper = new Sleeper();
        this.score = new Counter();
        this.runner = new AnimationRunner(this.gui, 60);
        this.levelInfo = levelInfo;
        this.blockCounter = new Counter();
        this.ballCounter = new Counter();
        this.levelFinished = false;
        this.paused = false;
        this.pauseScreen = new PauseScreen(gui.getKeyboardSensor());
        this.keyboard = gui.getKeyboardSensor();
        this.running = true;
        this.remainingBalls = ZERO;
        this.remainingBlocks = ZERO;
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize.
     */
    public void initialize() {
        // Create side blocks
        Block[] sideBlocks = new Block[4];
        sideBlocks[ZERO] = new Block(new Rectangle(new Point(ZERO, SIDE),
                WIDTH, UP), Color.DARK_GRAY);
        sideBlocks[ONE] = new Block(new Rectangle(new Point(ZERO, ZERO),
                UP, HEIGHT), Color.DARK_GRAY);
        sideBlocks[TWO] = new Block(new Rectangle(new Point(WIDTH - UP,
                ZERO), UP, HEIGHT), Color.DARK_GRAY);
        sideBlocks[THREE] = new Block(new Rectangle(new Point(ZERO, ZERO),
                WIDTH, SIDE), Color.WHITE);
        for (Block sb : sideBlocks) {
            sprites.addSprite(sb);
            sb.addToGame(this);
        }
        // Create countdown animation.
        countdownAnimation = new CountdownAnimation(TWO, THREE,
                sprites);
        // Create death-region block so balls disappear when touching it.
        Block deathRegion = new Block(new Rectangle(new Point(ZERO,
                gui.getDrawSurface().getHeight()),
                gui.getDrawSurface().getWidth(), 1), Color.BLUE);
        BallRemover ballRemover = new BallRemover(this, ballCounter);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
        environment.addCollidable(deathRegion);
        sprites.addSprite(deathRegion);

        // Create blocks.
        List<Block> blocks = levelInfo.blocks();
        for (Block block : blocks) {
            block.addHitListener(new BlockRemover(this, blockCounter,
                    ballCounter));
            // Add ScoreTrackingListener to the block.
            block.addHitListener(new ScoreTrackingListener(score));
            block.addToGame(this);
            environment.addCollidable(block);
            remainingBlocks++;
            blockCounter.increase(ONE);
        }

        // Create paddle.
        Paddle paddle = new Paddle(new Rectangle(new
                Point(gui.getDrawSurface().getWidth() / TWO
                - levelInfo.paddleWidth() / TWO, 570),
                levelInfo.paddleWidth(), SIDE), Color.YELLOW, keyboard,
                levelInfo.paddleSpeed());
        paddle.addToGame(this);

        // Create balls.
        List<Velocity> ballVelocities = levelInfo.initialBallVelocities();
        for (Velocity velocity : ballVelocities) {
            Ball ball = new Ball(new
                    Point(gui.getDrawSurface().getWidth() / TWO,
                    gui.getDrawSurface().getHeight() - 50), 5,
                    Color.WHITE, environment, this);
            ball.setVelocity(velocity);
            ball.addToGame(this);
            remainingBalls++;
            ballCounter.increase(ONE);
        }
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Sets current level.
     *
     * @param level the level
     */
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    /**
     * Run.
     */
    public void run() {
        setCurrentLevel(ONE);
        // Run the countdown.
        this.runner.run(this.countdownAnimation);
        while (true) {
            // Initialize current level.
            LevelInformation currentLevelInfo;
            if (score.getValue() < 105) {
                currentLevel = ONE;
            } else if (score.getValue() < 280) {
                currentLevel = TWO;
            } else {
                currentLevel = THREE;
            }

            if (currentLevel == ONE) {
                currentLevelInfo = new Level1();
            } else if (currentLevel == TWO) {
                currentLevelInfo = new Level2();
            } else if (currentLevel == THREE) {
                currentLevelInfo = new Level3();
            } else {
                break;
            }

            // Set the level information for the game level.
            this.setLevelInfo(currentLevelInfo);

            // Initialize and run the current level.
            this.initialize();
            this.runner.run(this);
            // Reset level.
            this.resetLevel();

            if (this.shouldStop()) {
                break;
            }
        }

        if (blockCounter.getValue() == ZERO) {
            // Add 100 to score when blocks are cleaned.
            score.increase(HUNDRED);
            levelFinished = true;
        }
        if (ballCounter.getValue() == ZERO) {
            levelFinished = true;
        }

        // Check if the level is finished.
        if (levelFinished) {
            if (currentLevel == THREE && (blockCounter.getValue() != ZERO
                    || ballCounter.getValue() == ZERO)) {
                showGameOverScreen();
            } else if (currentLevel == THREE) {
                showWinnerScreen();
            }
        }

    }


    /**
     * Show the game over screen.
     */
    private void showGameOverScreen() {
        GUI endScreenGui = new GUI("Game Over", WIDTH, HEIGHT);
        DrawSurface endScreenSurface = endScreenGui.getDrawSurface();
        endScreenSurface.setColor(Color.RED);
        endScreenSurface.drawText(HUNDRED, endScreenSurface.getHeight()
                        / TWO, "Game Over. Your score is "
                + score.getValue(), 32);
        endScreenGui.show(endScreenSurface);

        // Wait for space key press.
        KeyboardSensor keyboardSensor = endScreenGui.getKeyboardSensor();
        while (!keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
            sleeper.sleepFor(HUNDRED);
        }

        endScreenGui.close();
    }

    /**
     * Show the winner screen.
     */
    private void showWinnerScreen() {
        GUI endScreenGui = new GUI("Winner!", WIDTH, HEIGHT);
        DrawSurface endScreenSurface = endScreenGui.getDrawSurface();
        endScreenSurface.setColor(Color.GREEN);
        endScreenSurface.drawText(HUNDRED, endScreenSurface.getHeight()
                        / TWO, "You Win! Your score is "
                + score.getValue(), 32);
        endScreenGui.show(endScreenSurface);

        // Wait for space key press.
        KeyboardSensor keyboardSensor = endScreenGui.getKeyboardSensor();
        while (!keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
            sleeper.sleepFor(HUNDRED);
        }

        endScreenGui.close();
    }

    /**
     * Resets the level for the next iteration.
     */
    private void resetLevel() {
        this.running = true;
        this.levelFinished = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw the background.
        levelInfo.getBackground().drawOn(d);

        // Check if 'P' key is pressed.
        if (this.keyboard.isPressed("p") && !paused) {
            paused = true;
        }

        // If paused, show the pause screen and return.
        if (paused) {
            pauseScreen.doOneFrame(d);
            if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                paused = false;
            }
            return;
        }

        if (blockCounter.getValue() == ZERO) {
            // Add 100 when finishing a level.
            score.increase(HUNDRED);
            levelFinished = true;
        }
        if (ballCounter.getValue() == ZERO) {
            levelFinished = true;
        }

        // Check if the level is finished.
        if (levelFinished) {
            if (blockCounter.getValue() == ZERO && currentLevel == THREE) {
                showWinnerScreen();
            } else if (ballCounter.getValue() == ZERO && currentLevel
                    == THREE) {
                showGameOverScreen();
            }
            this.running = false;
            return;
        }

        // Check if the countdown animation should be displayed.
        if (!countdownAnimation.shouldStop()) {
            countdownAnimation.doOneFrame(d);
        } else {
            // Draw all the sprites.
            sprites.drawAllOn(d);
            sprites.notifyAllTimePassed();
        }

        // Draw the score on the screen.
        d.setColor(Color.BLACK);
        int scoreX = d.getWidth() / TWO;
        int scoreY = SIDE;
        int scoreSize = 16;
        String scoreText = "Score: " + score.getValue();
        d.drawText(scoreX, scoreY, scoreText, scoreSize);

        // Draw the level name on the screen.
        int levelNameX = d.getWidth() - 200;
        int levelNameY = SIDE;
        String levelName = "Level Name: " + levelInfo.levelName();
        d.drawText(levelNameX, levelNameY, levelName, scoreSize);
    }


    @Override
    public boolean shouldStop() {
        return !this.running || getRemainingBalls() == ZERO;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * Sets score.
     *
     * @param scoreCounter the score counter
     */
    public void setScore(Counter scoreCounter) {
        this.score = scoreCounter;
    }

    /**
     * Gets ball counter.
     *
     * @return the ball counter
     */
    public Counter getBallCounter() {
        return this.ballCounter;
    }

    /**
     * Sets level info.
     *
     * @param levelInfo the level info
     */
    public void setLevelInfo(LevelInformation levelInfo) {
        this.levelInfo = levelInfo;
    }

    /**
     * Gets remaining balls.
     *
     * @return the remaining balls
     */
    public int getRemainingBalls() {
        return remainingBalls;
    }

    /**
     * Sets remaining balls.
     *
     * @param count the count
     */
    public void setRemainingBalls(int count) {
        remainingBalls = count;
    }

    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks
     */
    public int getRemainingBlocks() {
        return remainingBlocks;
    }

    /**
     * Sets remaining blocks.
     *
     * @param count the count
     */
    public void setRemainingBlocks(int count) {
        remainingBlocks = count;
    }
}


