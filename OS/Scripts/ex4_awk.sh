#!/bin/sh
awk <$1 '{for(i=1;i<=NF;i++) if($i !~ /^[a-z]\+$/) $i=""; linie=""; for(i=1;i<=NF;i++) if($i!="") print "Da";linie=linie $i  " "; if(linie!="") print linie;}'
