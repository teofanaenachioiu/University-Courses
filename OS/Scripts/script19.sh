
#!/bin/sh
for f in `find . -type f` ;do
        if file -b $f | grep -q "text"; then
                nr=$(cat $f | wc -l)
                if [ $nr -lt 10 ]; then cat $f ;
                else echo $(head -n 5 $f)
                        echo $(tail -n 5 $f)
                fi
        fi
done
