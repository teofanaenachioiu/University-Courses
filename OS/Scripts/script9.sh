#!/bin/sh
if [ $# -lt 1 ]; then echo "Prea putine argumente"
else
        nr=$(cat $1 | wc -l)
        echo "Numarul de linii este: $nr"
fi
