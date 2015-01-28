
@echo off

if [%1] == [] goto usage
if [%2] == [] goto usage

call :execute_scripts %1 %2 %3
goto :eof

:execute_scripts

echo =============== EXECUTING IDM ===============================
for /F %%F in (idm/execution_order) do (
    echo Executing idm/%%F
    mysql -u%1 -p%2 < idm/%%F
)
echo ""

echo =============== EXECUTING AM ===============================
for /F %%F in (am/execution_order) do (
    echo Executing am/%%F
    mysql -u%1 -p%2 < am/%%F
)
echo ""

echo =============== EXECUTING KM ===============================
for /F %%F in (km/execution_order) do (
    echo Executing km/%%F
    mysql -u%1 -p%2 < km/%%F
)
echo ""

echo =============== EXECUTING REPORTS ===============================
for /F %%F in (reports/execution_order) do (
    echo Executing reports/%%F
    mysql -u%1 -p%2 < reports/%%F
)
echo ""

echo =============== EXECUTING COPATABILITY ===============================
for /F %%F in (compatability/execution_order) do (
    echo Executing compatibility/%%F
    mysql -u%1 -p%2 < compatability/%%F
)
echo ""

if %3 equ new_install (
    echo Executing new install script km/dml/reset_passwords.sql
    mysql -u%1 -p%2 openiam < km/dml/reset_passwords.sql)

goto :eof

:usage
echo Usage: execute.bat user pass [new_install]

