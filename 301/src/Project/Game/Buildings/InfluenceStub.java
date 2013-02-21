package Project.Game.Buildings;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */

@XmlRootElement(name = "Influence")
public class InfluenceStub {
    @XmlAttribute(name = "size")
    int size;
    @XmlAttribute(name = "strength")
    double strength;

    public int getSize() {
        return size;
    }

    public double getStrength() {
        return strength;
    }
}
