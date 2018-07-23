
#!/bin/sh
for f in `find . -type f -perm -g+w,u+w,o+w` ;do
        echo $f
        mv $f "$f.all"
done
for f in `find . -type f -perm -g+w,u+w,o+w` ;do
        echo $f
done
