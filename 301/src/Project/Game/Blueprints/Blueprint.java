package Project.Game.Blueprints;

import Project.Game.Vector2D;

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

    // The next blueprint in the upgrade chain
    Blueprint upgrade;
    // The previous blueprint in the upgrade chain
    Blueprint downgrade;

    @XmlElement(name = "FullName")
    String name;

    @XmlElementWrapper(name = "Buildings")
    @XmlElement(name = "Building")
    ArrayList<BlueprintBuilding> blueprintBuildings;

    Vector2D size;
    private boolean buildingsCalculated = false;

    public String getUpgradeString() {
        return upgradeString;
    }

    public Blueprint getUpgrade() {
        return upgrade;
    }

    public Blueprint getDowngrade() {
        return downgrade;
    }

    public String getName() {
        return name;
    }

    public void calculate() {
        blueprintBuildings = getBlueprintBuildings();
        int maxX = 0, maxY = 0, lowX = 0, lowY = 0;
        for (BlueprintBuilding building : blueprintBuildings) {
            Vector2D offset = building.getOffset();

            if (offset.x > maxX) maxX = (int) offset.x;
            if (offset.x < lowX) lowX = (int) offset.x;
            if (offset.y > maxY) maxY = (int) offset.y;
            if (offset.y < maxY) maxY = (int) offset.y;
        }

        size = new Vector2D(maxX - lowX, maxY - lowY);
    }

    public ArrayList<BlueprintBuilding> getBlueprintBuildings() {
        if (downgrade != null && buildingsCalculated == false) {
            ArrayList<BlueprintBuilding> buildings = new ArrayList<>();
            // Downgrade first so that they appear in correct order
            buildings.addAll(downgrade.getBlueprintBuildings());
            // Add all our buildings
            buildings.addAll(blueprintBuildings);
            blueprintBuildings = buildings;
            buildingsCalculated = true;
        }
        return blueprintBuildings;
    }

    public Vector2D getSize() {
        return size;
    }
}

