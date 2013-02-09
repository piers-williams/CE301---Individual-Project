package Project.Game.AI.SPL.Orders;

/**
 * Represents an SPL Object
 */
public interface SPLObject extends Comparable<SPLObject> {

    public String getType();

    // Completely un type safe but small enough cases I can probably handle it
    // Switching on strings is rather useful here
    public void setArguments(Object... args);

    public Object[] getArguments();

    public double getPriority();
}
