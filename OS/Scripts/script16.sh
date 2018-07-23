#!/bin/sh
echo -n "Fisierul: "; read fisier

for d in $@ ;do
        if [ -e $d/$fisier ]; then
                        echo "Am gasit $fisier in $d"
                fi

done
