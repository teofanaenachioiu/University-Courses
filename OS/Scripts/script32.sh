
#!/bin/sh
nr=0;
for file_name in `find . -type f -name "*c"` ; do

        if [ $nr -lt 5 ]; then
                        a=`cat $file_name | wc -l`

                        if [ $a -gt 10 ]; then
                                nr=$(($nr+1))

                                echo $file_name
                        fi

        fi
done
