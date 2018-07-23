#!/bin/sh
n=0
for i in `cat $1`; do
        echo $i
        c=`echo $i | cut -c1`
        if echo $i | grep -q "^[0-9][0-9]*$"; then
                echo $i >> $i.nr
        elif echo $c | grep -q "[A-Za-z]"; then
                echo $i >> $c
        else
                n=`expr $n + 1`
        fi
done
echo $n
