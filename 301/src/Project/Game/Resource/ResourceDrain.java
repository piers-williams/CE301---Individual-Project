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
     *
     * @param resource amount we want
     * @return the amount returned
     */
    public int claimResource(int resource) {
        if (this.resource < resource) throw new IllegalArgumentException("Does not have this much");
        // Very deliberately only want the one passed in, not this.resource
        int returnAmount = resource;
        this.resource -= resource;
        return returnAmount;
    }


    public boolean hasResource(int resource) {
        return this.resource >= resource;
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
