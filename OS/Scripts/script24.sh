
#!/bin/sh
for f in `find .` ;do
        nr=`expr length $f`
        if [ $nr -lt 8 ] ;then
                if file -b $f | grep -q "text"  ;then
                        head -n 10 $f
                fi
        fi
done
