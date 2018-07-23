#!/bin/sh
for F in `find -maxdepth 1 -type f -name "*[0-9]*"`; do
        echo $F
done
