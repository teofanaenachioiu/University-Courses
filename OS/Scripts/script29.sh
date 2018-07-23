
#!/bin/sh
for direct in $* ;do
        if [ -d $direct ] ;then
                #echo $direct
                for f in `find $direct -name "*.c"` ;do
                #       echo $f
                        cat $f | grep -o "#include<.*>" >>fis.txt
                done
        fi

done
cat fis.txt | sort -u
rm fis.txt
