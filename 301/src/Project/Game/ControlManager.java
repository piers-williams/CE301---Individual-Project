package Project.Game;

import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 */
public class ControlManager {
    Main main;
    public boolean paused;

    public ControlManager(Main main) {
        this.main = main;
    }

    // Call at end of round
    public void update() {
        if (!paused) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    switch (Keyboard.getEventKey()) {
                        case Keyboard.KEY_M:
                            Main.INFLUENCE_MAP.cycleFaction();
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
        // Handle the mouse
        if (Mouse.isButtonDown(0)) {
            if (main.blueprintToBuild != null) {
                Vector2D centralLocation = Vector2D.subtract(new Vector2D(Mouse.getX(), Mouse.getY()), Main.viewLocation);
                Entity entity = EntityFactory.getBlueprint(Main.HUMAN_FACTION, centralLocation, main.blueprintToBuild);
                Main.PLANNING_GRID.addBuilding(centralLocation, Main.BLUEPRINT_REGISTRY.get(main.blueprintToBuild).getSize(), Main.HUMAN_FACTION, false);
                Main.GAME_LOOP.addEntity(entity);
                main.blueprintToBuild = null;
            }
        }

    }
}
