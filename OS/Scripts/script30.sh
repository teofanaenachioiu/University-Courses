
#!/bin/sh
ps -ef | awk '{print $1}' | sort -u >users.txt

for user in $* ;do
        ps -ef | grep -w "^$user"
done
rm users.txt
