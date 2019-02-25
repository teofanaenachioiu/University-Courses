#!/bin/bash
echo Content-type: text/html
echo
echo "Am primit pe request antetele HTTP cu valorile:<br>"
echo "(comparati aceste valori cu antetele prezentate de Firebug sau Developer Tools - F12)<br><br>"
set | sed 's/$/<br>/' | grep '^HTTP_' | sed 's/^HTTP_//'

