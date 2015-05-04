
@echo off

if [%1] == [] goto usage
if [%2] == [] goto usage

call :execute_scripts %1 %2
goto :eof

:execute_scripts

for /F %%F in (execution_order) do (
    echo Executing %%F
    mysql -u%1 -p%2 < %%F
)

goto :eof

:usage
echo Usage: execute.bat user pass

