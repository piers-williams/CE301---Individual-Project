package Project.Game.UI;

import Project.Game.Vector2D;
import com.sun.istack.internal.Nullable;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Responsible for storing and dealing with all Buttons
 */
public class ButtonManager extends Widget {

    private String buttonTheme;

    @XmlElementWrapper(name = "Buttons")
    @XmlElement(name = "Button")
    private ArrayList<InternalButton> buttons;

    public ButtonManager(){

    }

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

    public static ArrayList<InternalButton> load(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(ArrayList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (ArrayList<InternalButton>) unmarshaller.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }



        throw new RuntimeException("Something went wrong loading Buttons");
    }

    @Override
    public void layout() {
        for (InternalButton button : buttons) {
            button.getButton().setPosition((int) button.location.x, (int) button.location.y);
            if (button.size != null) {
                button.getButton().setSize((int) button.size.x, (int) button.size.y);
            } else {
                button.getButton().adjustSize();
            }
        }
    }

    public static void main(String[] args) {
        ButtonManager manager = new ButtonManager();
        manager.buttons = ButtonManager.load("Content/UI.xml");

        try {
            JAXBContext context = JAXBContext.newInstance(ButtonManager.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(manager, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

@XmlRootElement(name = "Button")
@XmlAccessorType(XmlAccessType.NONE)
class InternalButton {
    private Button button;
    @XmlElement(name = "Title")
    String title;
    @XmlElement(name = "Location")
    Vector2D location;
    @XmlElement(name = "Dimension")
    Vector2D size;
    @XmlElement(name = "Action")
    Action action;

    public InternalButton(){

    }

    InternalButton(Button button, Vector2D location, Vector2D size) {
        this.button = button;
        this.location = location;
        this.size = size;
    }

    public Button getButton() {
        return button;
    }
}

@XmlRootElement(name = "Action")
class Action {
    @XmlElement(name = "Type")
    String type;
    @Nullable
    @XmlElement(name = "Argument")
    String Argument;

    public Action(){

    }
}
