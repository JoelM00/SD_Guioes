package G7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ClientReader {
    public static void main (String[] args) throws IOException {
        ClientStub c;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String stop = " ";

        while (stop != null) {
            System.out.println("\nPrima enter para ver Servidor: ");
            stop = in.readLine();

            c = new ClientStub(54321);

            List<Contact> contactos = c.getContacts();
            for (Contact co : contactos) {
                System.out.println(" -> " + co.toString());
            }
        }
    }
}
