import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail_Kaspiarovich on 2/27/2017.
 */
public class Client {

    static final int serverPort = 6060;
    static final String serverHost = "localhost";
    static final int queries = 16;

    public static void main(String[] args) throws Exception {

        try (Socket clientSocket = new Socket(serverHost, serverPort)) {

            for (int i = 0; i < queries; ++i) {

                System.out.println("Sending query to '" + serverHost + ":" + serverPort + "'...");

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                Gson gson = new GsonBuilder().create();

                Query query = new Query(Query.correct, " GB04BARC20474473160944Z / DEZ79850503003100180568 / FR763000400Z3200001019471656 EOL");
                String json = gson.toJson(query);
                System.out.println("Sending query = " + json);
                output.println(json);

                /* Reading response from server. */
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response = null;
                while ((response = input.readLine()) == null) ;

                Query result = gson.fromJson(response, Query.class);
                System.out.println("Response from server: " + result.getResult());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
