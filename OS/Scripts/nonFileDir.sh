
#!/bin/sh

#print non file and directory

for a in $* ;do
#       echo $a
        if [ ! -f $a ]; then
#               echo "da"
                if [ ! -d $a ];then
                        echo $a
                fi
        fi
done
