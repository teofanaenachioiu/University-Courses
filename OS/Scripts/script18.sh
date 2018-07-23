#!/bin/sh
if [ $# -ne 1 ]; then echo "Nu s-a dat directorul";
else
for f in `find $1` ;do
        if [ -f $f ]; then
        if grep -q "[0-9]\{5,\}" $f ;then echo $f
        fi
fi
done
fi
