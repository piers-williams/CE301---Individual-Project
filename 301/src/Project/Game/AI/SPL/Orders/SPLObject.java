package Project.Game.AI.SPL.Orders;

/**
 * Represents an SPL Object
 */
public interface SPLObject extends Comparable<SPLObject> {

    public String getType();

    public String getAddress();

    public void setAddress(String address);

    public double getPriority();
}
