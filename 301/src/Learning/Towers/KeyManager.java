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
                switch(Keyboard.getEventKey()){
                    case Keyboard.KEY_M :
                        Main.INFLUENCE_MAP.cycleFaction();
                        break;
                }
            }
        }
    }
}
