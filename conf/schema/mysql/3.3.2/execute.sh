#!/bin/bash

if [ -z "$1" ]
  	then
    	echo "Usage: execute.sh [mysql root password]"
		return
fi

dos2unix execution_order

echo "=============== EXECUTING 3.3.2 Script ==============================="
while read IDM_F  ; do
	echo "Executing $IDM_F"
	mysql --user=root --password=$1 < "$IDM_F"
done < execution_order

if [ "$2" == "new_install" ]
		then
			echo "Executing new install script"
			mysql --user=root --password=$1 openiam < dml/new_install/1_appTableImprovement.sql
fi

echo "=============== DONE EXECUTING 3.3.2 =========================="
echo ""
echo ""
