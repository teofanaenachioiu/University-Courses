
#!/bin/sh
for F in `find .` ;do
        file -b $F >>r.txt
done
cat r.txt | sort | uniq -c
rm r.txt
