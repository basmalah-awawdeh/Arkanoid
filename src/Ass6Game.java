
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * The type Ass 6 game.
 */
public class Ass6Game {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        List<LevelInformation> levels = new ArrayList<>();
        List<String> validLevelNumbers = Arrays.asList("1", "2", "3");

        // Remove invalid arguments and sort the valid level numbers
        List<String> sortedArgs = new ArrayList<>();
        for (String arg : args) {
            if (validLevelNumbers.contains(arg)) {
                sortedArgs.add(arg);
            }
        }

        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner animationRunner = new AnimationRunner(gui,
                60);
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();

        GameFlow game = new GameFlow(gui, animationRunner, keyboardSensor);

        // Play all levels if no specific level numbers were provided
        if (sortedArgs.isEmpty()) {
            levels.add(new Level1());
            levels.add(new Level2());
            levels.add(new Level3());
        } else {
            for (String levelNum : sortedArgs) {
                if (levelNum.equals("1")) {
                    levels.add(new Level1());
                } else if (levelNum.equals("2")) {
                    levels.add(new Level2());
                } else if (levelNum.equals("3")) {
                    levels.add(new Level3());
                }
            }
        }

        game.runLevels(levels);
        gui.close();
    }
}
