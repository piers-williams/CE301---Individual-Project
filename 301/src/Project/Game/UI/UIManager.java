package Project.Game.UI;

import Project.Game.AI.SPL.Orders.AttackOrder;
import Project.Game.AI.SPL.Orders.DefendOrder;
import Project.Game.AI.SPL.Orders.SPLObject;
import Project.Game.Faction;
import Project.Game.Factions;
import Project.Game.Main;
import Project.Game.Vector2D;
import com.sun.istack.internal.Nullable;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
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
public class UIManager extends Widget {

    private ButtonsWrapper buttons;
    private LabelWrapper labels;
    private EditField naturalLanguageInput;
    private Button naturalLanguageSubmit;
    public boolean showInput = false;
    public boolean showLabels = false;

    public UIManager() {
        buttons = UIManager.loadButtons("Content/UI/Buttons.xml");
        buttons.constructAllButtons();
        labels = UIManager.loadLabels("Content/UI/Labels.xml");
        labels.constructAllLabels();
        removeAllChildren();
        for (InternalButton button : buttons) {
            add(button.button);
        }
        for (InternalLabel label : labels) add(label.label);

        naturalLanguageInput = new EditField();
        naturalLanguageInput.setReadOnly(false);
        naturalLanguageSubmit = new Button("Submit");


        naturalLanguageSubmit.addCallback(new Runnable() {
            @Override
            public void run() {
                // Send text message stuff to pipeline
                String text = naturalLanguageInput.getText();

                // Do something with the object
                //System.out.println((text == null) ? "Text was null" : Main.PIPELINE.process(text));
                if (text != null) {
                    SPLObject object = Main.PIPELINE.process(text);
                    if (object != null) {
                        System.out.println(object);
                        switch (object.getType()) {
                            case "Attack":
                                System.out.println("Adding Attack Order to Queue");
                                AttackOrder order = (AttackOrder) object;
                                Factions.valueOf(object.getAddress()).getFaction().getSplQueue().addAttackOrder(order);
                                break;
                            case "Defend":
                                DefendOrder defendOrder = (DefendOrder) object;
                                Factions.valueOf(object.getAddress()).getFaction().getSplQueue().addDefendOrder(defendOrder);
                                break;
                        }
                        naturalLanguageInput.setText("");
                    }else{
                        naturalLanguageInput.setText("Message not known!");
                    }
                }

            }
        });

        add(naturalLanguageInput);
        add(naturalLanguageSubmit);
    }

    public UIManager(String buttonTheme) {
        this();
    }

    public void update(Faction faction) {
        for (InternalLabel label : labels) label.update(faction);
    }

    public static ButtonsWrapper loadButtons(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(ButtonsWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (ButtonsWrapper) unmarshaller.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Something went wrong loading Buttons");
    }

    public static LabelWrapper loadLabels(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(LabelWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (LabelWrapper) unmarshaller.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Something went wrong loading labels");
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
        for (InternalLabel label : labels) {
            label.label.setPosition((int) label.location.x, (int) label.location.y);
            if (label.size != null) {
                label.label.setSize((int) label.size.x, (int) label.size.y);
            } else {
                label.label.adjustSize();
            }
        }
        if (showInput) {
            naturalLanguageInput.setVisible(true);
            naturalLanguageInput.setSize(300, 50);
            naturalLanguageInput.setPosition((Main.SCREEN_WIDTH - 300) / 2, 50);

            naturalLanguageSubmit.setVisible(true);
            naturalLanguageSubmit.setSize(100, 50);
            naturalLanguageSubmit.setPosition((Main.SCREEN_WIDTH / 2 + 150), 50);
        } else {
            naturalLanguageInput.setVisible(false);
            naturalLanguageSubmit.setVisible(false);
        }

        Main.REGISTRY.layout(this, showLabels);
    }

    public static void main(String[] args) {
        ButtonsWrapper wrapper;
        wrapper = UIManager.loadButtons("Content/Buttons.xml");

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

    private Runnable getBuildFunction(final InternalButton button) {
        return new Runnable() {
            @Override
            public void run() {
                Main.MAIN.blueprintToBuild = button.action.Argument;
            }
        };
    }

    @Override
    public Iterator<InternalButton> iterator() {
        return buttons.iterator();
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


@XmlRootElement(name = "Label")
@XmlAccessorType(XmlAccessType.NONE)
class InternalLabel {
    Button label;
    @XmlElement(name = "Service")
    String service;
    @XmlElement(name = "Location")
    Vector2D location;
    @XmlElement(name = "Dimension")
    Vector2D size;

    public void update(Faction faction) {
        label.setText(faction.getService(service).toString());
    }
}

@XmlRootElement(name = "Labels")
class LabelWrapper implements Iterable<InternalLabel> {
    @XmlElement(name = "Label")
    ArrayList<InternalLabel> labels;

    @Override
    public Iterator<InternalLabel> iterator() {
        return labels.iterator();
    }

    public void constructAllLabels() {
        System.out.println("Number of Labels: " + labels.size());
        for (InternalLabel label : labels) label.label = new Button();
    }
}
