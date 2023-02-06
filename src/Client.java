
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1888);
            IServer service = (IServer) registry.lookup("IServer");
            BufferedReader txt = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println(service.askingEdges());
                String inputLine = txt.readLine();
                String response = service.checkEdges(inputLine);
                System.out.println(response);
                if (response.equals("Close Server!")) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}