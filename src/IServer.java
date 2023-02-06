import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    public String askingEdges() throws RemoteException;
    public String checkEdges(String inputLine) throws RemoteException;
}
