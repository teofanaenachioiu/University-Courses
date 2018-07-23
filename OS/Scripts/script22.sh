
#!/bin/sh
if [ $# -lt 1 ]; then echo "Nu s-au dat paramterii"
elif [ $(($#%3)) -ne 0 ] ;then echo "Nu s-a dat numarul portivit de parametrii"
else
        awk -v cuv=$2 -v k=$3 '{nr=0; for(i=1;i<=NF;i++) if ($i==cuv) nr++; if (k==nr) print $0}' $1
        shift 3
fi
