import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.runtime.logging.*;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class QueryHandler implements Runnable {

    public QueryHandler(Socket clientSocket, AhoCorasick algorithm) {

        this.clientSocket = clientSocket;
        this.algorithm = algorithm;
    }

    private final Socket clientSocket;
    private final AhoCorasick algorithm;

    public void run() {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while(true) {

                String request = reader.readLine();

                if (request == null)
                    continue;

                Gson gson = new GsonBuilder().create();
                Query query = gson.fromJson(request, Query.class);

                System.out.println("Processing query: " + request);

                if (query.getType().equals(Query.correct)) {

                    synchronized (algorithm) {

                        query.setResult(algorithm.correct(query.getText()));

                    }

                    String json = gson.toJson(query);
                    System.out.println("Sending response: " + json);

                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    output.println(json);
                }
            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {

            try {

                clientSocket.close();
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}