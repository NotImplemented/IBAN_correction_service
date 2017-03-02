package service;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

	private final AhoCorasick algorithm;
	private final String dictionaryFile = "dictionary.txt";
	
	public Controller() throws Exception {

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

        algorithm = new AhoCorasick(dictionary);	
	}

    @RequestMapping("/correct")
    public Query correct(@RequestParam(value="text", defaultValue="Default_IBAN") String text) {
        
		String corrected = algorithm.correct(text);
		return new Query(corrected);
    }
}