#Sa se scrie un script shell care primeste ca prim parametru in linia de comanda
#un cuvant urmat de oricate nume de directoare (minim 1). Sa se determine cate
#dintre aceste directoare contin cel putin doua fisiere in care apare cuvantul dat.
#Se va valida nr de argumente ale liniei de comanda.

#!/bin/sh
if [ $# -lt 2 ]
        then
        echo "Numar invalid de argumente"
else
        cuv=$1
        nr=0
        for director in $* ;do
                #verific daca argumentul e director
                if [ -d $director ]; then
                        #numar fisierele
                        nr_f=0
                        for f in `find $director -type f` ;do
                                #pentru fiecare fisier din director numar aparitiile cuvantului dat
                                cate=`cat $f | grep -c "$cuv"`
                                if [ $cate -gt 0 ] ;then
                                        nr_f=$(($nr_f+1))
                                fi
                        done
                        #cuvantul apare de cel putin doua ori .... am gasit un director
                        if [ $nr_f -gt 1 ]; then
                                nr=$(($nr+1))
                        fi
                fi
        done
        #afisare numar de directoare
        echo "Numarul de directoare care contin cel putin doua fisiere in care apare argumentul dat: $nr"

fi
