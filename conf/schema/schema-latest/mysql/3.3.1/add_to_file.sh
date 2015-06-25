#!/bin/bash


dos2unix $1/execution_order

echo "=============== EXECUTING FROM $1 ==============================="
while read IDM_F  ; do
	echo "Executing $1/$IDM_F"
	echo "/* Executing $1/$IDM_F */" >> $2
	cat "$1/$IDM_F" >> $2	
	echo >> $2 
	echo "commit;" >> $2
done < $1/execution_order

