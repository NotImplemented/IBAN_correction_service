# IBAN_correction_service

IBAN_correction_service is a service for correcting IBAN account numbers in a given string.

Command line parameters are:</br>
*  </t>-p or --port            listening port</br>
*  </t>-d or --dictionary      dictionary file with corrected and invalid IBAN in the format below:</br>

* correct_IBAN, detected_IBAN new_line</br>
* correct_IBAN, detected_IBAN new_line</br>
* ...</br>

Service facilitates [Aho-Corasick algorithm](https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm) to replace set of strings from input string and uses JSON as data-interchange format owing to [google-gson](https://github.com/google/gson).

Typical usage scenario is as follows:

<code>
        try (Socket clientSocket = new Socket(serverHost, serverPort)) {

                System.out.println("Sending query to '" + serverHost + ":" + serverPort + "'...");

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                Gson gson = new GsonBuilder().create();

                Query query = new Query(Query.correct, " GB04BARC20474473160944Z / DEZ79850503003100180568 / FR763000400Z3200001019471656 EOL");
                String json = gson.toJson(query);
                output.println(json);

                /* Reading response from server. */
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response = null;
                while ((response = input.readLine()) == null) ;

                Query result = gson.fromJson(response, Query.class);
                System.out.println("Response from server: " + result.getResult());

        } catch (IOException e) {

            e.printStackTrace();
        }

</code>

