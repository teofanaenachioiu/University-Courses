
#!/bin/sh
rm /tmp/fisiere
rm /tmp/directoare
for f in `find . -type f`; do
        echo $f >>/tmp/fisiere
done
for d in `find . -type d`; do
        echo $d >>/tmp/directoare
done
#cat /tmp/directoare | sort
cat /tmp/fisiere | srt
rm /tmp/fisiere
rm /tmp/directoare
