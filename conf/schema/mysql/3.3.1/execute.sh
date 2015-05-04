#!/bin/bash

if [ -z "$1" ]
  	then
    	echo "Usage: execute.sh [mysql root password]"
		return
fi

dos2unix execution_order

echo "=============== EXECUTING 3.3 Script ==============================="
while read IDM_F  ; do
	echo "Executing $IDM_F"
	mysql --user=root --password=$1 < "$IDM_F"
done < execution_order
echo "=============== DONE EXECUTING 3.3 =========================="
echo ""
echo ""

if [ "$2" == "new_install" ]
		then
			echo "Executing new install script util/1.fix_data.sql"
			mysql --user=root --password=$1 openiam < util/1.fix_data.sql
fi
