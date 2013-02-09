package Project.Game.Blueprints;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;

/**
 * Stores and loads the Blueprints for the game
 */
@XmlRootElement(name = "Registry")
public class BlueprintRegistry {

    // Blueprints are the same for every player/faction
    @XmlElementWrapper(name = "Bases")
    @XmlElement(name = "Base")
    public ArrayList<Blueprint> blueprints;

    public BlueprintRegistry() {
        blueprints = new ArrayList<>();
    }

    public void loadBlueprints(String filename) {

        try {
            JAXBContext context = JAXBContext.newInstance(BlueprintRegistry.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(BlueprintRegistry.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            BlueprintRegistry registry = (BlueprintRegistry) unmarshaller.unmarshal(new File("Content/Bases/Bases.xml"));

            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(registry, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
