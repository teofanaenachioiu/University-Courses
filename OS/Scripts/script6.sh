
#!/bin/bash
function stdir-cool {
    DIR=$1
    DEST=$2

    find $DIR -exec ls -l -d {} \; > $DEST 2>/dev/null
}

stdir-cool $1 vechi
while true; do
    sleep 1
    stdir-cool $1 curent
    diff vechi curent
    mv curent vechi
done

