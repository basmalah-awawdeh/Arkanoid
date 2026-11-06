
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {
    private static final int ZERO = 0;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter score;
    private Sleeper sleeper;
    private int currentLevel;
    private int remainingBlocks;

    /**
     * Instantiates a new Game flow.
     *
     * @param gui             the gui
     * @param animationRunner the animation runner
     * @param keyboardSensor  the keyboard sensor
     */
    public GameFlow(GUI gui, AnimationRunner animationRunner,
                    KeyboardSensor keyboardSensor) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboardSensor;
        this.score = new Counter();
        this.sleeper = new Sleeper();
        this.currentLevel = ZERO;
        this.remainingBlocks = ZERO;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        boolean allLevelsCompleted = true;

        for (LevelInformation levelInfo : levels) {
            currentLevel++;

            GameLevel level = new GameLevel(levelInfo, gui);
            level.setScore(score);
            level.setRemainingBlocks(remainingBlocks);
            level.initialize();

            while (!level.shouldStop()) {
                animationRunner.run(level);
                sleeper.sleepFor(50);
            }

            remainingBlocks = level.getRemainingBlocks();

            if (remainingBlocks > ZERO) {
                // Player lost the level
                allLevelsCompleted = false;
                break;
            }
        }
        String endMessage;
        if (currentLevel == levels.size() && remainingBlocks == ZERO) {
            endMessage = "You Win! Your score is " + score.getValue();
        } else {
            endMessage = "Game Over. Your score is " + score.getValue();
        }

        showEndScreen(endMessage);

        if (allLevelsCompleted && remainingBlocks == ZERO) {
            // Player cleared all levels
            remainingBlocks = ZERO;
        } else {
            // Player lost or exited the game
            remainingBlocks = -1;
        }
    }


    /**
     * Run.
     */
    public void run() {
        List<LevelInformation> levels = createLevels();

        String endMessage;
        if (currentLevel == levels.size() && remainingBlocks == ZERO) {
            endMessage = "You Win! Your score is " + score.getValue();
        } else {
            endMessage = "Game Over. Your score is " + score.getValue();
        }

        showEndScreen(endMessage);
    }


    private void showEndScreen(String message) {
        Animation endScreenAnimation = new
                KeyPressStoppableAnimation(keyboardSensor,
                KeyboardSensor.SPACE_KEY, new EndScreenAnimation(message));

        AnimationRunner endScreenRunner = new AnimationRunner(gui,
                60);

        endScreenRunner.run(endScreenAnimation);
    }


    private List<LevelInformation> createLevels() {
        List<LevelInformation> levels = new ArrayList<>();
        levels.add(new Level1());
        levels.add(new Level2());
        levels.add(new Level3());
        return levels;
    }
}


