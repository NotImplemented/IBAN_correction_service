# IBAN_correction_service

IBAN_correction_service is a service for correcting IBAN account numbers.

Command line parameters are:</br>
  -p or --port            listening port</br>
  -d or --dictionary      dictionary file with corrected and invalid IBAN in the format below:</br>

correct_IBAN, detected_IBAN new_line</br>
correct_IBAN, detected_IBAN new_line</br>
...</br>

Service facilitates [Aho-Corasick algorithm](https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm) to replace set of strings from input string.


