#Se se scrie un script ce muta toate fisierele in directorul dat ca parametru in linia de comanda.

#!/bin/sh

if [ $# -ne 1 ]; then echo "Dati un singur director destinatie"
        exit 1
fi

if [ ! -d $1 ]; then
        mkdir $1
fi

for F in `find .` ;do
        if [ $F -ef $0 ]; then echo "Scriptul nu se muta" ;continue ;fi
        if [ $F -ef $1 ]; then echo "Fisierul destinatie nu se muta"; continue ;fi
        mv $F $1
        echo "Am mutat $F in $1"
done
