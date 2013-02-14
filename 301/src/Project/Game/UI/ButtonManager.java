package Project.Game.UI;

import Project.Game.Main;
import Project.Game.Vector2D;
import com.sun.istack.internal.Nullable;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Responsible for storing and dealing with all Buttons
 */
public class ButtonManager extends Widget {

    private String buttonTheme;

    private ButtonsWrapper buttons;

    public ButtonManager() {
        buttons = ButtonManager.load("Content/UI.xml");
        buttons.constructAllButtons();
        removeAllChildren();
        for (InternalButton button : buttons) {
            add(button.button);
        }
    }

    public ButtonManager(String buttonTheme) {
        this();
        this.buttonTheme = buttonTheme;
    }

    public static ButtonsWrapper load(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(ButtonsWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (ButtonsWrapper) unmarshaller.unmarshal(new File(filename));
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
        ButtonsWrapper wrapper;
        wrapper = ButtonManager.load("Content/UI.xml");

        try {
            JAXBContext context = JAXBContext.newInstance(ButtonsWrapper.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(wrapper, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

@XmlRootElement(name = "Button")
@XmlAccessorType(XmlAccessType.NONE)
class InternalButton {
    Button button;
    @XmlElement(name = "Title")
    String title;
    @XmlElement(name = "Location")
    Vector2D location;
    @XmlElement(name = "Dimension")
    Vector2D size;
    @XmlElement(name = "Action")
    Action action;

    public InternalButton() {

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


@XmlRootElement(name = "Buttons")
class ButtonsWrapper implements Iterable<InternalButton> {
    @XmlElement(name = "Button")
    ArrayList<InternalButton> buttons;

    public ButtonsWrapper() {
        buttons = new ArrayList<>();
    }

    public void add(InternalButton button) {
        buttons.add(button);
    }

    /**
     * Constructs all non null buttons
     */
    public void constructAllButtons() {
        for (InternalButton button : buttons) {
            if (button.button == null) {
                button.button = new Button(button.title);
            }
        }

        assignCallbacks();
    }

    /**
     * Constructs and overwrites all buttons
     */
    public void reconstructAllButtons() {
        for (InternalButton button : buttons) {
            button.button = new Button(button.title);
        }
    }

    public void assignCallbacks() {
        for (InternalButton button : buttons) {
            switch (button.action.type) {
                case "Pause":
                    button.button.addCallback(getPauseFunction());
                    break;
                case "Build":
                    button.button.addCallback(getBuildFunction(button));
                    break;
            }
        }
    }

    private Runnable getPauseFunction() {
        return new Runnable() {
            @Override
            public void run() {
                Main.MAIN.togglePause();
            }
        };
    }

    private Runnable getBuildFunction(InternalButton button) {
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    @Override
    public Iterator<InternalButton> iterator() {

        return new Iterator<InternalButton>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < buttons.size();
            }

            @Override
            public InternalButton next() {
                return buttons.get(index++);
            }

            @Override
            public void remove() {
                buttons.remove(index);
            }
        };
    }
}

@XmlRootElement(name = "Action")
class Action {
    @XmlElement(name = "Type")
    String type;
    @Nullable
    @XmlElement(name = "Argument")
    String Argument;

    public Action() {

    }
}
