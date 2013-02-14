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

    public void calculateUpgrades() {
        for (Blueprint blueprint : blueprints) {
            if (blueprint.upgradeString != "Null") {
                if (contains(blueprint.upgradeString)) {
                    blueprint.upgrade = get(blueprint.upgradeString);
                }
            }
        }
    }

    public boolean contains(String name) {
        for (Blueprint blueprint : blueprints) {
            if (blueprint.name.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public Blueprint get(String name) {
        for (Blueprint blueprint : blueprints) {
            if (blueprint.name.equalsIgnoreCase(name)) return blueprint;
        }
        throw new IllegalArgumentException("Blueprint not found");
    }

    public static BlueprintRegistry load(String filename) {

        try {
            JAXBContext context = JAXBContext.newInstance(BlueprintRegistry.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (BlueprintRegistry) unmarshaller.unmarshal(new File(filename));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Something went wrong loading blueprints");
    }

    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(BlueprintRegistry.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            BlueprintRegistry registry = (BlueprintRegistry) unmarshaller.unmarshal(new File("Content/Bases/Bases.xml"));


            System.out.println(registry.contains("Home Level 2"));
            System.out.println(registry.contains("Home Level 4"));
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(registry, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
