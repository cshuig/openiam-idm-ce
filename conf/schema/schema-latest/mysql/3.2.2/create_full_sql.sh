#!/bin/bash

sqlFileName="full_db.sql"
cat "" > $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/2.3/" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta1/idm" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta1/am" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta1/km" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta1/reports" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta1/compatability" $sqlFileName

cat "/data/openiam/conf/schema/mysql/3.0/beta1/km/dml/reset_passwords.sql" >> $sqlFileName
echo >> $sqlFileName 
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta2" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta3" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta4" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta5" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.0/beta6" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.01" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.02" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.03" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.04" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.05" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.06" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.1" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.1.3" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.1" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.2-RC1" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.2-RC2" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.2-RC3" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.2-RC4" $sqlFileName
bash add_to_file.sh "/data/openiam/conf/schema/mysql/3.2.2-RC5" $sqlFileName

dos2unix $sqlFileName

