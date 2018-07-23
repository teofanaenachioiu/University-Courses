#Scrieti un script shell care primeste ca prim argument calea catre un director.
#Scriptul va parcurge recursiv directorul primit ca prim argument si va identifica:
#-fisierul pentru care utilizatorul curent are permisiuni de executie, cu numarul minim de cuvinte
#-fisierul pentru care utilizatorul curent are permisiuni de scriere, cu numarul maxim de linii
#Se vor afisa numele si numarul de cuvinte/linii din acel fisie (daca sunt mai multe, se va afisa doa primul).
#Se va valida numarul argumentelor liniei de comanda. 
#Daca directorul dat ca prim parametru nu exista, se va afisa un mesaj de eroare si se va inchei executia.


#!/bin/sh
if [ $# -ne 1 ]
        then echo "Dati un director!"
else
        if [ ! -e $1 ]; then
                if [ ! -d $1 ];then
                        echo "Eroare"
                fi
        elif [ -d $1 ]; then
        f_min_cuv="Nu exista"
        f_max_lin="Nu exista"
        min_cuv=0
        max_lin=0
        nr=1
                for F in `find $1 -type f -perm -u+x`;do
                        nr_cuv=`cat $F | wc -w`
                #echo "Cuv: " $nr_cuv
                        if [ $nr -eq 1 ]; then
                                min_cuv=`cat $F | wc -w`
                                f_min_cuv=$F
                        fi
                        nr=$(($nr+1))
                        if [ $nr_cuv -lt $min_cuv ]; then
                                min_cuv=$nr_cuv
                                f_min_cuv=$F
                        fi
                done
                echo "Numar minim de cuvinte:"
                echo $f_min_cuv
                echo $min_cuv
                for F in `find $1 -type f -perm -u+w`;do
                        nr_lin=`cat $F | wc -l`
                        #echo $nr_lin
                        if [ $nr_lin -gt $max_lin ]; then
                                max_lin=$nr_lin
                                f_max_lin=$F
                        fi
                done
                echo "Numar maxim de linii:"
                echo $f_max_lin
                echo $max_lin
        fi
fi
