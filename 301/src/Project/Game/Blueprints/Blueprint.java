package Project.Game.Blueprints;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Blueprints for Bases
 */
@XmlRootElement
public class Blueprint {
    @XmlElement(name = "Upgrade")
    String upgradeString;

    Blueprint upgrade;
    @XmlElement(name = "FullName")
    String name;

    @XmlElementWrapper(name = "Buildings")
    @XmlElement(name = "Building")
    ArrayList<BlueprintBuilding> blueprintBuildings;

    public String getUpgradeString() {
        return upgradeString;
    }

    public Blueprint getUpgrade() {
        return upgrade;
    }

    public String getName() {
        return name;
    }

    public ArrayList<BlueprintBuilding> getBlueprintBuildings() {
        return blueprintBuildings;
    }
}

