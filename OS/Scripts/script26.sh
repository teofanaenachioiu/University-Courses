
#!/bin/sh
cifra=$1
inc=$2
sf=$3
curent=$inc
while [ $curent -le $sf ]
        do
        copie=$curent
        while [ $copie -gt 0 ]
                do
                c=$(($copie%10))
                #echo $c
                if [ $c -eq $cifra ]; then
                        echo $curent
                        break
                fi
                copie=$(($copie/10))
        done
        curent=$(($curent+1))
done
