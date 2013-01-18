package Learning.Towers;

import org.lwjgl.input.Keyboard;

/**
 *
 */
public class KeyManager {
    Main main;

    public KeyManager(Main main) {
        this.main = main;
    }

    // Call at end of round
    public void update() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                    System.out.println("KEY M DOWN");
                    Main.INFLUENCE_MAP.cycleFaction();
                } else {
                    System.out.println("KEY M Released");
                }
            }
        }
    }
}
