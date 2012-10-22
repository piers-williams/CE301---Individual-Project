package Learning.TransparentDistributedComputeThread;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 01/05/12
 * Time: 21:27
 */
public  class Task<T extends Executable> implements Serializable, Comparable<Task>{

    T taskItem;
    int taskDifficulty;


    public Task(T taskItem, int taskDifficulty) {
        this.taskItem = taskItem;
        this.taskDifficulty = taskDifficulty;
    }

    public void  execute(){
        Response r = taskItem.execute();
    }
    public int compareTo(Task o) {
        if(taskDifficulty > o.taskDifficulty){
            return 1;
        }else if (taskDifficulty == o.taskDifficulty){
            return 0;
        }else{
            return -1;
        }
    }
}
