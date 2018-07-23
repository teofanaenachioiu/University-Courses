
#!/bin/sh
if [ $# -lt 1 ]
        then echo "Ba"
else
        for f in $* ;do
                cat $f | sort | uniq -c | sort -r | head -n 1 >>aux.txt
        done
        cat aux.txt | sort -r | sed '/^[ ]*\[0-9\]\+/d' |  awk '{print $0}'
        rm aux.txt
fi
