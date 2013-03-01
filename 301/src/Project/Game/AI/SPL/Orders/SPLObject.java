package Project.Game.AI.SPL.Orders;

/**
 * Represents an SPL Object
 */
public interface SPLObject extends Comparable<SPLObject> {

    public String getType();

    public double getPriority();
}
