package Project.Game.Behaviours.Constructive;

import Project.Game.Vector2D;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 *  Blueprints for Bases
 */
@XmlRootElement(name = "Base")
public class Blueprint {

    ArrayList<Building> buildings;
}

class Building{
    // Type of the building
    @XmlElement(name = "Type")
    String type;
    // Offset x, y
    @XmlElement(name = "Offset")
    Vector2D x, y;

}
