#!/bin/sh
nr=0
for F in `find . -name "*.c"`; do
        nr_linii=`cat $F | wc -l`
        if [ $nr_linii -gt 10 ]; then
                nr=$((nr+1))
                echo $F
        fi
        if [ $nr -gt 5 ]; then
                echo "S-a ajuns la numarul maxim de 5 fisiere"
                break
        fi
done
