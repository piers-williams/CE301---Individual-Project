package Project.Game.Resource;

import java.util.ArrayList;
import java.util.Random;

/**
 * Polls together all incoming Streams and can register outgoing streams
 */
public class ResourcePool {

    ArrayList<ResourceGenerator> inputs;
    ArrayList<ResourceGenerator> inputDeletions;
    ArrayList<ResourceDrain> drains;
    ArrayList<ResourceDrain> drainDeletions;


    int totalIncomePerRound;
    int totalOutcomePerRound;


    public ResourcePool() {
        inputs = new ArrayList<>();
        inputDeletions = new ArrayList<>();
        drains = new ArrayList<>();
        drainDeletions = new ArrayList<>();
    }

    public void update() {
        // Clearing deleted ones out
        for (ResourceGenerator generator : inputDeletions) {
            inputs.remove(generator);
        }
        inputDeletions.clear();
        for (ResourceDrain drain : drainDeletions) {
            drains.remove(drain);
        }
        drainDeletions.clear();


        totalIncomePerRound = 0;
        for (ResourceGenerator generator : inputs) {
            totalIncomePerRound += generator.getResourcePerTick();
        }

        // Distribute income to output
        totalOutcomePerRound = 0;
        for (ResourceDrain drain : drains) {
            totalOutcomePerRound += drain.getMaxDrainPerTick();
        }

        if (totalOutcomePerRound >= totalIncomePerRound) {
            for (ResourceDrain drain : drains) {
                drain.assignResource(drain.getMaxDrainPerTick());
            }

            System.out.println("Wasting resources");
        } else {
            for (ResourceDrain drain : drains) {
                drain.assignResource((totalOutcomePerRound / drain.getMaxDrainPerTick()) * totalIncomePerRound);
            }

            System.out.println("Not enough income");
        }

    }

    public void register(ResourceGenerator generator) {
        inputs.add(generator);
    }

    public void register(ResourceDrain drain) {
        drains.add(drain);
    }

    public void deRegister(ResourceGenerator generator) {
        inputDeletions.add(generator);
    }

    public void deRegister(ResourceDrain drain) {
        drainDeletions.add(drain);
    }

    public static void main(String[] args) {
        ResourcePool pool = new ResourcePool();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            pool.register(new ResourceGenerator(pool, 35));
        }

        for (int i = 0; i < 1500; i++) {
            pool.register(new ResourceDrain(pool, 20));
        }

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(20);
                pool.update();
                if (random.nextBoolean()) {
                    pool.register(new ResourceGenerator(pool, random.nextInt(50)));
                } else {
                    pool.register(new ResourceDrain(pool, random.nextInt(50)));
                }

               float rand = random.nextFloat();
                if(rand < 0.2){
                    pool.deRegister(pool.inputs.get(random.nextInt(pool.inputs.size())));
                }
                if(rand > 0.2 && rand < 0.4){
                  pool.deRegister(pool.drains.get(random.nextInt(pool.drains.size())));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
