
#!/bin/sh
#numarul mediu de linii
if [ $# -ne 1 ]; then echo "Dati un director"; exit 1; fi
if [ ! \( -d $1 \) ]; then echo "Dati un dir"; exit 2; fi
linii=0;
total=0;
nr=0
find $1 -type f -print | sort | while read Fisier t; do
        if [ `file $Fisier | grep -ci "ASCII text"` -eq 0 ]; then continue ;fi
        linii=`wc -l <$Fisier`
        #echo "$linii"
        total=$(($total+$linii))
        nr=$(($nr+1))
        echo "$total">/tmp/total
        echo "$nr">/tmp/nr

done
total=`cat /tmp/total`
nr=`cat /tmp/nr`
rm /tmp/total
rm /tmp/nr
medie=$(($total/$nr))
echo "Medie: $medie"

