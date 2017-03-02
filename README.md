# IBAN_correction_service

IBAN_correction_service is a service for correcting IBAN account numbers in an input string given set of valid->invalid IBAN pairs.

Service facilitates [Aho-Corasick algorithm](https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm) to replace set of strings from input string. RESTful is leveraged using Spring library (https://spring.io/). You can use maven or gradle to build application, these build managers will pull all necessary dependencies automatically.

To start service:
* Navigate to project root.
* Gradle users, run **gradle bootRun**.
* Maven users, use **mvn spring-boot:run**.

```
Open browser and make query:
http://localhost:8080/correct?text=%20GB04BARC20474473160944Z,%20DEZ79850503003100180568%20-%20FR763000400Z3200001019471656%20EOL

{"text":" GB04BARC20474473160944, DE79850503003100180568 - FR7630004003200001019471656 EOL"}
```
