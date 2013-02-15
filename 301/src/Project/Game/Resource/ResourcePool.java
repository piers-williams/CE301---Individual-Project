package Project.Game.Resource;

import java.util.ArrayList;
import java.util.Random;

/**
 * Polls together all incoming Streams and can register outgoing streams
 */
public class ResourcePool {

    private ArrayList<ResourceGenerator> inputs;
    private ArrayList<ResourceGenerator> inputDeletions;
    private final Object _inputs = new Object();
    private final Object _inputDeletions = new Object();

    private ArrayList<ResourceDrain> drains;
    private ArrayList<ResourceDrain> drainDeletions;
    private final Object _drains = new Object();
    private final Object _drainDeletions = new Object();


    private int totalIncomePerRound;
    private int totalOutcomePerRound;


    public ResourcePool() {
        inputs = new ArrayList<>();
        inputDeletions = new ArrayList<>();
        drains = new ArrayList<>();
        drainDeletions = new ArrayList<>();
    }

    public void update() {

        if (inputs.size() != 0 || drains.size() != 0) {
            synchronized (_inputDeletions) {
                for (ResourceGenerator generator : inputDeletions) {
                    synchronized (_inputs) {
                        inputs.remove(generator);
                    }
                }
                inputDeletions.clear();
            }
            synchronized (_drainDeletions) {
                for (ResourceDrain drain : drainDeletions) {
                    synchronized (_drains) {
                        drains.remove(drain);
                    }
                }
                drainDeletions.clear();
            }

            synchronized (_inputs) {
                totalIncomePerRound = 0;
                for (ResourceGenerator generator : inputs) {
                    totalIncomePerRound += generator.getResourcePerTick();
                }
            }

            synchronized (_drains) {
                // Distribute income to output
                totalOutcomePerRound = 0;
                for (ResourceDrain drain : drains) {
                    totalOutcomePerRound += drain.getMaxDrainPerTick();
                }

                if (totalOutcomePerRound >= totalIncomePerRound) {
                    for (ResourceDrain drain : drains) {
                        drain.assignResource(drain.getMaxDrainPerTick());
                    }

//                    System.out.println("Wasting resources");
                } else {
                    for (ResourceDrain drain : drains) {
                        drain.assignResource((totalOutcomePerRound / drain.getMaxDrainPerTick()) * totalIncomePerRound);
                    }

//                    System.out.println("Not enough income");
                }
            }
        }

    }

    public void register(ResourceGenerator generator) {
        synchronized (_inputs) {
            inputs.add(generator);
        }
    }

    public void register(ResourceDrain drain) {
        synchronized (_drains) {
            drains.add(drain);
        }
    }

    public void deRegister(ResourceGenerator generator) {
        synchronized (_inputDeletions) {
            inputDeletions.add(generator);
        }
    }

    public void deRegister(ResourceDrain drain) {
        synchronized (_drainDeletions) {
            drainDeletions.add(drain);
        }
    }

    public static void main(String[] args) {
        final ResourcePool pool = new ResourcePool();
        Random random = new Random();

        ArrayList<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            runnables.add(new Runnable() {

                Random random = new Random();

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(random.nextInt(10) + 10);

                            float rand = random.nextFloat();
                            if (rand < 0.2) {
                                pool.deRegister(pool.inputs.get(random.nextInt(pool.inputs.size())));
                            }
                            if (rand > 0.2 && rand < 0.4) {
                                pool.deRegister(pool.drains.get(random.nextInt(pool.drains.size())));
                            }
                            if (rand > 0.4 && rand < 0.6) {
                                pool.register(new ResourceGenerator(pool, random.nextInt(50) + 1));
                            }
                            if (rand > 0.6 && rand < 0.8) {
                                pool.register(new ResourceDrain(pool, random.nextInt(50) + 1));
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        for (int i = 0; i < 1000; i++) {
            pool.register(new ResourceGenerator(pool, 35));
        }

        for (int i = 0; i < 1500; i++) {
            pool.register(new ResourceDrain(pool, 20));
        }

        for (int i = 0; i < 50 * 120; i++) {
            if (i % 50 == 0) {
                System.out.println("Seconds Taken: " + i / 50);
            }
            try {
                Thread.sleep(20);
                pool.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 0) {
                for (Runnable runnable : runnables) {
                    Thread thread = new Thread(runnable);
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        }
    }

}
