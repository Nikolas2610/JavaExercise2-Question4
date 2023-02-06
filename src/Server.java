import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
    Graph graph = new Graph(300000, 3000000);
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Server() throws RemoteException {
        super();
    }

    @Override
    public String askingEdges() {
        return "Write to keyboard the edge. Nodes are " + graph.getNODES() +
                " and the accept format is X,X with max value " + (graph.getNODES() - 1)
                + " (example: 4,2)\nFor exit write -1,-1:";
    }

    @Override
    public String checkEdges(String inputLine) {
        if (inputLine == null || inputLine.length() == 0) {
            return "Empty input";
        }
        String[] edges = inputLine.split(",");
        if (edges.length != 2) {
            return "Wrong input\n\n";
        } else {
            try {
//          Transform the string data to numbers
                int[] edgesInt = new int[2];
                for (int i = 0; i < edges.length; i++) {
                    edgesInt[i] = Integer.parseInt(edges[i]);
                }
//          Check if the user want to close the connection
                if (edges[0].equals("-1") && edges[1].equals("-1")) {
                    System.out.println("Close Server!");
                    return "Close Server!";
                }
//          Check if the user gives available numbers with the min and max nodes
                if (edgesInt[0] >= graph.getNODES() || edgesInt[1] >= graph.getNODES() ||
                        edgesInt[0] < 0 || edgesInt[1] < 0) {
                    return "Wrong input\n\n";
                }
//          Check if the edge exists to the graph and create message for the client
                return graph.edgeExists(edgesInt[0], edgesInt[1]);
            } catch (NumberFormatException e) {
                return "Wrong input\n\n";
            }

        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1888);
            IServer service = new Server();
            registry.rebind("IServer", service);
            System.out.println(ANSI_BLUE + "Server running..." + ANSI_RESET);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
