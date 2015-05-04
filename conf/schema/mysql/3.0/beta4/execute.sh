#!/bin/bash

if [ -z "$1" ]
  	then
    	echo "Usage: execute.sh [mysql root password]"
		return
fi

dos2unix execution_order

echo "=============== EXECUTING Beta4 Script ==============================="
while read IDM_F  ; do
	echo "Executing $IDM_F"
	mysql --user=root --password=$1 < "$IDM_F"
done < execution_order
echo "=============== DONE Beta4 FINAL =========================="
echo ""
echo ""
