
#!/bin/sh

for fisier in `find . -maxdepth 1 -type f -name "*.txt"` ;do
        if grep -q "cat" $fisier;then
                echo $fisier
        fi
done
