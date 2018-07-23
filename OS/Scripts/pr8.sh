
#!/bin/sh
#suma arg de pe poz divizibile cu 8
suma=0
while [ $# -ge 8 ]; do
        suma=$(($suma+$8))
        shift 8
done
echo $suma
