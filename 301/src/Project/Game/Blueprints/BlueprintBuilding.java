package Project.Game.Blueprints;

import Project.Game.Vector2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *   Represents a blueprints idea of a building
 */
@XmlRootElement
public class BlueprintBuilding {
    // Type of the building
    @XmlElement(name = "Type")
    String type;
    // Offset x, y
    @XmlElement(name = "Offset")
    Vector2D offset;

    public String getType() {
        return type;
    }

    public Vector2D getOffset() {
        return offset;
    }
}
