
#!/bin/sh
f=`find . -type f`
d=`find . -type d`
echo $f;
echo "Spatiere"
echo $d
for x in $f; do
        for y in $d; do
                if [ $x = $y ]; then
                        echo "OK"
                fi
        done
done
