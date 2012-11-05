package Learning.KeyBoardHandling.Input;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 05/11/12
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class KeyManager {

    HashMap<Integer, Keys> keyAssignments;

    public void HandleKeyboard() {

    }

    private Keys translateKey(Integer input) {
        if (keyAssignments.containsKey(input)) {
            return keyAssignments.get(input);
        }
        return null;
    }

    public void loadKeys(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
            }
        } catch (IOException ioe) {

        }

        keyAssignments.clear();
        for (Keys key : Keys.values()) {
            keyAssignments.put(key.getKey(), key);
        }
    }
}
