#!/bin/sh
while true; do
        nume=$1
        numar=$2
        if [ -z $numar ]; then
                break;
        fi
        ls -l $nume | awk '{if($5==numar) print "Da"; else print "Nu";}' numar=$numar
        numar=$numar
        shift 2
done

