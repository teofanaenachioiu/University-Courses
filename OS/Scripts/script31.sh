
#!/bin/sh
sum=0
for el in $* ;do
        if [ -f $el ]; then
                nr=`cat $el | ls -c`
                if [ $(($nr%2)) -eq 1 ] ;then
                        sum=$(($sum+1))
                fi
        fi
        if [ $el = "[0-9]*+\?-\?[0-9]*i\?" ]; then
done
