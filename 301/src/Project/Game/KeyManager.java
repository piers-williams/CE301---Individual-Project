package Project.Game;

import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
        // Handle the mouse
        if (Mouse.isButtonDown(0)) {
            if (main.blueprintToBuild != null) {
                Entity entity = EntityFactory.getBlueprint(Main.HUMAN_FACTION, new Vector2D(Mouse.getX(), Mouse.getY()), main.blueprintToBuild);
                Main.GAME_LOOP.addEntity(entity);
                main.blueprintToBuild = null;
            }
        }

    }
}
