package Project.Game.Resource;

/**
 * Drains resource from a pool to be used by constructive behaviours
 */
public final class ResourceDrain {

    private ResourcePool pool;

    private int maxDrainPerTick;

    public ResourceDrain(ResourcePool pool, int maxDrainPerTick) {
        this.pool = pool;
        this.maxDrainPerTick = maxDrainPerTick;
    }

    public int getMaxDrainPerTick() {
        return maxDrainPerTick;
    }

    public void assignResource(int amount) {

    }

    public void deRegister(){
        pool.deRegister(this);
    }
}
