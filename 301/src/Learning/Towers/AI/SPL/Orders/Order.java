package Learning.Towers.AI.SPL.Orders;

/**
 *  Represents an SPL Order
 */
public interface Order extends Comparable<Order>{

    public String getType();

    public double getPriority();
}
