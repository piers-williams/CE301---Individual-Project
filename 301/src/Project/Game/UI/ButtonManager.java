package Project.Game.UI;

import Project.Game.Vector2D;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;

/**
 * Responsible for storing and dealing with all Buttons
 */
public class ButtonManager extends Widget {

    private String buttonTheme;

    private ArrayList<InternalButton> buttons;

    public ButtonManager(String buttonTheme) {
        this.buttons = new ArrayList<>(20);
        this.buttonTheme = buttonTheme;
    }

    public void addButton(String title, Vector2D location, Vector2D size) {
        Button button = new Button(title);
        button.setTheme(buttonTheme);
        add(button);

        buttons.add(new InternalButton(button, location, size));
    }

    public static ButtonManager load(String filename){
        try {
            JAXBContext context = JAXBContext.newInstance(ButtonManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (ButtonManager) unmarshaller.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Something went wrong loading Buttons");
    }

    @Override
    public void layout() {
        for (InternalButton button : buttons) {
            button.button.setPosition((int) button.location.x, (int) button.location.y);
            if (button.size != null) {
                button.button.setSize((int) button.size.x, (int) button.size.y);
            } else {
                button.button.adjustSize();
            }
        }
    }

}

@XmlRootElement(name = "Button")
class InternalButton {
    Button button;
    String title;
    Vector2D location;
    Vector2D size;

    InternalButton(Button button, Vector2D location, Vector2D size) {
        this.button = button;
        this.location = location;
        this.size = size;
    }
}
