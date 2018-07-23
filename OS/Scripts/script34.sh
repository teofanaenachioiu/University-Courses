#!/bin/sh
for f in `find . -maxdepth 1 -type f -name "*[0-9]*"`; do
        echo $f
done
