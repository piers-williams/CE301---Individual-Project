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

    Blueprint upgrade;
    @XmlElement(name = "FullName")
    String name;

    @XmlElementWrapper(name = "Buildings")
    @XmlElement(name = "Building")
    ArrayList<Building> buildings;
}

@XmlRootElement
class Building {
    // Type of the building
    @XmlElement(name = "Type")
    String type;
    // Offset x, y
    @XmlElement(name = "Offset")
    Vector2D offset;

}
