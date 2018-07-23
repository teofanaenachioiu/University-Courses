
#!/bin/sh
for f in `find $1 -perm 755` ;do
        echo -n "Da/Nu: "
        read accept
        if [ $accept = "da" ] ;then
                echo $f
                chmod 744 $f
        fi
done
