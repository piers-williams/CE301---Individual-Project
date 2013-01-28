package Project.Game.AI.SPL.Orders;

/**
 * Represents an attack order
 */
public abstract class BasicOrder implements Order{
    private Object[] arguments;
    private double priority;
    private String type;

    protected BasicOrder(Object[] arguments, double priority, String type) {
        this.arguments = arguments;
        this.priority = priority;
        this.type = type;
    }

    @Override
    public void setArguments(Object... args) {
        arguments = args;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Order o) {
        return (int) (o.getPriority() - getPriority());
    }
}
