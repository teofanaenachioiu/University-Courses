#!/bin/sh

#verificare perechi fisier - lungime

while true; do
        if [ -z $1 ] || [ -z $2 ]; then break; fi
        fisier=$1
        numar=$2
        if [ ! -f $fisier ]; then continue; fi
        if [ `echo $numar | grep -c "[0-9]*"` -eq 0 ]; then continue; fi
        lung=`ls -l $fisier | awk '{print $5}'`
        if [ $lung -eq $numar ]; then echo "$fisier are dim $numar"; else echo "Nu"; fi
        shift 2
done
