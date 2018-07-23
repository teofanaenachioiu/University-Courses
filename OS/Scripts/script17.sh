#!/bin/sh
for f in $* ;do
        grep -io "[a-z]\+" $f
done | sort | uniq -c | sort -r -k2
