package Learning.KeyBoardHandling.Input;

import org.lwjgl.input.Keyboard;


public enum Keys {
    /**
     * Global keys
     */
    PAUSE(Keyboard.KEY_P),
    SHOOTING(Keyboard.KEY_S),
    /**
     * Influence Keys
     */
    INFLUENCE_UP(Keyboard.KEY_UP),
    INFLUENCE_DOWN(Keyboard.KEY_DOWN),
    INFLUENCE_TOGGLE(Keyboard.KEY_I);
    private Integer keyValue;


    Keys(Integer defaultKey) {
        this.keyValue = defaultKey;
    }

    public Integer getKey() {
        return keyValue;
    }

    public void setKey(Integer newKey) {
        keyValue = newKey;
    }
}
