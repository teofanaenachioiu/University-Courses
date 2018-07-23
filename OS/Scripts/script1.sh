#!/bin/sh
for F in `find`; do
        if [ ! -f $F ]; then
                continue
        fi
U=`ls -l $F | cut -d' ' -f3`
D=`ls -l $F | cut -d' ' -f5`
if [ $D -gt 100 ]; then
        echo $U
fi
done | sort | uniq
