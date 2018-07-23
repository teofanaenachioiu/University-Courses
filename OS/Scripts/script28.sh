
#!/bin/sh
for d in $* ;do
        if [ ! -d $d ] ;then continue ;fi
        if [ -d $d ] ;then
        nr=$(du -s -B1 $d)
        fi
        #echo $nr
        copie=$nr
        while [ $copie -gt 0 ] ; do
                cif=$(($copie % 10))
                echo $cif
                if [ $(($cif % 2)) -eq 0 ]; then $nr>>aux.txt ;fi
                copie=$((copie / 10))
        done
done
cat aux.txt | sort -nu
rm aux.txt
