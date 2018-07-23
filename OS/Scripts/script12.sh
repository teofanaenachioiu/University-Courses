
#!/bin/sh
awk '{print $3}' $1 | awk 'BIGIN {nr=0;n=0} {if(substr($1,0,length($1))=="[0-9]") {n++;x[nr++]=NR}}END{if (n>0) {print "Liniile "; for (nr-- ; nr>=0; nr--) print x[nr] ;print "au al treilea cuvant numar; numar total linii:" n}}'
