package Learning.TransparentDistributedComputeThread;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 02/05/12
 * Time: 15:53
 */
public interface Executable extends Serializable {

    public Response execute();
}
