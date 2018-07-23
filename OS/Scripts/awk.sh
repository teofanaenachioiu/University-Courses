#!/bin/sh

#suma nr de pe poz 2

cat nrSeparate | cut -f 2 -d":"| awk 'BEGIN{S=0}{S=S+$1}END{print S}'
