#!/bin/sh
for F in $(find . -type f -name "*.txt") do
        nr_linii=`cat $F | wc -l`
        echo "Numar linii $nr_linii"
done
