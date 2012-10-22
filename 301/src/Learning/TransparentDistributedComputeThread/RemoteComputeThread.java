package Learning.TransparentDistributedComputeThread;

import java.rmi.Remote;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 01/05/12
 * Time: 21:12
 */
public interface RemoteComputeThread extends Remote {

    public Response execute(Task input);
}
