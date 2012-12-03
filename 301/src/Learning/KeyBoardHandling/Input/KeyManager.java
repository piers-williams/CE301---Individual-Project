package Learning.KeyBoardHandling.Input;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class KeyManager {

    HashMap<Integer, Keys> inputToKeys;
    HashMap<Keys, Integer> keysToInput;

    public KeyManager() {
        inputToKeys = new HashMap<>(20);
        keysToInput = new HashMap<>(20);
    }

    public void HandleKeyboard() {

    }

    private Keys translateKey(Integer input) {
        if (inputToKeys.containsKey(input)) {
            return inputToKeys.get(input);
        }
        return null;
    }

    public void changeKey(Keys action, Integer key) {

    }

    public void loadKeys(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("\\s+");
                if (line.length == 2) {
                    Keys.valueOf(line[0]).setKey(Character.getNumericValue(line[1].charAt(0)));
                }
            }
        } catch (IOException ioe) {

        }

        updateKeyAssignments();
    }

    private void updateKeyAssignments() {
        inputToKeys.clear();
        for (Keys key : Keys.values()) {
            inputToKeys.put(key.getKey(), key);
        }
    }
}
