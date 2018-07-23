#!/bin/sh
if [ $# -lt 1 ] ;then echo "fara parametrii"
else
        max=`expr length $1`
        max_cuv=$1

        for cuv in $* ;do
                nr=`expr length $cuv`
                if [ $nr -gt $max ]; then
                        max=$nr
                        max_cuv=$cuv
                fi
        done
        echo $max_cuv
fi
