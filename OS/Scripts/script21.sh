
#!/bin/sh
if [ $# -gt 0 ];then
        for f in $* ;do
        #echo $f
        if [ -f $f ];then
                nr_l=`cat $f | wc -l`
                nr_c=`cat $f | wc -c`
                echo $f,$nr_l,$nr_c
        fi
        if [ -d $f ];then
                nr_f=`find $f | wc -l`
                echo $f,$nr_f
        fi
        done
else echo "Nu s-au dat parametrii"
fi
