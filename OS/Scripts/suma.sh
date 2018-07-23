
#!/bin/sh

#suma de pe pozitii impare
S=0
while [ $# -gt 0 ];do
        S=$(($S+$1))
        shift
        shift
done
echo $S
