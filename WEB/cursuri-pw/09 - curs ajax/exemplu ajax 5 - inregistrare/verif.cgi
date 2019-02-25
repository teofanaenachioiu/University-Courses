#!/bin/bash
echo Content-type: text/html
echo
sleep 2
user=`echo $QUERY_STRING | cut -d"=" -f2`
if grep $user useri.dat > /dev/null
then
  echo 0
else
  echo 1
fi
