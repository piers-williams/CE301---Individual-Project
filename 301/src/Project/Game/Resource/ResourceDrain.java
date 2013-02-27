package Project.Game.Resource;

/**
 * Drains resource from a pool to be used by constructive behaviours
 * <p/>
 * Should only belong to a single object at once - not designed to be shared
 */
public final class ResourceDrain {

    private ResourcePool pool;

    private int maxDrainPerTick;
    private int resource = 0;

    public ResourceDrain(ResourcePool pool, int maxDrainPerTick) {
        this.pool = pool;
        this.maxDrainPerTick = (maxDrainPerTick <= 0) ? 1 : maxDrainPerTick;
    }

    public int getMaxDrainPerTick() {
        return maxDrainPerTick;
    }

    public void assignResource(int amount) {
        resource += amount;
    }

    /**
     * Should be guarded by a call to hasResource()
     * @return the amount returned
     */
    public int claimResource() {
        int returnAmount = this.resource;
        this.resource = 0;
        return returnAmount;
    }


    public boolean hasResource() {
        return this.resource >= 1;
    }

    // Detach this from a pool
    public void deRegister() {
        pool.deRegister(this);
    }

    /**
     * Should not rely on this, will drain resources with nothing going away
     *
     * Could return the resources somehow
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        deRegister();
    }
}
