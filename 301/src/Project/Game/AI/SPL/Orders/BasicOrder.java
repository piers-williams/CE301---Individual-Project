package Project.Game.AI.SPL.Orders;

/**
 * Represents an attack order
 */
public abstract class BasicOrder implements SPLObject {
    private double priority;
    private String type;
    private String address;

    protected BasicOrder(double priority, String type) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int compareTo(SPLObject o) {
        return (int) (o.getPriority() - getPriority());
    }
}
