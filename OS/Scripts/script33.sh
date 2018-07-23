
#!/bin/sh
if [ -d $1 ] ;then
        t_f=0
        t_l=0
        find $1 -type f -print | while read F ;do
                if [ `file $F | grep "text" -c` -gt 0 ] ;then
                        lc=`wc -l` <$F
                        t_l=$(($t_l + $lc))
echo $t_l
                        t_f=$(($t_f + 1))
echo $t_f
                fi
                done
        echo "$t_l / $t_f = $(($t_l / $t_f))"

else echo "$1 nu e director"
fi
