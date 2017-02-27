import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Integer listeningPort;
    static String dictionaryFile;

    static String helpMessage = "Mandatory parameters are:\r\n" +
            "\t-p or --port \t\tlistening port\r\n" +
            "\t-d or --diсtionary \t\tdictionary file\r\n";

    public static void main(String[] args) throws Exception {

        String commandLine = "Starting service node with command line: ";

        for (int i = 0; i < args.length; ++i) {

            if (i > 0)
                commandLine += " ";
            commandLine += args[i];
        }

        System.out.println(commandLine);

        for (int i = 0; i < args.length; ++i) {

            if (args[i].equals("-p") || args[i].equals("--port")) {

                if (i + 1 < args.length) {
                    listeningPort = Integer.parseInt(args[i + 1]);
                }
            } else if (args[i].equals("-d") || args[i].equals("--dictionary")) {

                if (i + 1 < args.length) {
                    dictionaryFile = args[i + 1];
                }
            }
        }

        if (listeningPort == null) {
            System.out.println(helpMessage);
            throw new IllegalArgumentException("Specify listening port number.");
        }

        if (dictionaryFile == null) {
            System.out.println(helpMessage);
            throw new IllegalArgumentException("Specify dictionary file formatted as: <correct>, <detected> (brackets for clarity).");
        }

        List<Pair<String, String>> dictionary = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String pair[] = line.split(", ");
                if (pair.length != 2)
                    continue;

                dictionary.add(new Pair<>(pair[0], pair[1]));
            }
        }
        catch (IOException e) {

            e.printStackTrace();
            throw new Exception("Cannot read data from '" + dictionaryFile + "'.");
        }

        AhoCorasick algorithm = new AhoCorasick(dictionary);

        try (ServerSocket serverSocket = new ServerSocket(listeningPort)) {

            while (true) {

                try {

                    Socket connectionSocket = serverSocket.accept();
                    QueryHandler handler = new QueryHandler(connectionSocket, algorithm);

                    (new Thread(handler)).start();

                } catch (IOException e) {

                    System.out.println("Cannot receive data from socket: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        System.out.println("Stopping service on port " + listeningPort + " and directory '" + dictionaryFile + "'.");
    }
}
