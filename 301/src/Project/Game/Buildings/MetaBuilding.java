package Project.Game.Buildings;

import Project.Game.Vector2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Actual stats about a building
 */
@XmlRootElement(name = "Building")
public class MetaBuilding {

    @XmlElement(name = "Name")
    String name;
    @XmlElement(name = "Size")
    Vector2D size;
    @XmlElement(name = "Cost")
    int cost;
    @XmlElement(name = "BuildTime")
    int buildTime;

    @Override
    public String toString() {
        return "Building: " + name + " |::| " + size + " |::| " + cost + " |::| " + buildTime;
    }

    public String getName() {
        return name;
    }

    public Vector2D getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

    public int getBuildTime() {
        return buildTime;
    }
}
