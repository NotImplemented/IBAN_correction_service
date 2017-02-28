# IBAN_correction_service

IBAN_correction_service is a service for correcting IBAN account numbers.

Command line parameters are:
  -p or --port            listening port
  -d or --dictionary      dictionary file with corrected and invalid IBAN in the format below:

<correct IBAN>, <detected IBAN> new_line
<correct IBAN>, <detected IBAN> new_line
...

Service facilitates [Aho-Corasick algorithm](https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm) to replace set of strings from input string.


