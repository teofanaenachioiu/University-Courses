#!/bin/sh
for F in `find . -type f 2>/dev/null`; do
        if [ ! -r $F ]; then continue
        fi
        if head -n 1 $F | grep -q "^#!/bin/sh"; then echo $F
        fi
done
