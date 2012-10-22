package Learning.TransparentDistributedComputeThread;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 01/05/12
 * Time: 21:32
 */
public class OuterNode extends UnicastRemoteObject implements RemoteComputeThread {

    public OuterNode() throws RemoteException{

    }

    public Response execute(Task input) {
        return null;
    }
}
