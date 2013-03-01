package Project.Game.AI.SPL.Orders;

/**
 * Represents an attack order
 */
public abstract class BasicOrder implements SPLObject {
    private double priority;
    private String type;

    protected BasicOrder( double priority, String type) {
        this.priority = priority;
        this.type = type;
    }
    @Override
    public double getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(SPLObject o) {
        return (int) (o.getPriority() - getPriority());
    }
}
