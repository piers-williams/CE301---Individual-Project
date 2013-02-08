package Project.Game.Blueprints;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Stores and loads the Blueprints for the game
 */
@XmlRootElement(name = "Bases")
public class BlueprintRegistry {

    // Blueprints are the same for every player/faction
    private ArrayList<Blueprint> blueprints;

    public void loadBlueprints(String filename) {

        try {
            JAXBContext context = JAXBContext.newInstance(BlueprintRegistry.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
