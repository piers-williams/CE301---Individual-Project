package Project.Game;

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
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_M:
                        Main.INFLUENCE_MAP.cycleFaction();
                        break;
                    case Keyboard.KEY_P:
                        main.togglePause();
                        break;
                    case Keyboard.KEY_A:
                        main.shiftView(new Vector2D(50, 0));
                        break;
                    case Keyboard.KEY_W:
                        main.shiftView(new Vector2D(0, -50));
                        break;
                    case Keyboard.KEY_S:
                        main.shiftView(new Vector2D(0, 50));
                        break;
                    case Keyboard.KEY_D:
                        main.shiftView(new Vector2D(-50, 0));
                        break;
                }
            }
        }
    }
}
