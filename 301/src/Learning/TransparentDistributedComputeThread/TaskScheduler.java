package Learning.TransparentDistributedComputeThread;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 01/05/12
 * Time: 21:25
 */
public class TaskScheduler {

     Queue<Task> tasks = new PriorityQueue<Task>();

    public void add(Task newTask){
        tasks.add(newTask);
    }
}
