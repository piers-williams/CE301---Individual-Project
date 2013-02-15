package Project.Game.Resource;

/**
 * Generates Resources and passes to a Pool
 */
public final class ResourceGenerator {

    private ResourcePool pool;

    private int resourcePerTick;

    public ResourceGenerator(ResourcePool pool, int resourcePerTick) {
        this.pool = pool;
        this.resourcePerTick = resourcePerTick;
    }

    public int getResourcePerTick() {
        return resourcePerTick;
    }

    /**
     * Stops the pool receiving resource from this stream
     */
    public void deRegister() {
        pool.deRegister(this);
    }
}
